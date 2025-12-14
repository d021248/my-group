package d021248.group.viz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.util.UIConstants;

/**
 * Interactive Swing viewer for Cayley operation tables.
 * <p>
 * Displays the group operation table with color coding and interactive
 * tooltips showing operation details when hovering over cells.
 * </p>
 */
public class CayleyTableViewer<E extends Element> extends JPanel {
    private static final long serialVersionUID = 1L;

    private final Group<E> group;
    private final List<E> elements;
    private final int cellSize;
    private final Color[] palette;
    private final JLabel statusLabel;

    private int hoveredRow = -1;
    private int hoveredCol = -1;

    public CayleyTableViewer(Group<E> group, int cellSize) {
        this.group = group;
        this.elements = new ArrayList<>(group.elements());

        // Sort elements lexicographically by their string representation
        this.elements.sort((e1, e2) -> e1.toString().compareTo(e2.toString()));

        this.cellSize = cellSize;
        this.palette = generatePalette(elements.size());
        this.statusLabel = new JLabel(" ");

        int n = elements.size();
        setPreferredSize(new Dimension((n + 1) * cellSize, (n + 1) * cellSize));
        setBackground(Color.WHITE);

        setupMouseListener();
    }

    private void setupMouseListener() {
        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int col = e.getX() / cellSize - 1;
                int row = e.getY() / cellSize - 1;

                if (row >= 0 && row < elements.size() && col >= 0 && col < elements.size()) {
                    if (hoveredRow != row || hoveredCol != col) {
                        hoveredRow = row;
                        hoveredCol = col;

                        E a = elements.get(row);
                        E b = elements.get(col);
                        E result = group.operate(a, b);

                        statusLabel.setText(String.format("  %s ∗ %s = %s", a, b, result));
                        repaint();
                    }
                } else {
                    if (hoveredRow != -1 || hoveredCol != -1) {
                        hoveredRow = -1;
                        hoveredCol = -1;
                        statusLabel.setText(" ");
                        repaint();
                    }
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                hoveredCol = -1;
                statusLabel.setText(" ");
                repaint();
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int n = elements.size();

        // Draw top-left corner (black)
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, cellSize, cellSize);

        // Draw headers
        Font headerFont = new Font(UIConstants.FONT_SANS_SERIF, Font.BOLD, Math.max(8, cellSize / 3));
        g2.setFont(headerFont);

        for (int i = 0; i < n; i++) {
            // Top header
            g2.setColor(palette[i]);
            g2.fillRect((i + 1) * cellSize, 0, cellSize, cellSize);

            // Left header
            g2.fillRect(0, (i + 1) * cellSize, cellSize, cellSize);

            // Header borders
            g2.setColor(Color.DARK_GRAY);
            g2.drawRect((i + 1) * cellSize, 0, cellSize, cellSize);
            g2.drawRect(0, (i + 1) * cellSize, cellSize, cellSize);
        }

        // Draw table cells
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                E result = group.operate(elements.get(row), elements.get(col));
                int resultIndex = elements.indexOf(result);

                int x = (col + 1) * cellSize;
                int y = (row + 1) * cellSize;

                g2.setColor(palette[resultIndex]);
                g2.fillRect(x, y, cellSize, cellSize);

                // Highlight hovered cell
                if (row == hoveredRow && col == hoveredCol) {
                    g2.setColor(new Color(255, 255, 255, 128));
                    g2.fillRect(x, y, cellSize, cellSize);
                    g2.setColor(Color.BLACK);
                    g2.setStroke(new java.awt.BasicStroke(3));
                    g2.drawRect(x, y, cellSize, cellSize);
                    g2.setStroke(new java.awt.BasicStroke(1));
                } else {
                    g2.setColor(Color.GRAY);
                    g2.drawRect(x, y, cellSize, cellSize);
                }
            }
        }

        // Highlight identity row and column
        E identity = group.identity();
        int idIndex = elements.indexOf(identity);
        g2.setColor(new Color(0, 0, 0, 64));
        g2.setStroke(new java.awt.BasicStroke(2));

        // Identity row
        g2.drawRect(cellSize, (idIndex + 1) * cellSize, n * cellSize, cellSize);
        // Identity column
        g2.drawRect((idIndex + 1) * cellSize, cellSize, cellSize, n * cellSize);

        g2.setStroke(new java.awt.BasicStroke(1));
    }

    private Color[] generatePalette(int n) {
        Color[] colors = new Color[n];
        for (int i = 0; i < n; i++) {
            float hue = (float) i / n;
            colors[i] = Color.getHSBColor(hue, 0.7f, 0.9f);
        }
        return colors;
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
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            int n = group.elements().size();
            int cellSize = Math.clamp(800 / n, 20, 60);

            CayleyTableViewer<E> viewer = new CayleyTableViewer<>(group, cellSize);

            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(new JScrollPane(viewer), BorderLayout.CENTER);

            JPanel statusPanel = new JPanel(new BorderLayout());
            statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            viewer.getStatusLabel().setFont(new Font("Monospaced", Font.PLAIN, 14));
            statusPanel.add(viewer.getStatusLabel(), BorderLayout.WEST);

            JLabel infoLabel = new JLabel(String.format("Group order: %d  (hover over cells to see operations)", n));
            infoLabel.setFont(new Font(UIConstants.FONT_SANS_SERIF, Font.PLAIN, 12));
            statusPanel.add(infoLabel, BorderLayout.EAST);

            mainPanel.add(statusPanel, BorderLayout.SOUTH);

            frame.setContentPane(mainPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
