/**
 * Symmetric and alternating groups - permutations on n elements.
 * <p>
 * The symmetric group S_n contains all permutations of {1, 2, ..., n}.
 * The alternating group A_n contains only even permutations (sign = +1).
 * </p>
 * 
 * <p>
 * Order: |S_n| = n!, |A_n| = n!/2
 * </p>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * SymmetricGroup s4 = new SymmetricGroup(4);
 * Permutation cycle = PermutationFactory.cycle(1, 2, 3); // (1 2 3)
 * Permutation trans = PermutationFactory.transposition(1, 2, 4); // (1 2)
 * Permutation product = cycle.compose(trans);
 * }</pre>
 * 
 * @see d021248.group.symmetric.SymmetricGroup
 * @see d021248.group.symmetric.AlternatingGroup
 * @see d021248.group.symmetric.Permutation
 * @see d021248.group.symmetric.PermutationFactory
 */
package d021248.group.symmetric;
