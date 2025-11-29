package d021248.group.homomorphism;

import java.util.HashSet;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;

/**
 * Utility class for analyzing group homomorphisms.
 */
public final class HomomorphismAnalyzer {

    private HomomorphismAnalyzer() {
    }

    /**
     * Verify that a mapping is a valid group homomorphism.
     * <p>
     * Checks that φ(g₁ ∘ g₂) = φ(g₁) ∘ φ(g₂) for all g₁, g₂ in the source group.
     *
     * @param phi the homomorphism to verify
     * @return true if φ preserves the group operation
     */
    public static <E1 extends Element, E2 extends Element> boolean isHomomorphism(
            Homomorphism<E1, E2> phi) {
        Group<E1> source = phi.source();
        Group<E2> target = phi.target();

        Set<E1> elements = source.elements();

        // Check identity: φ(e_G) = e_H
        E1 sourceIdentity = source.identity();
        E2 targetIdentity = target.identity();
        if (!phi.apply(sourceIdentity).equals(targetIdentity)) {
            return false;
        }

        // Check operation preservation: φ(g₁ ∘ g₂) = φ(g₁) ∘ φ(g₂)
        for (E1 g1 : elements) {
            for (E1 g2 : elements) {
                E1 product = source.operate(g1, g2);
                E2 imageProduct = phi.apply(product);
                E2 productImages = target.operate(phi.apply(g1), phi.apply(g2));

                if (!imageProduct.equals(productImages)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Compute the kernel of a homomorphism.
     * <p>
     * ker(φ) = {g ∈ G | φ(g) = e_H}
     *
     * @param phi the homomorphism
     * @return kernel as a subgroup of the source
     */
    public static <E1 extends Element, E2 extends Element> Subgroup<E1> kernel(
            Homomorphism<E1, E2> phi) {
        Group<E1> source = phi.source();
        Group<E2> target = phi.target();

        E2 targetIdentity = target.identity();
        Set<E1> kernelElements = new HashSet<>();

        for (E1 element : source.elements()) {
            if (phi.apply(element).equals(targetIdentity)) {
                kernelElements.add(element);
            }
        }

        return SubgroupGenerator.generate(source, kernelElements);
    }

    /**
     * Compute the image of a homomorphism.
     * <p>
     * im(φ) = {φ(g) | g ∈ G}
     *
     * @param phi the homomorphism
     * @return image as a subgroup of the target
     */
    public static <E1 extends Element, E2 extends Element> Subgroup<E2> image(
            Homomorphism<E1, E2> phi) {
        Group<E1> source = phi.source();
        Group<E2> target = phi.target();

        Set<E2> imageElements = new HashSet<>();

        for (E1 element : source.elements()) {
            imageElements.add(phi.apply(element));
        }

        return SubgroupGenerator.generate(target, imageElements);
    }

    /**
     * Check if a homomorphism is injective (one-to-one).
     * <p>
     * φ is injective ⟺ ker(φ) = {e_G}
     *
     * @param phi the homomorphism
     * @return true if φ is injective
     */
    public static <E1 extends Element, E2 extends Element> boolean isInjective(
            Homomorphism<E1, E2> phi) {
        Subgroup<E1> ker = kernel(phi);
        return ker.order() == 1;
    }

    /**
     * Check if a homomorphism is surjective (onto).
     * <p>
     * φ is surjective ⟺ im(φ) = H
     *
     * @param phi the homomorphism
     * @return true if φ is surjective
     */
    public static <E1 extends Element, E2 extends Element> boolean isSurjective(
            Homomorphism<E1, E2> phi) {
        Group<E2> target = phi.target();

        Subgroup<E2> im = image(phi);
        return im.order() == target.order();
    }

    /**
     * Check if a homomorphism is an isomorphism (bijective).
     * <p>
     * φ is an isomorphism ⟺ φ is injective and surjective
     *
     * @param phi the homomorphism
     * @return true if φ is an isomorphism
     */
    public static <E1 extends Element, E2 extends Element> boolean isIsomorphism(
            Homomorphism<E1, E2> phi) {
        return isInjective(phi) && isSurjective(phi);
    }

    /**
     * Apply First Isomorphism Theorem: G/ker(φ) ≅ im(φ).
     * <p>
     * Returns the order of both G/ker(φ) and im(φ), which must be equal.
     *
     * @param phi the homomorphism
     * @return order of the quotient group (equals order of image)
     */
    public static <E1 extends Element, E2 extends Element> int firstIsomorphismTheorem(
            Homomorphism<E1, E2> phi) {
        Group<E1> source = phi.source();

        Subgroup<E1> ker = kernel(phi);
        Subgroup<E2> im = image(phi);

        // By Lagrange: |G/ker(φ)| = |G| / |ker(φ)|
        int quotientOrder = source.order() / ker.order();

        // Verify First Isomorphism Theorem
        if (quotientOrder != im.order()) {
            throw new IllegalStateException(
                    "First Isomorphism Theorem violated: |G/ker(φ)| ≠ |im(φ)|");
        }

        return quotientOrder;
    }

    /**
     * Compose two homomorphisms: (ψ ∘ φ)(g) = ψ(φ(g)).
     *
     * @param phi first homomorphism G → H
     * @param psi second homomorphism H → K
     * @return composition ψ ∘ φ: G → K
     */
    public static <E1 extends Element, E2 extends Element, E3 extends Element> Homomorphism<E1, E3> compose(
            Homomorphism<E1, E2> phi,
            Homomorphism<E2, E3> psi) {
        if (!phi.target().equals(psi.source())) {
            throw new IllegalArgumentException("Target of phi must equal source of psi");
        }

        GroupHomomorphism<E1, E3> composition = e1 -> psi.apply(phi.apply(e1));

        return new Homomorphism<>(phi.source(), psi.target(), composition);
    }
}
