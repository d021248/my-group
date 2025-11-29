package d021248.group.demo;

import java.util.Set;

import d021248.group.Group;
import d021248.group.GroupHelper;
import d021248.group.GroupTableFormatter;
import d021248.group.api.Element;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.strategy.StrategyRegistry;
import d021248.group.strategy.ValidatingGenerationStrategy;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Simple demo showcasing creation of groups, strategy usage, and validation
 * hooks.
 */
public final class Demo {
    public static void main(String[] args) {
        // 1. Build three sample finite groups
        CyclicGroup z12 = new CyclicGroup(12); // Z_12
        SymmetricGroup s4 = new SymmetricGroup(4); // S_4
        DihedralGroup d6 = new DihedralGroup(6); // D_6 (order 12)

        // 2. Use GroupHelper + registered strategies
        System.out.println("=== Strategy Generators ===");
        printGenerators("Z_12", new GroupHelper<>(z12));
        printGenerators("S_4", new GroupHelper<>(s4));
        printGenerators("D_6", new GroupHelper<>(d6));

        // 3. Show closure generation for a chosen generator of Z_12 (e.g., 5)
        var helperZ12 = new GroupHelper<>(z12);
        CyclicElement g5 = new CyclicElement(5, 12);
        Set<CyclicElement> closureG5 = helperZ12.closure(Set.of(g5));
        System.out.println("Closure of <5> in Z_12 size=" + closureG5.size() + " => " + closureG5);
        System.out.println("Is <5> a generator? " + helperZ12.isGenerator(g5));

        // 4. Register a custom validating strategy for S_4 (e.g., use (1 2) and (1 2 3
        // 4))
        StrategyRegistry.register(SymmetricGroup.class, new ValidatingGenerationStrategy<Permutation>() {
            @Override
            public Set<Permutation> generators(Group<Permutation> group) {
                int n = ((SymmetricGroup) group).degree();
                int[] cycle = new int[n];
                for (int i = 0; i < n - 1; i++) {
                    cycle[i] = i + 2;
                }
                cycle[n - 1] = 1; // n-cycle
                Permutation nCycle = new Permutation(cycle);
                int[] trans = new int[n];
                for (int i = 0; i < n; i++) {
                    trans[i] = i + 1;
                }
                trans[0] = 2;
                trans[1] = 1; // (1 2)
                Permutation swap12 = new Permutation(trans);
                return Set.of(nCycle, swap12);
            }

            @Override
            public boolean validates(Group<Permutation> group, Set<Permutation> gens) {
                GroupHelper<Permutation> helper = new GroupHelper<>(group);
                return !gens.isEmpty() && helper.isGeneratingSet(gens);
            }
        });

        var helperS4 = new GroupHelper<>(s4);
        Set<Permutation> customGens = helperS4.strategyGenerators();
        System.out.println("Custom S_4 gens (validated): " + customGens);

        // 5. Demonstrate table formatting (small group only, e.g., Z_6)
        CyclicGroup z6 = new CyclicGroup(6);
        System.out.println("\nMultiplication table for Z_6 (additive mod 6):");
        var cfg = GroupTableFormatter.forGroup(z6).build();
        System.out.println(GroupTableFormatter.toPlainText(cfg));

        // 6. Show registry snapshot
        System.out.println("\nRegistered strategy keys: ");
        System.out.println("  CyclicGroup -> " + StrategyRegistry.lookup(CyclicGroup.class).name());
        System.out.println("  SymmetricGroup -> " + StrategyRegistry.lookup(SymmetricGroup.class).name());
        System.out.println("  DihedralGroup -> " + StrategyRegistry.lookup(DihedralGroup.class).name());
    }

    private static <E extends Element> void printGenerators(String label, GroupHelper<E> helper) {
        var gens = helper.strategyGenerators();
        System.out.println(label + " generators: " + gens);
    }
}
