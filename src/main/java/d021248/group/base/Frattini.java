package d021248.group.base;

import java.util.HashSet;
import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Group;

/**
 * Utility class to compute the Frattini subgroup of a group.
 */
public class Frattini {
    private Frattini() {
    }

    /**
     * Computes the Frattini subgroup of the given group.
     * The Frattini subgroup is the intersection of all maximal subgroups.
     *
     * @param group the group
     * @return the set of elements in the Frattini subgroup
     */
    public static <T extends Element> Set<T> compute(Group<T> group) {
        var maximalSubgroups = MaximalSubgroup.compute(group);
        if (maximalSubgroups.isEmpty()) {
            return Set.of();
        }
        Set<T> intersection = new HashSet<>(maximalSubgroups.get(0));
        for (var subgroup : maximalSubgroups) {
            intersection.retainAll(subgroup);
        }
        return Set.copyOf(intersection);
    }
}
