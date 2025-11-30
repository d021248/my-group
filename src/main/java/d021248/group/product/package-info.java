/**
 * Direct products of groups (G₁ × G₂).
 * <p>
 * The direct product combines two groups with component-wise operation:
 * (a₁, a₂) * (b₁, b₂) = (a₁ *₁ b₁, a₂ *₂ b₂)
 * </p>
 * 
 * <p>
 * Order: |G₁ × G₂| = |G₁| · |G₂|
 * </p>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * CyclicGroup z2 = GroupFactory.cyclic(2);
 * CyclicGroup z3 = GroupFactory.cyclic(3);
 * DirectProduct<CyclicElement, CyclicElement> z2xz3 = GroupFactory.directProduct(z2, z3);
 * // Klein four-group: V₄ = Z₂ × Z₂
 * var v4 = GroupFactory.directProduct(z2, z2);
 * }</pre>
 * 
 * @see d021248.group.product.DirectProduct
 * @see d021248.group.product.ProductElement
 */
package d021248.group.product;
