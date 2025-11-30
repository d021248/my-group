/**
 * Group homomorphisms and morphism analysis.
 * <p>
 * This package provides tools for working with structure-preserving maps
 * between groups.
 * A group homomorphism φ: G → H is a function that preserves the group
 * operation:
 * 
 * <pre>
 *     φ(g₁ ∘ g₂) = φ(g₁) ∘ φ(g₂) for all g₁, g₂ ∈ G
 * </pre>
 *
 * <h2>Key Concepts</h2>
 * <ul>
 * <li><b>Homomorphism</b>: Structure-preserving map between groups</li>
 * <li><b>Kernel</b>: ker(φ) = {g ∈ G | φ(g) = e_H}, always a normal
 * subgroup</li>
 * <li><b>Image</b>: im(φ) = {φ(g) | g ∈ G}, subgroup of target</li>
 * <li><b>Injective</b>: φ is one-to-one ⟺ ker(φ) = {e_G}</li>
 * <li><b>Surjective</b>: φ is onto ⟺ im(φ) = H</li>
 * <li><b>Isomorphism</b>: Bijective homomorphism (groups are "the same")</li>
 * </ul>
 *
 * <h2>First Isomorphism Theorem</h2>
 * <p>
 * For any homomorphism φ: G → H:
 * 
 * <pre>
 *     G/ker(φ) ≅ im(φ)
 * </pre>
 * 
 * This fundamental theorem relates quotient groups to images.
 *
 * <h2>Examples</h2>
 * 
 * <pre>{@code
 * // Sign homomorphism: S_n → Z_2
 * SymmetricGroup s3 = GroupFactory.symmetric(3);
 * CyclicGroup z2 = GroupFactory.cyclic(2);
 * 
 * Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
 *         s3, z2,
 *         p -> new CyclicElement(p.isEven() ? 0 : 1, 2));
 * 
 * // Analyze homomorphism
 * Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);
 * System.out.println("ker(sign) = A_3, order: " + kernel.order()); // 3
 * 
 * Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(sign);
 * System.out.println("im(sign) = Z_2, order: " + image.order()); // 2
 * 
 * // First Isomorphism Theorem: S_3/A_3 ≅ Z_2
 * int quotientOrder = HomomorphismAnalyzer.firstIsomorphismTheorem(sign);
 * System.out.println("|S_3/A_3| = |Z_2| = " + quotientOrder); // 2
 * }</pre>
 *
 * @see d021248.group.homomorphism.GroupHomomorphism
 * @see d021248.group.homomorphism.Homomorphism
 * @see d021248.group.homomorphism.HomomorphismAnalyzer
 */
package d021248.group.homomorphism;
