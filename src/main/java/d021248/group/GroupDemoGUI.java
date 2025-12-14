package d021248.group;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import d021248.group.api.Element;
import d021248.group.util.ThreadUtil;
import d021248.group.util.UIConstants;
import d021248.group.viz.CayleyGraphViewer;
import d021248.group.viz.CayleyTableViewer;
import d021248.group.viz.SubgroupLatticeViewer;

/**
 * Graphical launcher for group theory visualizations.
 * <p>
 * Provides a simple Swing GUI with dropdown menus for selecting
 * group types (Cyclic, Dihedral, Symmetric, Alternating) and
 * buttons to launch various visualization windows.
 * </p>
 * <p>
 * Usage:
 * 
 * <pre>{@code
 * SwingUtilities.invokeLater(() -> {
 *     GroupDemoGUI launcher = new GroupDemoGUI();
 *     launcher.setVisible(true);
 * });
 * }</pre>
 * </p>
 */
public final class GroupDemoGUI extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JComboBox<String> groupTypeCombo;
    private final JSpinner paramSpinner;

    /**
     * Creates a new GUI launcher with default settings.
     */
    public GroupDemoGUI() {
        super("Group Visualization Launcher");

        groupTypeCombo = new JComboBox<>(new String[] {
                UIConstants.CYCLIC_GROUP,
                UIConstants.DIHEDRAL_GROUP,
                UIConstants.SYMMETRIC_GROUP,
                UIConstants.ALTERNATING_GROUP
        });

        paramSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 12, 1));

        setupUI();
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
    }

    private void setupUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // Selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        selectionPanel.add(new JLabel("Group Type:"));
        selectionPanel.add(groupTypeCombo);
        selectionPanel.add(new JLabel("Parameter n:"));
        selectionPanel.add(paramSpinner);

        mainPanel.add(selectionPanel, BorderLayout.NORTH);

        // Button panel
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 5, 5));

        JButton cayleyTableBtn = new JButton("Show Cayley Table");
        cayleyTableBtn.addActionListener(e -> launchCayleyTable());
        buttonPanel.add(cayleyTableBtn);

        JButton latticeBtn = new JButton("Show Subgroup Lattice");
        latticeBtn.addActionListener(e -> launchSubgroupLattice());
        buttonPanel.add(latticeBtn);

        JButton graphBtn = new JButton("Show Cayley Graph");
        graphBtn.addActionListener(e -> launchCayleyGraph());
        buttonPanel.add(graphBtn);

        JButton allBtn = new JButton("Show All Three");
        allBtn.addActionListener(e -> launchAll());
        buttonPanel.add(allBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel info = new JLabel("Select a group and click a button to visualize");
        info.setFont(info.getFont().deriveFont(11f));
        infoPanel.add(info);
        mainPanel.add(infoPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private Group<?> createSelectedGroup() {
        int param = (Integer) paramSpinner.getValue();
        String type = (String) groupTypeCombo.getSelectedItem();

        if (type == null) {
            return GroupFactory.symmetric(4);
        }

        return switch (type) {
            case UIConstants.CYCLIC_GROUP -> GroupFactory.cyclic(param);
            case UIConstants.DIHEDRAL_GROUP -> GroupFactory.dihedral(param);
            case UIConstants.SYMMETRIC_GROUP -> GroupFactory.symmetric(param);
            case UIConstants.ALTERNATING_GROUP -> GroupFactory.alternating(param);
            default -> GroupFactory.symmetric(4);
        };
    }

    private String getGroupTitle() {
        int param = (Integer) paramSpinner.getValue();
        String type = (String) groupTypeCombo.getSelectedItem();

        if (type == null) {
            return "S_4";
        }

        return switch (type) {
            case UIConstants.CYCLIC_GROUP -> "Z_" + param;
            case UIConstants.DIHEDRAL_GROUP -> "D_" + param;
            case UIConstants.SYMMETRIC_GROUP -> "S_" + param;
            case UIConstants.ALTERNATING_GROUP -> "A_" + param;
            default -> "S_4";
        };
    }

    private void launchCayleyTable() {
        Group<?> group = createSelectedGroup();
        showViewer(group, "Cayley Table");
    }

    private void launchSubgroupLattice() {
        Group<?> group = createSelectedGroup();
        showViewer(group, "Subgroup Lattice");
    }

    private void launchCayleyGraph() {
        Group<?> group = createSelectedGroup();
        showViewer(group, "Cayley Graph");
    }

    private void launchAll() {
        Group<?> group = createSelectedGroup();
        String title = getGroupTitle();

        showCayleyTable(group, title);
        ThreadUtil.sleep(300);
        showLattice(group, title);
        ThreadUtil.sleep(300);
        showGraph(group, title);
    }

    @SuppressWarnings("unchecked")
    private <E extends Element> void showViewer(Group<?> group, String viewType) {
        Group<E> g = (Group<E>) group;
        String title = getGroupTitle() + " - " + viewType;

        if (viewType.contains("Cayley Table")) {
            CayleyTableViewer.show(g, title);
        } else if (viewType.contains("Lattice")) {
            SubgroupLatticeViewer.show(g, title);
        } else if (viewType.contains("Graph")) {
            CayleyGraphViewer.show(g, title);
        }
    }

    @SuppressWarnings("unchecked")
    private <E extends Element> void showCayleyTable(Group<?> group, String groupName) {
        CayleyTableViewer.show((Group<E>) group, groupName + " - Cayley Table");
    }

    @SuppressWarnings("unchecked")
    private <E extends Element> void showLattice(Group<?> group, String groupName) {
        SubgroupLatticeViewer.show((Group<E>) group, groupName + " - Subgroup Lattice");
    }

    @SuppressWarnings("unchecked")
    private <E extends Element> void showGraph(Group<?> group, String groupName) {
        CayleyGraphViewer.show((Group<E>) group, groupName + " - Cayley Graph");
    }

    /**
     * Standalone entry point for launching the GUI directly.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GroupDemoGUI launcher = new GroupDemoGUI();
            launcher.setVisible(true);
        });
    }
}
