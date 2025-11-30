/**
 * Quotient groups (G/H) where H is a normal subgroup of G.
 * <p>
 * A quotient group consists of cosets gH = {gh : h ∈ H} with operation
 * (g₁H)(g₂H) = (g₁g₂)H. Only well-defined when H is normal in G.
 * </p>
 * 
 * <p>
 * Order: |G/H| = |G| / |H| (by Lagrange's theorem)
 * </p>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * SymmetricGroup s3 = new SymmetricGroup(3);
 * AlternatingGroup a3 = new AlternatingGroup(3);
 * Subgroup<Permutation> subA3 = new Subgroup<>(s3, a3.elements());
 * QuotientGroup<Permutation> s3modA3 = GroupFactory.quotient(s3, subA3);
 * System.out.println(s3modA3.order()); // 2 (= 6/3)
 * }</pre>
 * 
 * @see d021248.group.quotient.QuotientGroup
 * @see d021248.group.quotient.Coset
 */
package d021248.group.quotient;
