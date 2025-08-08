
package d021248.group.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Group;
import d021248.group.api.Operation;

/**
 * Calculates all subgroups of a given group.
 */
public class Subgroup<T extends Element> {
    private final Group<T> group;
    private final List<Set<T>> subgroups;

    public Subgroup(Group<T> group) {
        this.group = group;
        this.subgroups = calculateSubgroups();
    }

    /**
     * Returns all subgroups as sets of elements.
     */
    public List<Set<T>> getSubgroups() {
        return subgroups;
    }

    /**
     * Calculates all subgroups of the group.
     */
    private List<Set<T>> calculateSubgroups() {
        List<T> elements = new ArrayList<>(group.elements());
        List<Set<T>> result = new ArrayList<>();
        int n = elements.size();
        // Generate all non-empty subsets
        for (int mask = 1; mask < (1 << n); mask++) {
            Set<T> subset = new HashSet<>();
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(elements.get(i));
                }
            }
            if (isSubgroup(subset)) {
                result.add(subset);
            }
        }
        return result;
    }

    /**
     * Checks if a subset is a subgroup.
     */
    private boolean isSubgroup(Set<T> subset) {
        if (subset.isEmpty())
            return false;
        Operation<T> op = group.operation();
        // Closure
        for (T a : subset) {
            for (T b : subset) {
                T prod = op.apply(a, b);
                if (!subset.contains(prod))
                    return false;
            }
        }
        // Inverses
        for (T a : subset) {
            T inv = (T) a.inverse();
            if (!subset.contains(inv))
                return false;
        }
        return true;
    }
}
