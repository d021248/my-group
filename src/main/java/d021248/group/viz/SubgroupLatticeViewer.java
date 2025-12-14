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
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.subgroup.SpecialSubgroups;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupAnalyzer;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.util.UIConstants;

/**
 * Interactive Hasse diagram viewer for the subgroup lattice.
 * <p>
 * Displays all subgroups arranged in layers by order, with lines showing
 * containment relationships. Normal subgroups, center, commutator, and Frattini
 * subgroups are highlighted with different colors.
 * </p>
 */
public class SubgroupLatticeViewer<E extends Element> extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Group<E> group;
    private final List<Subgroup<E>> subgroups;
    private final Map<Subgroup<E>, Point> positions;
    private final Map<Subgroup<E>, SubgroupType> types;
    private final JLabel statusLabel;

    private Subgroup<E> hoveredSubgroup = null;

    private static final int NODE_RADIUS = 20;
    private static final int LAYER_HEIGHT = 100;
    private static final int MIN_NODE_SPACING = 60;

    private enum SubgroupType {
        NORMAL, CENTER, COMMUTATOR, FRATTINI, MAXIMAL, TRIVIAL, FULL, REGULAR
    }

    private static class Point {
        final int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public SubgroupLatticeViewer(Group<E> group) {
        this.group = group;
        this.subgroups = SubgroupGenerator.allSubgroups(group);
        this.positions = new HashMap<>();
        this.types = new HashMap<>();
        this.statusLabel = new JLabel(" ");

        classifySubgroups();
        calculateLayout();

        setBackground(Color.WHITE);
        setupMouseListener();
    }

    private void classifySubgroups() {
        Subgroup<E> center = SpecialSubgroups.center(group);
        Subgroup<E> commutator = SpecialSubgroups.commutatorSubgroup(group);
        Subgroup<E> frattini = SpecialSubgroups.frattiniSubgroup(group);
        List<Subgroup<E>> maximalSubs = SpecialSubgroups.maximalSubgroups(group);
        Set<Subgroup<E>> maximalSet = new HashSet<>(maximalSubs);

        for (Subgroup<E> sub : subgroups) {
            if (sub.order() == 1) {
                types.put(sub, SubgroupType.TRIVIAL);
            } else if (sub.order() == group.order()) {
                types.put(sub, SubgroupType.FULL);
            } else if (sub.elements().equals(center.elements())) {
                types.put(sub, SubgroupType.CENTER);
            } else if (sub.elements().equals(commutator.elements()) && sub.order() > 1) {
                types.put(sub, SubgroupType.COMMUTATOR);
            } else if (sub.elements().equals(frattini.elements()) && sub.order() > 1) {
                types.put(sub, SubgroupType.FRATTINI);
            } else if (maximalSet.contains(sub)) {
                types.put(sub, SubgroupType.MAXIMAL);
            } else if (SubgroupAnalyzer.isNormal(group, sub)) {
                types.put(sub, SubgroupType.NORMAL);
            } else {
                types.put(sub, SubgroupType.REGULAR);
            }
        }
    }

    private void calculateLayout() {
        // Group subgroups by order (layer)
        Map<Integer, List<Subgroup<E>>> layers = new HashMap<>();
        for (Subgroup<E> sub : subgroups) {
            layers.computeIfAbsent(sub.order(), k -> new ArrayList<>()).add(sub);
        }

        // Sort orders
        List<Integer> orders = new ArrayList<>(layers.keySet());
        Collections.sort(orders);

        // Calculate positions
        int maxWidth = 0;
        for (List<Subgroup<E>> layer : layers.values()) {
            maxWidth = Math.max(maxWidth, layer.size());
        }

        int width = Math.max(800, maxWidth * MIN_NODE_SPACING + 100);
        int height = orders.size() * LAYER_HEIGHT + 100;

        setPreferredSize(new Dimension(width, height));

        for (int i = 0; i < orders.size(); i++) {
            int order = orders.get(i);
            List<Subgroup<E>> layer = layers.get(order);
            int y = height - 50 - i * LAYER_HEIGHT;

            int layerWidth = (layer.size() - 1) * MIN_NODE_SPACING;
            int startX = (width - layerWidth) / 2;

            for (int j = 0; j < layer.size(); j++) {
                int x = startX + j * MIN_NODE_SPACING;
                positions.put(layer.get(j), new Point(x, y));
            }
        }
    }

    private void setupMouseListener() {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                Subgroup<E> newHovered = null;

                for (Map.Entry<Subgroup<E>, Point> entry : positions.entrySet()) {
                    Point p = entry.getValue();
                    int dx = e.getX() - p.x;
                    int dy = e.getY() - p.y;
                    if (dx * dx + dy * dy <= NODE_RADIUS * NODE_RADIUS) {
                        newHovered = entry.getKey();
                        break;
                    }
                }

                if (newHovered != hoveredSubgroup) {
                    hoveredSubgroup = newHovered;
                    if (hoveredSubgroup != null) {
                        updateStatusLabel(hoveredSubgroup);
                    } else {
                        statusLabel.setText(" ");
                    }
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredSubgroup = null;
                statusLabel.setText(" ");
                repaint();
            }
        };

        addMouseListener(adapter);
        addMouseMotionListener(adapter);
    }

    private void updateStatusLabel(Subgroup<E> sub) {
        StringBuilder sb = new StringBuilder("  Order: " + sub.order());
        sb.append("  |  Index: ").append(sub.index());

        SubgroupType type = types.get(sub);
        if (type != null && type != SubgroupType.REGULAR) {
            sb.append("  |  Type: ").append(type.name());
        }

        boolean normal = SubgroupAnalyzer.isNormal(group, sub);
        if (normal && sub.order() != 1 && sub.order() != group.order()) {
            sb.append("  |  NORMAL ⊲");
        }

        statusLabel.setText(sb.toString());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        // Draw edges first
        drawEdges(g2);

        // Draw nodes
        drawNodes(g2);
    }

    private void drawEdges(Graphics2D g2) {
        g2.setColor(new Color(150, 150, 150));
        g2.setStroke(new BasicStroke(1.5f));

        for (Subgroup<E> sub1 : subgroups) {
            for (Subgroup<E> sub2 : subgroups) {
                if (sub1.order() < sub2.order() && isDirectCover(sub1, sub2)) {
                    Point p1 = positions.get(sub1);
                    Point p2 = positions.get(sub2);
                    g2.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }
    }

    private boolean isDirectCover(Subgroup<E> smaller, Subgroup<E> larger) {
        if (!larger.elements().containsAll(smaller.elements())) {
            return false;
        }

        // Check if there's no intermediate subgroup
        for (Subgroup<E> intermediate : subgroups) {
            if (intermediate.order() > smaller.order() &&
                    intermediate.order() < larger.order() &&
                    larger.elements().containsAll(intermediate.elements()) &&
                    intermediate.elements().containsAll(smaller.elements())) {
                return false;
            }
        }

        return true;
    }

    private void drawNodes(Graphics2D g2) {
        Font labelFont = new Font(UIConstants.FONT_SANS_SERIF, Font.BOLD, 11);
        g2.setFont(labelFont);
        FontMetrics fm = g2.getFontMetrics();

        for (Subgroup<E> sub : subgroups) {
            Point p = positions.get(sub);
            boolean hovered = sub.equals(hoveredSubgroup);

            // Determine color based on type
            Color fillColor = getColorForType(types.get(sub));
            Color borderColor = hovered ? Color.BLACK : Color.DARK_GRAY;

            // Draw node
            if (hovered) {
                g2.setColor(new Color(255, 255, 0, 100));
                g2.fillOval(p.x - NODE_RADIUS - 5, p.y - NODE_RADIUS - 5,
                        (NODE_RADIUS + 5) * 2, (NODE_RADIUS + 5) * 2);
            }

            g2.setColor(fillColor);
            g2.fillOval(p.x - NODE_RADIUS, p.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            g2.setColor(borderColor);
            g2.setStroke(new BasicStroke(hovered ? 3f : 2f));
            g2.drawOval(p.x - NODE_RADIUS, p.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

            // Draw label
            String label = String.valueOf(sub.order());
            int labelWidth = fm.stringWidth(label);
            g2.setColor(Color.BLACK);
            g2.drawString(label, p.x - labelWidth / 2, p.y + 5);
        }
    }

    private Color getColorForType(SubgroupType type) {
        return switch (type) {
            case TRIVIAL -> new Color(220, 220, 220);
            case FULL -> new Color(100, 150, 255);
            case CENTER -> new Color(255, 200, 100);
            case COMMUTATOR -> new Color(255, 150, 150);
            case FRATTINI -> new Color(200, 150, 255);
            case MAXIMAL -> new Color(150, 255, 150);
            case NORMAL -> new Color(150, 220, 255);
            case REGULAR -> new Color(255, 255, 255);
        };
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

            SubgroupLatticeViewer<E> viewer = new SubgroupLatticeViewer<>(group);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(new JScrollPane(viewer), BorderLayout.CENTER);

            JPanel statusPanel = new JPanel(new BorderLayout());
            statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            viewer.getStatusLabel().setFont(new Font(UIConstants.FONT_SANS_SERIF, Font.PLAIN, 12));
            statusPanel.add(viewer.getStatusLabel(), BorderLayout.WEST);

            JLabel legend = new JLabel("<html>Legend: " +
                    "<span style='color:rgb(100,150,255)'>■</span> Full group  " +
                    "<span style='color:rgb(255,200,100)'>■</span> Center  " +
                    "<span style='color:rgb(255,150,150)'>■</span> Commutator  " +
                    "<span style='color:rgb(150,255,150)'>■</span> Maximal  " +
                    "<span style='color:rgb(150,220,255)'>■</span> Normal</html>");
            legend.setFont(new Font(UIConstants.FONT_SANS_SERIF, Font.PLAIN, 11));
            statusPanel.add(legend, BorderLayout.EAST);

            mainPanel.add(statusPanel, BorderLayout.SOUTH);

            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
