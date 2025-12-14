package d021248.group.conjugacy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupAnalyzer;
import d021248.group.subgroup.SubgroupGenerator;

/**
 * Utilities for computing conjugacy classes and related structures.
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>{@code
 * SymmetricGroup s3 = new SymmetricGroup(3);
 * List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s3);
 * System.out.println("S_3 has " + classes.size() + " conjugacy classes");
 * 
 * // Verify class equation: |G| = sum of class sizes
 * int sum = classes.stream().mapToInt(ConjugacyClass::size).sum();
 * System.out.println(sum + " = " + s3.order()); // should be equal
 * }</pre>
 */
public final class ConjugacyAnalyzer {
    private static final String PARENT_NULL_MSG = "parent group must not be null";

    private ConjugacyAnalyzer() {
    }

    /**
     * Compute all conjugacy classes of a group.
     * <p>
     * Partitions the group into disjoint conjugacy classes where two elements
     * are in the same class if they are conjugate (g ~ h iff g = xhx⁻¹ for some x).
     * </p>
     * <p>
     * Properties:
     * </p>
     * <ul>
     * <li>Conjugacy classes partition the group (disjoint union)</li>
     * <li>|G| = Σ |cl(g)| (class equation)</li>
     * <li>Elements in center form singleton classes</li>
     * <li>|cl(g)| divides |G| (by orbit-stabilizer)</li>
     * </ul>
     * 
     * @param parent the parent group
     * @return list of all conjugacy classes
     */
    public static <E extends Element> List<ConjugacyClass<E>> conjugacyClasses(Group<E> parent) {
        Objects.requireNonNull(parent, PARENT_NULL_MSG);

        List<ConjugacyClass<E>> classes = new ArrayList<>();
        Set<E> remaining = new HashSet<>(parent.elements());

        while (!remaining.isEmpty()) {
            E representative = remaining.iterator().next();
            Set<E> classElements = conjugacyClass(parent, representative);

            classes.add(new ConjugacyClass<>(parent, representative, classElements));
            remaining.removeAll(classElements);
        }

        return classes;
    }

    /**
     * Compute the conjugacy class of a single element.
     * <p>
     * Returns cl(g) = {xgx⁻¹ : x ∈ G}
     * </p>
     * 
     * @param parent  the parent group
     * @param element the element whose conjugacy class to compute
     * @return the conjugacy class containing the element
     */
    public static <E extends Element> Set<E> conjugacyClass(Group<E> parent, E element) {
        Objects.requireNonNull(parent, PARENT_NULL_MSG);
        Objects.requireNonNull(element, "element must not be null");

        Set<E> classElements = new HashSet<>();
        for (E x : parent.elements()) {
            E conjugate = conjugate(parent, element, x);
            classElements.add(conjugate);
        }
        return classElements;
    }

    /**
     * Compute conjugate of element by another: xgx⁻¹.
     * 
     * @param parent the parent group
     * @param g      the element to conjugate
     * @param x      the conjugating element
     * @return xgx⁻¹
     */
    public static <E extends Element> E conjugate(Group<E> parent, E g, E x) {
        E xInv = parent.inverse(x);
        return parent.operate(parent.operate(x, g), xInv);
    }

    /**
     * Check if two elements are conjugate.
     * <p>
     * Elements g and h are conjugate if there exists x such that h = xgx⁻¹.
     * </p>
     * 
     * @param parent the parent group
     * @param g      first element
     * @param h      second element
     * @return true if g and h are conjugate
     */
    public static <E extends Element> boolean areConjugate(Group<E> parent, E g, E h) {
        Objects.requireNonNull(parent, PARENT_NULL_MSG);
        Objects.requireNonNull(g, "g must not be null");
        Objects.requireNonNull(h, "h must not be null");

        // Check if h is in the conjugacy class of g
        for (E x : parent.elements()) {
            if (conjugate(parent, g, x).equals(h)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compute the class equation for a group.
     * <p>
     * The class equation is: |G| = |Z(G)| + Σ |cl(g_i)|
     * where the sum is over representatives of non-central classes.
     * </p>
     * <p>
     * Returns a map from class size to count of classes with that size.
     * </p>
     * 
     * @param parent the parent group
     * @return class equation as a map (class size → count)
     */
    public static <E extends Element> Map<Integer, Long> classEquation(Group<E> parent) {
        Objects.requireNonNull(parent, PARENT_NULL_MSG);

        List<ConjugacyClass<E>> classes = conjugacyClasses(parent);
        return classes.stream()
                .collect(Collectors.groupingBy(ConjugacyClass::size, Collectors.counting()));
    }

    /**
     * Verify the class equation holds: |G| = sum of all class sizes.
     * 
     * @param parent the parent group
     * @return true if class equation is satisfied
     */
    public static <E extends Element> boolean verifyClassEquation(Group<E> parent) {
        Objects.requireNonNull(parent, PARENT_NULL_MSG);

        List<ConjugacyClass<E>> classes = conjugacyClasses(parent);
        int sum = classes.stream().mapToInt(ConjugacyClass::size).sum();
        return sum == parent.order();
    }

    /**
     * Find the centralizer of an element (elements that commute with it).
     * <p>
     * C_G(g) = {x ∈ G : xg = gx}
     * </p>
     * <p>
     * By orbit-stabilizer theorem: |cl(g)| × |C_G(g)| = |G|
     * </p>
     * 
     * @param parent  the parent group
     * @param element the element to find centralizer for
     * @return the centralizer subgroup
     */
    public static <E extends Element> Subgroup<E> elementCentralizer(Group<E> parent, E element) {
        Subgroup<E> singletonSubgroup = SubgroupGenerator.generate(parent, Set.of(element));
        return SubgroupAnalyzer.centralizer(parent, singletonSubgroup);
    }

    /**
     * Count the number of conjugacy classes in a group.
     * 
     * @param parent the parent group
     * @return number of conjugacy classes
     */
    public static <E extends Element> int numberOfConjugacyClasses(Group<E> parent) {
        Objects.requireNonNull(parent, PARENT_NULL_MSG);
        return conjugacyClasses(parent).size();
    }
}
