package d021248.group.viz;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import d021248.group.Group;
import d021248.group.GroupHelper;
import d021248.group.api.Element;

/**
 * Interactive Cayley graph visualizer.
 * <p>
 * Displays group elements as vertices arranged in a circle, with directed edges
 * colored by generator. Shows the group's structure through its generator
 * relationships.
 * </p>
 */
public class CayleyGraphViewer<E extends Element> extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Group<E> group;
    private final List<E> elements;
    private List<E> generators; // Not final - initialized in constructor
    private final Map<E, Point> positions;
    private final Color[] generatorColors;
    private final JLabel statusLabel;

    private E hoveredElement = null;
    private E selectedElement = null;

    private static final int NODE_RADIUS = 25;
    private static final int ARROW_SIZE = 8;

    private static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public CayleyGraphViewer(Group<E> group) {
        this.group = group;
        this.elements = new ArrayList<>(group.elements());

        // Get generators using GroupHelper strategy
        GroupHelper<E> helper = new GroupHelper<>(group);
        try {
            this.generators = new ArrayList<>(helper.strategyGenerators());
        } catch (Exception e) {
            // Fallback: use all non-identity elements as generators for small groups
            this.generators = new ArrayList<>();
            E identity = group.identity();
            for (E elem : elements) {
                if (!elem.equals(identity) && generators.size() < 6) {
                    generators.add(elem);
                }
            }
            if (generators.isEmpty()) {
                generators.add(elements.get(Math.min(1, elements.size() - 1)));
            }
        }

        this.positions = new HashMap<>();
        this.generatorColors = generateGeneratorColors(generators.size());
        this.statusLabel = new JLabel("Click elements to highlight their generator paths");

        calculateCircularLayout();
        setBackground(Color.WHITE);
        setupMouseListener();
    }

    private void calculateCircularLayout() {
        int n = elements.size();
        int centerX = 400;
        int centerY = 400;
        int radius = Math.min(350, Math.max(150, n * 15));

        setPreferredSize(new Dimension(800, 800));

        // Place identity at top
        E identity = group.identity();
        int idIndex = elements.indexOf(identity);
        if (idIndex > 0) {
            // Swap to put identity first
            E temp = elements.get(0);
            elements.set(0, identity);
            elements.set(idIndex, temp);
        }

        for (int i = 0; i < n; i++) {
            double angle = -Math.PI / 2 + (2 * Math.PI * i / n);
            int x = centerX + (int) (radius * Math.cos(angle));
            int y = centerY + (int) (radius * Math.sin(angle));
            positions.put(elements.get(i), new Point(x, y));
        }
    }

    private void setupMouseListener() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                E newHovered = findElementAt(e.getX(), e.getY());

                if (newHovered != hoveredElement) {
                    hoveredElement = newHovered;
                    if (hoveredElement != null && selectedElement == null) {
                        updateStatusLabel(hoveredElement);
                    }
                    repaint();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                E clicked = findElementAt(e.getX(), e.getY());

                if (clicked != null) {
                    selectedElement = (selectedElement == clicked) ? null : clicked;
                    if (selectedElement != null) {
                        updateStatusLabel(selectedElement);
                    } else {
                        statusLabel.setText("Click elements to highlight their generator paths");
                    }
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (selectedElement == null) {
                    hoveredElement = null;
                    statusLabel.setText("Click elements to highlight their generator paths");
                    repaint();
                }
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private E findElementAt(int x, int y) {
        for (Map.Entry<E, Point> entry : positions.entrySet()) {
            Point p = entry.getValue();
            int dx = x - p.x;
            int dy = y - p.y;
            if (dx * dx + dy * dy <= NODE_RADIUS * NODE_RADIUS) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void updateStatusLabel(E element) {
        StringBuilder sb = new StringBuilder("  Element: " + element);

        if (!element.equals(group.identity())) {
            int order = group.order(element);
            sb.append("  |  Order: ").append(order);

            // Check if it's a generator
            if (generators.contains(element)) {
                sb.append("  |  [GENERATOR]");
            }
        } else {
            sb.append("  [IDENTITY]");
        }

        statusLabel.setText(sb.toString());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw edges
        drawEdges(g2);

        // Draw nodes
        drawNodes(g2);

        // Draw legend
        drawLegend(g2);
    }

    private void drawEdges(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2f));

        for (int genIdx = 0; genIdx < generators.size(); genIdx++) {
            E gen = generators.get(genIdx);
            Color color = generatorColors[genIdx];

            // Determine opacity based on selection
            int alpha = 180;
            if (selectedElement != null) {
                // Only show edges involving selected element
                alpha = 80;
            }

            g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));

            for (E from : elements) {
                // Skip if we have a selection and this edge doesn't involve it
                if (selectedElement != null && !from.equals(selectedElement)) {
                    E to = group.operate(from, gen);
                    if (!to.equals(selectedElement)) {
                        continue;
                    }
                }

                E to = group.operate(from, gen);
                Point pFrom = positions.get(from);
                Point pTo = positions.get(to);

                // Make edges involving selected element more visible
                if (selectedElement != null && (from.equals(selectedElement) || to.equals(selectedElement))) {
                    g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 255));
                    g2.setStroke(new BasicStroke(3f));
                } else if (selectedElement == null) {
                    g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha));
                    g2.setStroke(new BasicStroke(2f));
                }

                drawArrow(g2, pFrom.x, pFrom.y, pTo.x, pTo.y);
            }
        }

        g2.setStroke(new BasicStroke(1f));
    }

    private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
        // Calculate direction
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dist = Math.sqrt(dx * dx + dy * dy);

        if (dist < NODE_RADIUS * 2)
            return; // Skip self-loops for now

        // Shorten line to stop at node boundary
        double ratio = (dist - NODE_RADIUS) / dist;
        int endX = x1 + (int) (dx * ratio);
        int endY = y1 + (int) (dy * ratio);

        // Draw line
        g2.drawLine(x1, y1, endX, endY);

        // Draw arrowhead
        double angle = Math.atan2(dy, dx);
        int[] xPoints = new int[3];
        int[] yPoints = new int[3];

        xPoints[0] = endX;
        yPoints[0] = endY;
        xPoints[1] = endX - (int) (ARROW_SIZE * Math.cos(angle - Math.PI / 6));
        yPoints[1] = endY - (int) (ARROW_SIZE * Math.sin(angle - Math.PI / 6));
        xPoints[2] = endX - (int) (ARROW_SIZE * Math.cos(angle + Math.PI / 6));
        yPoints[2] = endY - (int) (ARROW_SIZE * Math.sin(angle + Math.PI / 6));

        g2.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawNodes(Graphics2D g2) {
        Font labelFont = new Font("SansSerif", Font.BOLD, 11);
        g2.setFont(labelFont);
        FontMetrics fm = g2.getFontMetrics();

        E identity = group.identity();

        for (E elem : elements) {
            Point p = positions.get(elem);
            boolean isIdentity = elem.equals(identity);
            boolean isGenerator = generators.contains(elem);
            boolean isHovered = elem.equals(hoveredElement);
            boolean isSelected = elem.equals(selectedElement);

            // Determine colors
            Color fillColor;
            if (isIdentity) {
                fillColor = new Color(255, 215, 0); // Gold for identity
            } else if (isGenerator) {
                fillColor = new Color(100, 200, 255); // Blue for generators
            } else {
                fillColor = Color.WHITE;
            }

            // Highlight
            if (isSelected) {
                g2.setColor(new Color(255, 255, 0, 150));
                g2.fillOval(p.x - NODE_RADIUS - 8, p.y - NODE_RADIUS - 8,
                        (NODE_RADIUS + 8) * 2, (NODE_RADIUS + 8) * 2);
            } else if (isHovered) {
                g2.setColor(new Color(200, 200, 255, 100));
                g2.fillOval(p.x - NODE_RADIUS - 5, p.y - NODE_RADIUS - 5,
                        (NODE_RADIUS + 5) * 2, (NODE_RADIUS + 5) * 2);
            }

            // Draw node
            g2.setColor(fillColor);
            g2.fillOval(p.x - NODE_RADIUS, p.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            g2.setColor(isSelected ? Color.RED : Color.BLACK);
            g2.setStroke(new BasicStroke(isSelected ? 3f : 2f));
            g2.drawOval(p.x - NODE_RADIUS, p.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            // Draw label
            String label = elem.toString();
            if (label.length() > 8) {
                label = label.substring(0, 6) + "..";
            }
            int labelWidth = fm.stringWidth(label);
            g2.setColor(Color.BLACK);
            g2.drawString(label, p.x - labelWidth / 2, p.y + 5);
        }

        g2.setStroke(new BasicStroke(1f));
    }

    private void drawLegend(Graphics2D g2) {
        int x = 20;
        int y = 20;
        int lineHeight = 25;

        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.setColor(Color.BLACK);
        g2.drawString("Generators:", x, y);

        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        for (int i = 0; i < generators.size(); i++) {
            y += lineHeight;
            g2.setColor(generatorColors[i]);
            g2.fillRect(x, y - 12, 15, 15);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y - 12, 15, 15);
            g2.drawString(" " + generators.get(i).toString(), x + 20, y);
        }
    }

    private Color[] generateGeneratorColors(int n) {
        Color[] colors = {
                new Color(220, 50, 50), // Red
                new Color(50, 150, 220), // Blue
                new Color(50, 200, 50), // Green
                new Color(220, 150, 50), // Orange
                new Color(150, 50, 200), // Purple
                new Color(200, 200, 50), // Yellow
        };

        if (n <= colors.length) {
            Color[] result = new Color[n];
            System.arraycopy(colors, 0, result, 0, n);
            return result;
        }

        // Generate more colors if needed
        Color[] result = new Color[n];
        for (int i = 0; i < n; i++) {
            if (i < colors.length) {
                result[i] = colors[i];
            } else {
                float hue = (float) i / n;
                result[i] = Color.getHSBColor(hue, 0.8f, 0.8f);
            }
        }
        return result;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * Create and display the viewer in a JFrame.
     */
    public static <E extends Element> void show(Group<E> group, String title) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(title);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            CayleyGraphViewer<E> viewer = new CayleyGraphViewer<>(group);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(new JScrollPane(viewer), BorderLayout.CENTER);

            JPanel statusPanel = new JPanel(new BorderLayout());
            statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            viewer.getStatusLabel().setFont(new Font("SansSerif", Font.PLAIN, 12));
            statusPanel.add(viewer.getStatusLabel(), BorderLayout.WEST);

            JLabel info = new JLabel(String.format("Group order: %d  |  Generators: %d",
                    group.elements().size(), viewer.generators.size()));
            info.setFont(new Font("SansSerif", Font.PLAIN, 12));
            statusPanel.add(info, BorderLayout.EAST);

            mainPanel.add(statusPanel, BorderLayout.SOUTH);

            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
