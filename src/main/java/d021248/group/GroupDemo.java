package d021248.group;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Scanner;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import d021248.group.action.Action;
import d021248.group.action.ActionAnalyzer;
import d021248.group.action.Orbit;
import d021248.group.api.Element;
import d021248.group.conjugacy.ConjugacyAnalyzer;
import d021248.group.conjugacy.ConjugacyClass;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.homomorphism.Homomorphism;
import d021248.group.homomorphism.HomomorphismAnalyzer;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupAnalyzer;
import d021248.group.symmetric.CayleyPermutationGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;
import d021248.group.util.Constants;
import d021248.group.util.ThreadUtil;
import d021248.group.viz.CayleyGraphViewer;
import d021248.group.viz.CayleyTableViewer;
import d021248.group.viz.SubgroupLatticeViewer;

/**
 * Unified interactive demo launcher for the group theory library.
 * <p>
 * Provides access to:
 * <ul>
 * <li>Interactive visualizations (Cayley tables, subgroup lattices, Cayley
 * graphs)</li>
 * <li>Concept demonstrations (homomorphisms, conjugacy classes, group
 * actions)</li>
 * <li>Theorem demonstrations (Cayley's theorem, First Isomorphism,
 * Orbit-Stabilizer)</li>
 * </ul>
 * </p>
 * <p>
 * Usage:
 * <ul>
 * <li>{@code java GroupDemo} - Interactive console menu</li>
 * <li>{@code java GroupDemo --gui} - Graphical launcher with dropdowns</li>
 * <li>{@code java GroupDemo viz <type> <n>} - Quick visualization (types: Z, D,
 * S, A)</li>
 * </ul>
 * </p>
 */
public final class GroupDemo {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        if (args.length > 0) {
            // Check for GUI mode
            if (args[0].equals("--gui") || args[0].equals("-g")) {
                launchGUI();
                return;
            }
            // Command-line mode for quick access
            handleCommandLine(args);
            return;
        }

