package d021248.group.automorphism;

import java.util.HashSet;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.homomorphism.GroupHomomorphism;
import d021248.group.homomorphism.Homomorphism;
import d021248.group.homomorphism.HomomorphismAnalyzer;

/**
 * Utility class for computing and analyzing automorphisms.
 */
public final class AutomorphismAnalyzer {

    private AutomorphismAnalyzer() {
    }

    /**
     * Verify that a mapping is a valid automorphism.
     * <p>
     * An automorphism must be an isomorphism G → G.
     *
     * @param auto the automorphism to verify
     * @return true if it's a valid automorphism
     */
    public static <E extends Element> boolean isAutomorphism(Automorphism<E> auto) {
        Homomorphism<E, E> homo = new Homomorphism<>(auto.group(), auto.group(), auto);
        return HomomorphismAnalyzer.isHomomorphism(homo) &&
                HomomorphismAnalyzer.isIsomorphism(homo);
    }

    /**
     * Compute the inner automorphism induced by an element.
     * <p>
     * Inn_g(h) = ghg⁻¹ (conjugation by g)
     *
     * @param group the group
     * @param g     the element inducing conjugation
     * @return inner automorphism
     */
    public static <E extends Element> Automorphism<E> innerAutomorphism(Group<E> group, E g) {
        E gInv = group.inverse(g);
        GroupHomomorphism<E, E> mapping = h -> group.operate(group.operate(g, h), gInv);
        return new Automorphism<>(group, mapping);
    }

    /**
     * Compute all inner automorphisms Inn(G).
     * <p>
     * Inn(G) = {Inn_g | g ∈ G}
     *
     * @param group the group
     * @return set of all inner automorphisms
     */
    public static <E extends Element> Set<Automorphism<E>> innerAutomorphisms(Group<E> group) {
        Set<Automorphism<E>> inner = new HashSet<>();

        for (E g : group.elements()) {
            inner.add(innerAutomorphism(group, g));
        }

        return inner;
    }

    /**
     * Check if two automorphisms are equal (same mapping).
     *
     * @param auto1 first automorphism
     * @param auto2 second automorphism
     * @return true if they map all elements identically
     */
    public static <E extends Element> boolean areEqual(Automorphism<E> auto1, Automorphism<E> auto2) {
        if (!auto1.group().equals(auto2.group())) {
            return false;
        }

        for (E element : auto1.group().elements()) {
            if (!auto1.apply(element).equals(auto2.apply(element))) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compose two automorphisms: (φ ∘ ψ)(x) = φ(ψ(x)).
     *
     * @param phi first automorphism
     * @param psi second automorphism
     * @return composition
     */
    public static <E extends Element> Automorphism<E> compose(Automorphism<E> phi, Automorphism<E> psi) {
        if (!phi.group().equals(psi.group())) {
            throw new IllegalArgumentException("Automorphisms must act on the same group");
        }

        GroupHomomorphism<E, E> mapping = e -> phi.apply(psi.apply(e));
        return new Automorphism<>(phi.group(), mapping);
    }

    /**
     * Compute the identity automorphism.
     *
     * @param group the group
     * @return identity automorphism
     */
    public static <E extends Element> Automorphism<E> identity(Group<E> group) {
        GroupHomomorphism<E, E> mapping = e -> e;
        return new Automorphism<>(group, mapping);
    }

    /**
     * Compute the inverse of an automorphism.
     * <p>
     * Since automorphisms are bijective, the inverse exists.
     *
     * @param auto the automorphism
     * @return inverse automorphism
     */
    public static <E extends Element> Automorphism<E> inverse(Automorphism<E> auto) {
        Group<E> group = auto.group();

        GroupHomomorphism<E, E> mapping = target -> {
            for (E source : group.elements()) {
                if (auto.apply(source).equals(target)) {
                    return source;
                }
            }
            throw new IllegalStateException("Automorphism is not surjective");
        };

        return new Automorphism<>(group, mapping);
    }

    /**
     * Check if an automorphism is inner.
     * <p>
     * An automorphism φ is inner if φ = Inn_g for some g ∈ G.
     *
     * @param auto the automorphism
     * @return true if inner
     */
    public static <E extends Element> boolean isInner(Automorphism<E> auto) {
        Set<Automorphism<E>> innerAutos = innerAutomorphisms(auto.group());

        for (Automorphism<E> inner : innerAutos) {
            if (areEqual(auto, inner)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Count the number of distinct inner automorphisms.
     * <p>
     * |Inn(G)| = |G| / |Z(G)| where Z(G) is the center.
     *
     * @param group the group
     * @return number of distinct inner automorphisms
     */
    public static <E extends Element> int countDistinctInnerAutomorphisms(Group<E> group) {
        Set<Automorphism<E>> innerSet = innerAutomorphisms(group);
        Set<Automorphism<E>> distinct = new HashSet<>();

        for (Automorphism<E> auto : innerSet) {
            boolean found = false;
            for (Automorphism<E> existing : distinct) {
                if (areEqual(auto, existing)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                distinct.add(auto);
            }
        }

        return distinct.size();
    }

    /**
     * Check if the group is complete (Z(G) = {e} and every automorphism is inner).
     * <p>
     * Complete groups satisfy: Aut(G) = Inn(G) and Z(G) = {e}.
     * This implementation only checks if Z(G) = {e}.
     *
     * @param group the group
     * @return true if center is trivial
     */
    public static <E extends Element> boolean isComplete(Group<E> group) {
        E identity = group.identity();

        for (E g : group.elements()) {
            if (g.equals(identity)) {
                continue;
            }

            // Check if g commutes with all elements
            boolean inCenter = true;
            for (E h : group.elements()) {
                if (!group.operate(g, h).equals(group.operate(h, g))) {
                    inCenter = false;
                    break;
                }
            }

            if (inCenter) {
                return false; // Found non-identity element in center
            }
        }

        return true; // Center is trivial
    }
}
