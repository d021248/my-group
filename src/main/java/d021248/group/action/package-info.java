/**
 * Group actions and orbit analysis.
 * <p>
 * This package provides tools for working with group actions on sets.
 * A group action is a way for a group G to "act on" or "operate on" a set X,
 * written as G × X → X, satisfying:
 * <pre>
 *     1. e · x = x (identity acts trivially)
 *     2. (g₁g₂) · x = g₁ · (g₂ · x) (compatibility)
 * </pre>
 *
 * <h2>Key Concepts</h2>
 * <ul>
 *   <li><b>Action</b>: A structure-preserving way for G to permute elements of X</li>
 *   <li><b>Orbit</b>: orb(x) = {g · x | g ∈ G}, the set of all images of x</li>
 *   <li><b>Stabilizer</b>: Stab(x) = {g ∈ G | g · x = x}, elements fixing x</li>
 *   <li><b>Transitive</b>: Action has only one orbit (G acts "everywhere")</li>
 *   <li><b>Free</b>: All stabilizers are trivial (only identity fixes points)</li>
 * </ul>
 *
 * <h2>Orbit-Stabilizer Theorem</h2>
 * <p>
 * For any group action and element x:
 * <pre>
 *     |orb(x)| × |Stab(x)| = |G|
 * </pre>
 * This fundamental theorem relates orbit size to stabilizer order.
 *
 * <h2>Burnside's Lemma</h2>
 * <p>
 * The number of orbits equals the average number of fixed points:
 * <pre>
 *     |X/G| = (1/|G|) × Σ_{g∈G} |Fix(g)|
 * </pre>
 * Useful for counting problems with symmetry.
 *
 * <h2>Examples</h2>
 * <pre>{@code
 * // Conjugation action: G acts on itself by g · h = ghg⁻¹
 * CyclicGroup z4 = GroupFactory.cyclic(4);
 * 
 * Action<CyclicElement, CyclicElement> conjugation = new Action<>(
 *     z4,
 *     z4.elements(),
 *     (g, h) -> {
 *         CyclicElement gInv = z4.inverse(g);
 *         return z4.operate(z4.operate(g, h), gInv);
 *     }
 * );
 * 
 * // Analyze action
 * List<Orbit<CyclicElement>> orbits = ActionAnalyzer.orbits(conjugation);
 * System.out.println("Number of orbits: " + orbits.size()); // 4 (abelian)
 * 
 * // For each element, verify orbit-stabilizer theorem
 * for (CyclicElement x : z4.elements()) {
 *     Orbit<CyclicElement> orb = ActionAnalyzer.orbit(conjugation, x);
 *     Subgroup<CyclicElement> stab = ActionAnalyzer.stabilizer(conjugation, x);
 *     System.out.println("|orb| × |Stab| = " + orb.size() * stab.order()); // 4
 * }
 * 
 * // Burnside's Lemma
 * int numOrbits = ActionAnalyzer.burnsideLemma(conjugation);
 * System.out.println("Number of orbits: " + numOrbits); // 4
 * }</pre>
 *
 * <h2>Common Actions</h2>
 * <ul>
 *   <li><b>Conjugation</b>: G acts on itself by g · h = ghg⁻¹ (orbits = conjugacy classes)</li>
 *   <li><b>Left multiplication</b>: G acts on cosets G/H by g · (aH) = (ga)H</li>
 *   <li><b>Right multiplication</b>: Similar but from the right</li>
 *   <li><b>Permutation</b>: S_n acts on {1,...,n} by permuting indices</li>
 *   <li><b>Linear</b>: GL(V) acts on vector space V by matrix multiplication</li>
 * </ul>
 *
 * @see d021248.group.action.GroupAction
 * @see d021248.group.action.Action
 * @see d021248.group.action.Orbit
 * @see d021248.group.action.ActionAnalyzer
 */
package d021248.group.action;