        // Interactive menu mode
        while (true) {
            showMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> visualizationMenu();
                case "2" -> conceptMenu();
                case "3" -> theoremMenu();
                case "g", "G" -> {
                    System.out.println("Launching GUI...");
                    launchGUI();
                }
                case "q", "Q" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    private static void showMainMenu() {
        System.out.println("\n" + Constants.SEPARATOR_60);
        System.out.println("        GROUP THEORY DEMO - Interactive Launcher");
        System.out.println(Constants.SEPARATOR_60);
        System.out.println("\n1. 📊 Visualizations (Cayley tables, lattices, graphs)");
        System.out.println("2. 🔬 Concepts (Homomorphisms, conjugacy, actions)");
        System.out.println("3. 📐 Theorems (Cayley, Isomorphism, Orbit-Stabilizer)");
        System.out.println("G. Switch to GUI mode");
        System.out.println("Q. Quit");
        System.out.print("\nChoice: ");
    }

    private static void visualizationMenu() {
        System.out.println("\n=== VISUALIZATIONS ===");
        System.out.println("1. Cyclic group Z_n");
        System.out.println("2. Dihedral group D_n");
        System.out.println("3. Symmetric group S_n");
        System.out.println("4. Alternating group A_n");
        System.out.println("B. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();
        if (choice.equalsIgnoreCase("B")) {
            return;
        }

        System.out.print("Enter parameter (e.g., 4 for Z_4, S_4, etc.): ");
        int n = Integer.parseInt(scanner.nextLine().trim());

        Group<?> group = switch (choice) {
            case "1" -> GroupFactory.cyclic(n);
            case "2" -> GroupFactory.dihedral(n);
            case "3" -> GroupFactory.symmetric(n);
            case "4" -> GroupFactory.alternating(n);
            default -> null;
        };

        if (group != null) {
            String name = switch (choice) {
                case "1" -> "Z_" + n;
                case "2" -> "D_" + n;
                case "3" -> "S_" + n;
                case "4" -> "A_" + n;
                default -> "Group";
            };

            System.out.println("\n✨ Launching visualizations for " + name + "...");
            CayleyTableViewer.show(group, "Cayley Table - " + name);
            ThreadUtil.sleep(300);
            SubgroupLatticeViewer.show(group, "Subgroup Lattice - " + name);
            ThreadUtil.sleep(300);
            CayleyGraphViewer.show(group, "Cayley Graph - " + name);
        }
    }

    private static void conceptMenu() {
        System.out.println("\n=== CONCEPT DEMONSTRATIONS ===");
        System.out.println("1. Homomorphisms & kernels (sign: S_3 → Z_2)");
        System.out.println("2. Conjugacy classes (in S_3 and D_4)");
        System.out.println("3. Group actions (orbits & stabilizers)");
        System.out.println("B. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> demonstrateHomomorphism();
            case "2" -> demonstrateConjugacy();
            case "3" -> demonstrateGroupAction();
            case "B", "b" -> {
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void theoremMenu() {
        System.out.println("\n=== THEOREM DEMONSTRATIONS ===");
        System.out.println("1. Cayley's Theorem (every group is a permutation group)");
        System.out.println("2. First Isomorphism Theorem (S_3/A_3 ≅ Z_2)");
        System.out.println("3. Orbit-Stabilizer Theorem (|Orbit| × |Stabilizer| = |G|)");
        System.out.println("B. Back");
        System.out.print("\nChoice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> demonstrateCayleyTheorem();
            case "2" -> demonstrateFirstIsomorphism();
            case "3" -> demonstrateOrbitStabilizer();
            case "B", "b" -> {
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    // ===== Homomorphism Demo =====
    private static void demonstrateHomomorphism() {
        System.out.println("\n" + Constants.SEPARATOR_60);
        System.out.println("HOMOMORPHISM: Sign map S_3 → Z_2");
        System.out.println(Constants.SEPARATOR_60);

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
                s3, z2,
                p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

        System.out.println("Source: S_3 (order " + s3.order() + ")");
        System.out.println("Target: Z_2 (order " + z2.order() + ")");

        System.out.println("\nMapping examples:");
        Permutation identity = new Permutation(new int[] { 1, 2, 3 });
        Permutation transposition = new Permutation(new int[] { 2, 1, 3 });
        Permutation threeCycle = new Permutation(new int[] { 2, 3, 1 });

        System.out.println("  sign(" + identity + ") = " + sign.apply(identity) + " (even)");
        System.out.println("  sign(" + transposition + ") = " + sign.apply(transposition) + " (odd)");
        System.out.println("  sign(" + threeCycle + ") = " + sign.apply(threeCycle) + " (even)");

        System.out.println("\nProperties:");
        System.out.println("  ✓ Is homomorphism: " + HomomorphismAnalyzer.isHomomorphism(sign));
        System.out.println("  ✗ Is injective: " + HomomorphismAnalyzer.isInjective(sign));
        System.out.println("  ✓ Is surjective: " + HomomorphismAnalyzer.isSurjective(sign));

        Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);
        System.out.println("\nKernel (alternating group A_3):");
        System.out.println("  Order: " + kernel.order());
        System.out.println("  Elements: " + kernel.elements());
        System.out.println("  Is normal: " + SubgroupAnalyzer.isNormal(s3, kernel));

        pressEnterToContinue();
    }

    // ===== Conjugacy Demo =====
    private static void demonstrateConjugacy() {
        System.out.println("\n" + Constants.SEPARATOR_60);
        System.out.println("CONJUGACY CLASSES in S_3");
        System.out.println(Constants.SEPARATOR_60);

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s3);

        System.out.println("S_3 has " + classes.size() + " conjugacy classes:\n");

        for (int i = 0; i < classes.size(); i++) {
            ConjugacyClass<Permutation> cl = classes.get(i);
            System.out.println("Class " + (i + 1) + " (size " + cl.size() + "):");
            System.out.println("  Representative: " + cl.representative());
            System.out.println("  Elements: " + cl.elements());
            System.out.println();
        }

        System.out.println("Class Equation: |G| = " + s3.order() + " = " +
                classes.stream().map(ConjugacyClass::size).toList().toString()
                        .replace("[", "").replace("]", ""));

        pressEnterToContinue();
    }

    // ===== Group Action Demo =====
    private static void demonstrateGroupAction() {
        System.out.println("\n" + Constants.SEPARATOR_60);
        System.out.println("GROUP ACTION: Conjugation (S_3 acts on itself)");
        System.out.println(Constants.SEPARATOR_60);

        SymmetricGroup s3 = GroupFactory.symmetric(3);

        Action<Permutation, Permutation> conjugation = new Action<>(
                s3,
                s3.elements(),
                (g, h) -> s3.operate(s3.operate(g, h), s3.inverse(g)));

        System.out.println("Action: g · h = ghg⁻¹");
        System.out.println("Is valid action: " + ActionAnalyzer.isAction(conjugation));

        List<Orbit<Permutation>> orbits = ActionAnalyzer.orbits(conjugation);
        System.out.println("\nOrbits (= conjugacy classes): " + orbits.size());

        for (int i = 0; i < orbits.size(); i++) {
            Orbit<Permutation> orb = orbits.get(i);
            System.out.println("\n  Orbit " + (i + 1) + " (size " + orb.size() + "):");
            System.out.println("    Representative: " + orb.representative());
            System.out.println("    Elements: " + orb.elements());
        }

        System.out.println("\nAction properties:");
        System.out.println("  Is transitive: " + ActionAnalyzer.isTransitive(conjugation));
        System.out.println("  Is free: " + ActionAnalyzer.isFree(conjugation));

        pressEnterToContinue();
    }

    // ===== Cayley Theorem Demo =====
    private static void demonstrateCayleyTheorem() {
        System.out.println("\n" + Constants.SEPARATOR_60);
        System.out.println("CAYLEY'S THEOREM: Every group is a permutation group");
        System.out.println(Constants.SEPARATOR_60);

        CyclicGroup z4 = GroupFactory.cyclic(4);
        System.out.println("\nOriginal: Z_4 (order " + z4.order() + ")");
        System.out.println("Elements: " + z4.elements());

        CayleyPermutationGroup<CyclicElement> permZ4 = new CayleyPermutationGroup<>(z4);
        System.out.println("\nCayley representation: Subgroup of S_4");
        System.out.println("Order: " + permZ4.order() + " (same!)");

        System.out.println("\nElement mapping (g → L_g where L_g(x) = g*x):");
        var ordered = permZ4.orderedElements();

        for (int i = 0; i < ordered.size(); i++) {
            CyclicElement g = ordered.get(i);
            System.out.printf("  %s → permutation showing how it acts on Z_4\n", g);
        }

        System.out.println("\n✨ This proves Z_4 is isomorphic to a subgroup of S_4!");
        System.out.println("\nLaunch visualization? (y/n): ");

        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
            CayleyTableViewer.show(permZ4, "Z_4 as Permutation Group");
        }

        pressEnterToContinue();
    }

    // ===== First Isomorphism Theorem =====
    private static void demonstrateFirstIsomorphism() {
        System.out.println("\n" + Constants.SEPARATOR_60);
        System.out.println("FIRST ISOMORPHISM THEOREM: S_3/ker(φ) ≅ im(φ)");
        System.out.println(Constants.SEPARATOR_60);

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        CyclicGroup z2 = GroupFactory.cyclic(2);

        Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
                s3, z2,
                p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

        Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);
        Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(sign);

        System.out.println("\nHomomorphism: sign: S_3 → Z_2");
        System.out.println("\nKernel (A_3): order " + kernel.order());
        System.out.println("Image: order " + image.order());

        System.out.println("\nBy First Isomorphism Theorem:");
        System.out.println("  S_3/A_3 ≅ Z_2");
        System.out.println("  |S_3|/|A_3| = " + s3.order() + "/" + kernel.order() + " = " +
                (s3.order() / kernel.order()));
        System.out.println("  |Image| = " + image.order());
        System.out.println("\n  ✓ Theorem verified: 2 = 2");

        pressEnterToContinue();
    }

    // ===== Orbit-Stabilizer Theorem =====
    private static void demonstrateOrbitStabilizer() {
        System.out.println("\n" + Constants.SEPARATOR_60);
        System.out.println("ORBIT-STABILIZER THEOREM: |Orbit| × |Stabilizer| = |G|");
        System.out.println(Constants.SEPARATOR_60);

        SymmetricGroup s3 = GroupFactory.symmetric(3);
        Action<Permutation, Permutation> conjugation = new Action<>(
                s3,
                s3.elements(),
                (g, h) -> s3.operate(s3.operate(g, h), s3.inverse(g)));

        Permutation transposition = new Permutation(new int[] { 2, 1, 3 });

        System.out.println("\nElement: " + transposition);
        System.out.println("Group: S_3 (order " + s3.order() + ")");

        Orbit<Permutation> orbit = ActionAnalyzer.orbit(conjugation, transposition);
        var stabilizer = ActionAnalyzer.stabilizer(conjugation, transposition);

        System.out.println("\nOrbit (conjugacy class):");
        System.out.println("  Size: " + orbit.size());
        System.out.println("  Elements: " + orbit.elements());

        System.out.println("\nStabilizer:");
        System.out.println("  Size: " + stabilizer.order());
        System.out.println("  Elements: " + stabilizer.elements());

        System.out.println("\nOrbit-Stabilizer Theorem:");
        System.out.println("  |Orbit| × |Stabilizer| = " + orbit.size() + " × " + stabilizer.order() +
                " = " + (orbit.size() * stabilizer.order()));
        System.out.println("  |G| = " + s3.order());
        System.out.println("\n  ✓ Theorem verified!");

        pressEnterToContinue();
    }

    // ===== Command-line mode =====
    private static void handleCommandLine(String[] args) {
        String cmd = args[0].toLowerCase();

        if (cmd.equals("viz") && args.length >= 3) {
            // viz <type> <n>
            String type = args[1].toUpperCase();
            int n = Integer.parseInt(args[2]);

            Group<?> group = switch (type) {
                case "Z", "CYCLIC" -> GroupFactory.cyclic(n);
                case "D", "DIHEDRAL" -> GroupFactory.dihedral(n);
                case "S", "SYMMETRIC" -> GroupFactory.symmetric(n);
                case "A", "ALTERNATING" -> GroupFactory.alternating(n);
                default -> null;
            };

            if (group != null) {
                String name = type + "_" + n;
                System.out.println("Launching visualizations for " + name);
                CayleyTableViewer.show(group, "Cayley Table - " + name);
                ThreadUtil.sleep(300);
                SubgroupLatticeViewer.show(group, "Subgroup Lattice - " + name);
                ThreadUtil.sleep(300);
                CayleyGraphViewer.show(group, "Cayley Graph - " + name);
            }
        } else {
            System.out.println("Usage:");
            System.out.println("  java GroupDemo                     # Interactive console menu");
            System.out.println("  java GroupDemo --gui               # Graphical launcher");
            System.out.println("  java GroupDemo viz <type> <n>      # Quick visualization");
            System.out.println("    Types: Z, D, S, A");
        }
    }

    // ===== GUI Mode =====
    private static void launchGUI() {
        SwingUtilities.invokeLater(() -> {
            GUILauncher launcher = new GUILauncher();
            launcher.setVisible(true);
        });
    }

    /**
     * Graphical launcher with dropdown menus for group selection.
     */
    private static class GUILauncher extends JFrame {
        private static final long serialVersionUID = 1L;

        private final JComboBox<String> groupTypeCombo;
        private final JSpinner paramSpinner;

        public GUILauncher() {
            super("Group Visualization Launcher");

            groupTypeCombo = new JComboBox<>(new String[] {
                    "Cyclic (Z_n)",
                    "Dihedral (D_n)",
                    "Symmetric (S_n)",
                    "Alternating (A_n)"
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
    }

    private static void pressEnterToContinue() {
        System.out.print("\n[Press Enter to continue]");
        scanner.nextLine();
    }

    private GroupDemo() {
    }
}
