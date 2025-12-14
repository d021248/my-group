package d021248.group.viz.examples;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import d021248.group.Group;
import d021248.group.GroupFactory;
import d021248.group.api.Element;
import d021248.group.viz.CayleyGraphViewer;
import d021248.group.viz.CayleyTableViewer;
import d021248.group.viz.SubgroupLatticeViewer;

/**
 * Interactive launcher with group selection UI.
 * Provides dropdowns to select group type and parameter.
 */
public class VizLauncher extends JFrame {
    private static final long serialVersionUID = 1L;

    private final JComboBox<String> groupTypeCombo;
    private final JSpinner paramSpinner;
    private Group<?> currentGroup;

    public VizLauncher() {
        super("Group Visualization Launcher");

        groupTypeCombo = new JComboBox<>(new String[] {
                "Cyclic (Z_n)",
                "Dihedral (D_n)",
                "Symmetric (S_n)",
                "Alternating (A_n)"
        });

        paramSpinner = new JSpinner(new SpinnerNumberModel(4, 2, 12, 1));

        setupUI();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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

        var cayleyTableBtn = new javax.swing.JButton("Show Cayley Table");
        cayleyTableBtn.addActionListener(e -> launchCayleyTable());
        buttonPanel.add(cayleyTableBtn);

        var latticeBtn = new javax.swing.JButton("Show Subgroup Lattice");
        latticeBtn.addActionListener(e -> launchSubgroupLattice());
        buttonPanel.add(latticeBtn);

        var graphBtn = new javax.swing.JButton("Show Cayley Graph");
        graphBtn.addActionListener(e -> launchCayleyGraph());
        buttonPanel.add(graphBtn);

        var allBtn = new javax.swing.JButton("Show All Three");
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
            case "Cyclic (Z_n)" -> GroupFactory.cyclic(param);
            case "Dihedral (D_n)" -> GroupFactory.dihedral(param);
            case "Symmetric (S_n)" -> GroupFactory.symmetric(param);
            case "Alternating (A_n)" -> GroupFactory.alternating(param);
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
            case "Cyclic (Z_n)" -> "Z_" + param;
            case "Dihedral (D_n)" -> "D_" + param;
            case "Symmetric (S_n)" -> "S_" + param;
            case "Alternating (A_n)" -> "A_" + param;
            default -> "S_4";
        };
    }

    private void launchCayleyTable() {
        currentGroup = createSelectedGroup();
        showViewer(currentGroup, "Cayley Table", VizType.CAYLEY_TABLE);
    }

    private void launchSubgroupLattice() {
        currentGroup = createSelectedGroup();
        showViewer(currentGroup, "Subgroup Lattice", VizType.LATTICE);
    }

    private void launchCayleyGraph() {
        currentGroup = createSelectedGroup();
        showViewer(currentGroup, "Cayley Graph", VizType.GRAPH);
    }

    private void launchAll() {
        currentGroup = createSelectedGroup();
        String title = getGroupTitle();

        showViewer(currentGroup, title + " - Cayley Table", VizType.CAYLEY_TABLE);

        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        showViewer(currentGroup, title + " - Subgroup Lattice", VizType.LATTICE);

        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        showViewer(currentGroup, title + " - Cayley Graph", VizType.GRAPH);
    }

    private enum VizType {
        CAYLEY_TABLE, LATTICE, GRAPH
    }

    @SuppressWarnings("unchecked")
    private <E extends Element> void showViewer(Group<?> group, String title, VizType type) {
        Group<E> g = (Group<E>) group;
        String fullTitle = getGroupTitle() + " - " + title;

        switch (type) {
            case CAYLEY_TABLE -> CayleyTableViewer.show(g, fullTitle);
            case LATTICE -> SubgroupLatticeViewer.show(g, fullTitle);
            case GRAPH -> CayleyGraphViewer.show(g, fullTitle);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VizLauncher launcher = new VizLauncher();
            launcher.setVisible(true);
        });
    }
}
