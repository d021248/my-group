/**
 * Conjugacy classes and conjugacy-based group analysis.
 * <p>
 * This package provides tools for computing and analyzing conjugacy classes
 * in finite groups.
 * </p>
 * 
 * <h2>Key Concepts</h2>
 * <ul>
 * <li><strong>Conjugacy</strong>: Elements g and h are conjugate if h = xgx⁻¹
 * for some x ∈ G</li>
 * <li><strong>Conjugacy Class</strong>: cl(g) = {xgx⁻¹ : x ∈ G}</li>
 * <li><strong>Class Equation</strong>: |G| = |Z(G)| + Σ |cl(g_i)|</li>
 * <li><strong>Orbit-Stabilizer</strong>: |cl(g)| × |C_G(g)| = |G|</li>
 * </ul>
 * 
 * <h2>Example Usage</h2>
 * 
 * <pre>{@code
 * // Compute all conjugacy classes
 * SymmetricGroup s4 = new SymmetricGroup(4);
 * List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s4);
 * 
 * // Print class equation
 * Map<Integer, Long> equation = ConjugacyAnalyzer.classEquation(s4);
 * equation.forEach((size, count) ->
 *     System.out.println(count + " classes of size " + size));
 * 
 * // Check if elements are conjugate
 * Permutation p1 = ..., p2 = ...;
 * boolean conj = ConjugacyAnalyzer.areConjugate(s4, p1, p2);
 * }</pre>
 * 
 * @see d021248.group.conjugacy.ConjugacyClass
 * @see d021248.group.conjugacy.ConjugacyAnalyzer
 */
package d021248.group.conjugacy;
