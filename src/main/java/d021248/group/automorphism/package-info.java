/**
 * Group automorphisms and the automorphism group Aut(G).
 * <p>
 * This package provides tools for working with automorphisms, which are
 * isomorphisms from a group to itself. Automorphisms preserve group structure
 * while permuting elements.
 *
 * <h2>Key Concepts</h2>
 * <ul>
 * <li><b>Automorphism</b>: Isomorphism φ: G → G</li>
 * <li><b>Inner Automorphism</b>: Inn_g(h) = ghg⁻¹ (conjugation by g)</li>
 * <li><b>Aut(G)</b>: Group of all automorphisms under composition</li>
 * <li><b>Inn(G)</b>: Subgroup of inner automorphisms</li>
 * <li><b>Out(G)</b>: Outer automorphism group = Aut(G)/Inn(G)</li>
 * </ul>
 *
 * <h2>Fundamental Theorems</h2>
 * <ul>
 * <li>Inn(G) is a normal subgroup of Aut(G)</li>
 * <li>Inn(G) ≅ G/Z(G) where Z(G) is the center</li>
 * <li>If Z(G) = {e}, then Inn(G) ≅ G</li>
 * <li>Abelian groups: Inn(G) = {id} (only trivial inner automorphism)</li>
 * </ul>
 *
 * <h2>Examples</h2>
 * 
 * <pre>{@code
 * // Inner automorphism by conjugation
 * SymmetricGroup s3 = GroupFactory.symmetric(3);
 * Permutation g = new Permutation(new int[] { 2, 1, 3 }); // (1 2)
 * 
 * Automorphism<Permutation> inner = AutomorphismAnalyzer.innerAutomorphism(s3, g);
 * 
 * // Verify it's an automorphism
 * boolean valid = AutomorphismAnalyzer.isAutomorphism(inner); // true
 * 
 * // Apply to element
 * Permutation h = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)
 * Permutation conjugate = inner.apply(h); // (1 2) · (1 2 3) · (1 2)
 * System.out.println("Conjugate: " + conjugate);
 * 
 * // Get all inner automorphisms
 * Set<Automorphism<Permutation>> innerAutos = AutomorphismAnalyzer.innerAutomorphisms(s3);
 * System.out.println("Number of inner automorphisms: " + innerAutos.size()); // 6
 * 
 * // Count distinct inner automorphisms
 * int distinct = AutomorphismAnalyzer.countDistinctInnerAutomorphisms(s3);
 * System.out.println("Distinct: " + distinct); // Equals |S_3|/|Z(S_3)|
 * 
 * // Compose automorphisms
 * Permutation g2 = new Permutation(new int[] { 2, 3, 1 }); // (1 2 3)
 * Automorphism<Permutation> inner2 = AutomorphismAnalyzer.innerAutomorphism(s3, g2);
 * Automorphism<Permutation> composed = AutomorphismAnalyzer.compose(inner, inner2);
 * 
 * // For abelian groups, all inner automorphisms are trivial
 * CyclicGroup z6 = GroupFactory.cyclic(6);
 * Set<Automorphism<CyclicElement>> innerZ6 = AutomorphismAnalyzer.innerAutomorphisms(z6);
 * // All map to identity automorphism
 * }</pre>
 *
 * <h2>Common Automorphism Groups</h2>
 * <ul>
 * <li><b>Cyclic groups</b>: Aut(Z_n) ≅ (Z_n)* (units mod n)</li>
 * <li><b>Symmetric groups</b>: Aut(S_n) ≅ S_n for n ≠ 6</li>
 * <li><b>Dihedral groups</b>: Contains reflections and rotations</li>
 * <li><b>Abelian groups</b>: Inn(G) = {id}, all automorphisms are outer</li>
 * </ul>
 *
 * @see d021248.group.automorphism.Automorphism
 * @see d021248.group.automorphism.AutomorphismAnalyzer
 */
package d021248.group.automorphism;
