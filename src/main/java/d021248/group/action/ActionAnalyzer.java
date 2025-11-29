package d021248.group.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;

/**
 * Utility class for analyzing group actions.
 */
public final class ActionAnalyzer {

    private ActionAnalyzer() {
    }

    /**
     * Verify that a mapping is a valid group action.
     * <p>
     * Checks:
     * <ul>
     *   <li>Identity: e · x = x for all x ∈ X</li>
     *   <li>Compatibility: (g₁g₂) · x = g₁ · (g₂ · x) for all g₁, g₂, x</li>
     * </ul>
     *
     * @param action the action to verify
     * @return true if it's a valid group action
     */
    public static <E extends Element, X> boolean isAction(Action<E, X> action) {
        Group<E> group = action.group();
        Set<X> set = action.set();
        E identity = group.identity();

        // Check identity: e · x = x
        for (X x : set) {
            if (!action.act(identity, x).equals(x)) {
                return false;
            }
        }

        // Check compatibility: (g₁g₂) · x = g₁ · (g₂ · x)
        for (E g1 : group.elements()) {
            for (E g2 : group.elements()) {
                E product = group.operate(g1, g2);
                for (X x : set) {
                    X productAction = action.act(product, x);
                    X compatibleAction = action.act(g1, action.act(g2, x));
                    if (!productAction.equals(compatibleAction)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Compute the orbit of an element under the group action.
     * <p>
     * orb(x) = {g · x | g ∈ G}
     *
     * @param action the group action
     * @param x the element
     * @return orbit of x
     */
    public static <E extends Element, X> Orbit<X> orbit(Action<E, X> action, X x) {
        Set<X> orbitElements = new HashSet<>();

        for (E g : action.group().elements()) {
            orbitElements.add(action.act(g, x));
        }

        return new Orbit<>(x, orbitElements);
    }

    /**
     * Partition the set into disjoint orbits.
     *
     * @param action the group action
     * @return list of all orbits
     */
    public static <E extends Element, X> List<Orbit<X>> orbits(Action<E, X> action) {
        Set<X> remaining = new HashSet<>(action.set());
        List<Orbit<X>> orbits = new ArrayList<>();

        while (!remaining.isEmpty()) {
            X x = remaining.iterator().next();
            Orbit<X> orb = orbit(action, x);
            orbits.add(orb);
            remaining.removeAll(orb.elements());
        }

        return orbits;
    }

    /**
     * Compute the stabilizer of an element.
     * <p>
     * Stab(x) = {g ∈ G | g · x = x}
     *
     * @param action the group action
     * @param x the element
     * @return stabilizer subgroup
     */
    public static <E extends Element, X> Subgroup<E> stabilizer(Action<E, X> action, X x) {
        Group<E> group = action.group();
        Set<E> stabilizerElements = new HashSet<>();

        for (E g : group.elements()) {
            if (action.act(g, x).equals(x)) {
                stabilizerElements.add(g);
            }
        }

        return SubgroupGenerator.generate(group, stabilizerElements);
    }

    /**
     * Verify the Orbit-Stabilizer Theorem: |orb(x)| × |Stab(x)| = |G|.
     *
     * @param action the group action
     * @param x the element
     * @return true if theorem holds
     */
    public static <E extends Element, X> boolean verifyOrbitStabilizer(Action<E, X> action, X x) {
        Orbit<X> orb = orbit(action, x);
        Subgroup<E> stab = stabilizer(action, x);
        int groupOrder = action.group().order();

        return orb.size() * stab.order() == groupOrder;
    }

    /**
     * Check if the action is transitive (single orbit).
     *
     * @param action the group action
     * @return true if G acts transitively on X
     */
    public static <E extends Element, X> boolean isTransitive(Action<E, X> action) {
        return orbits(action).size() == 1;
    }

    /**
     * Check if the action is free (all stabilizers are trivial).
     *
     * @param action the group action
     * @return true if Stab(x) = {e} for all x
     */
    public static <E extends Element, X> boolean isFree(Action<E, X> action) {
        for (X x : action.set()) {
            Subgroup<E> stab = stabilizer(action, x);
            if (stab.order() != 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compute fixed points: elements where g · x = x.
     *
     * @param action the group action
     * @param g the group element
     * @return set of fixed points
     */
    public static <E extends Element, X> Set<X> fixedPoints(Action<E, X> action, E g) {
        Set<X> fixed = new HashSet<>();

        for (X x : action.set()) {
            if (action.act(g, x).equals(x)) {
                fixed.add(x);
            }
        }

        return fixed;
    }

    /**
     * Count fixed points for each group element.
     *
     * @param action the group action
     * @return map from group element to number of fixed points
     */
    public static <E extends Element, X> Map<E, Integer> fixedPointCounts(Action<E, X> action) {
        Map<E, Integer> counts = new HashMap<>();

        for (E g : action.group().elements()) {
            counts.put(g, fixedPoints(action, g).size());
        }

        return counts;
    }

    /**
     * Apply Burnside's Lemma: |X/G| = (1/|G|) × Σ |Fix(g)|.
     * <p>
     * Returns the number of orbits.
     *
     * @param action the group action
     * @return number of orbits
     */
    public static <E extends Element, X> int burnsideLemma(Action<E, X> action) {
        int totalFixed = 0;
        Group<E> group = action.group();

        for (E g : group.elements()) {
            totalFixed += fixedPoints(action, g).size();
        }

        return totalFixed / group.order();
    }
}
