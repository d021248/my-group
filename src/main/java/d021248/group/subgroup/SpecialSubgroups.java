package d021248.group.subgroup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.util.Constants;

/**
 * Utilities for computing special subgroups.
 * <p>
 * This class provides methods for computing the center, commutator subgroup,
 * Frattini subgroup, and maximal subgroups of a group.
 * </p>
 */
public final class SpecialSubgroups {

    private SpecialSubgroups() {
        // Utility class
    }

    /**
     * Compute the center of a group.
     * <p>
     * Z(G) = {g ∈ G | gx = xg for all x ∈ G}
     * </p>
     *
     * @param parent the group
     * @return the center as a subgroup
     */
    public static <E extends Element> Subgroup<E> center(Group<E> parent) {
        Objects.requireNonNull(parent, Constants.NULL_PARENT);

        Set<E> center = new HashSet<>();
        Set<E> elements = parent.elements();

        for (E g : elements) {
            boolean isInCenter = true;
            for (E x : elements) {
                if (!parent.operate(g, x).equals(parent.operate(x, g))) {
                    isInCenter = false;
                    break;
                }
            }
            if (isInCenter) {
                center.add(g);
            }
        }

        return SubgroupGenerator.generate(parent, center);
    }

    /**
     * Compute the commutator subgroup (derived subgroup) of a group.
     * <p>
     * [G,G] = ⟨[g,h] | g,h ∈ G⟩ where [g,h] = g⁻¹h⁻¹gh
     * </p>
     *
     * @param parent the group
     * @return the commutator subgroup
     */
    public static <E extends Element> Subgroup<E> commutatorSubgroup(Group<E> parent) {
        Objects.requireNonNull(parent, Constants.NULL_PARENT);

        Set<E> commutators = new HashSet<>();
        Set<E> elements = parent.elements();

        // Generate all commutators [g,h] = g⁻¹h⁻¹gh
        for (E g : elements) {
            for (E h : elements) {
                E gInv = parent.inverse(g);
                E hInv = parent.inverse(h);
                E commutator = parent.operate(parent.operate(gInv, hInv),
                        parent.operate(g, h));
                commutators.add(commutator);
            }
        }

        // Generate subgroup from all commutators
        return SubgroupGenerator.generate(parent, commutators);
    }

    /**
     * Compute the Frattini subgroup of a group.
     * <p>
     * Φ(G) = intersection of all maximal subgroups
     * </p>
     *
     * @param parent the group
     * @return the Frattini subgroup
     */
    public static <E extends Element> Subgroup<E> frattiniSubgroup(Group<E> parent) {
        Objects.requireNonNull(parent, Constants.NULL_PARENT);

        List<Subgroup<E>> maximalSubs = maximalSubgroups(parent);

        if (maximalSubs.isEmpty()) {
            // No maximal subgroups (e.g., group of prime order)
            // Φ(G) = {e}
            return SubgroupGenerator.generate(parent, Set.of(parent.identity()));
        }

        // Intersect all maximal subgroups
        Set<E> intersection = new HashSet<>(maximalSubs.get(0).elements());
        for (int i = 1; i < maximalSubs.size(); i++) {
            intersection.retainAll(maximalSubs.get(i).elements());
        }

        return SubgroupGenerator.generate(parent, intersection);
    }

    /**
     * Find all maximal subgroups of a finite group.
     * <p>
     * A subgroup M is maximal if M ≠ G and there is no subgroup H with M ⊂ H ⊂ G.
     * </p>
     *
     * @param parent the group
     * @return list of all maximal subgroups
     */
    public static <E extends Element> List<Subgroup<E>> maximalSubgroups(Group<E> parent) {
        Objects.requireNonNull(parent, Constants.NULL_PARENT);

        if (parent.order() > Constants.MAX_SUBGROUP_ENUMERATION_SIZE) {
            throw new IllegalArgumentException(
                    "Finding maximal subgroups is only practical for small groups (order ≤ "
                            + Constants.MAX_SUBGROUP_ENUMERATION_SIZE + ")");
        }

        List<Subgroup<E>> allSubs = SubgroupGenerator.allSubgroups(parent);
        List<Subgroup<E>> maximalSubs = new ArrayList<>();

        int groupOrder = parent.order();

        for (Subgroup<E> candidate : allSubs) {
            if (candidate.order() == groupOrder) {
                continue; // Skip the whole group
            }

            boolean isMaximal = true;
            for (Subgroup<E> other : allSubs) {
                // Check if there exists H with candidate ⊂ H ⊂ G
                if (other.order() > candidate.order() && other.order() < groupOrder) {
                    if (candidate.elements().stream().allMatch(e -> other.elements().contains(e))) {
                        isMaximal = false;
                        break;
                    }
                }
            }

            if (isMaximal) {
                maximalSubs.add(candidate);
            }
        }

        return maximalSubs;
    }
}
