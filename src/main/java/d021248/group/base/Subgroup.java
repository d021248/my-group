
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BinaryOperator;

import d021248.group.api.SimpleGroup;

/**
 * Calculates all subgroups of a given finite group.
 */
public class Subgroup<T> {
    private final SimpleGroup<T> group;
    private final List<Set<T>> subgroups;

    public Subgroup(SimpleGroup<T> group) {
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
            if (isSubgroup(subset, group)) {
                result.add(subset);
            }
        }
        return result;
    }

    /**
     * Checks if a subset is a subgroup.
     */
    private boolean isSubgroup(Set<T> subset, SimpleGroup<T> group) {
        if (subset.isEmpty())
            return false;
        BinaryOperator<T> op = group::operate;
        // Closure
        for (T a : subset) {
            for (T b : subset) {
                T prod = op.apply(a, b);
                if (!subset.contains(prod))
                    return false;
            }
        }
        // Inverses: for each a, find b in subset such that op.apply(a, b) == identity
        // && op.apply(b, a) == identity
        T identity = findIdentity(subset, op);
        for (T a : subset) {
            boolean hasInverse = false;
            for (T b : subset) {
                if (op.apply(a, b).equals(identity) && op.apply(b, a).equals(identity)) {
                    hasInverse = true;
                    break;
                }
            }
            if (!hasInverse)
                return false;
        }
        return true;
    }

    /**
     * Finds the identity element in the subset (if any).
     */
    private T findIdentity(Set<T> subset, BinaryOperator<T> op) {
        for (T e : subset) {
            boolean isIdentity = true;
            for (T x : subset) {
                if (!op.apply(e, x).equals(x) || !op.apply(x, e).equals(x)) {
                    isIdentity = false;
                    break;
                }
            }
            if (isIdentity)
                return e;
        }
        return null; // Not found
    }
}
