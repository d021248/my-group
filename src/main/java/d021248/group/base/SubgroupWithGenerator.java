package d021248.group.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Group;

/**
 * Calculates all subgroups of a given group using generators.
 * Suitable for large groups.
 */
public class SubgroupWithGenerator<T extends Element> {
    private final Group<T> group;
    private final List<Set<T>> subgroups;

    public SubgroupWithGenerator(Group<T> group) {
        this.group = group;
        this.subgroups = calculateSubgroupsWithGenerators();
    }

    /**
     * Returns all subgroups as sets of elements.
     */
    public List<Set<T>> getSubgroups() {
        return subgroups;
    }

    /**
     * Calculates all subgroups by generating them from all possible non-empty
     * subsets of generators.
     * For large groups, this is much more efficient than brute-force subset
     * checking.
     */
    private List<Set<T>> calculateSubgroupsWithGenerators() {
        List<T> elements = new ArrayList<>(group.elements());
        List<Set<T>> result = new ArrayList<>();
        int n = elements.size();
        // For very large groups, only use small generator sets
        int maxGen = n > 20 ? 2 : Math.min(4, n); // Use pairs for large groups, up to 4 for small
        // Generate all non-empty subsets of up to maxGen elements
        for (int k = 1; k <= maxGen; k++) {
            List<int[]> combinations = combinations(n, k);
            for (int[] idxs : combinations) {
                Set<T> gens = new HashSet<>();
                for (int idx : idxs) {
                    gens.add(elements.get(idx));
                }
                Set<T> subgroup = Generator.generate(gens, group.operation());
                // Avoid duplicates
                if (!containsSet(result, subgroup)) {
                    result.add(subgroup);
                }
            }
        }
        return result;
    }

    // Utility: generate all k-combinations of n elements
    private List<int[]> combinations(int n, int k) {
        List<int[]> result = new ArrayList<>();
        int[] comb = new int[k];
        combineHelper(result, comb, 0, n - 1, 0);
        return result;
    }

    private void combineHelper(List<int[]> result, int[] comb, int start, int end, int index) {
        if (index == comb.length) {
            result.add(comb.clone());
            return;
        }
        for (int i = start; i <= end && end - i + 1 >= comb.length - index; i++) {
            comb[index] = i;
            combineHelper(result, comb, i + 1, end, index + 1);
        }
    }

    // Utility: check if a set is already in the list (by content)
    private boolean containsSet(List<Set<T>> sets, Set<T> candidate) {
        for (Set<T> s : sets) {
            if (s.size() == candidate.size() && s.containsAll(candidate)) {
                return true;
            }
        }
        return false;
    }
}
