/**
 * Cyclic groups (Z_n) under addition modulo n.
 * <p>
 * A cyclic group of order n consists of elements {0, 1, 2, ..., n-1} under
 * addition modulo n.
 * Every cyclic group is abelian (commutative).
 * </p>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * CyclicGroup z7 = new CyclicGroup(7);
 * CyclicElement a = new CyclicElement(3, 7);
 * CyclicElement b = new CyclicElement(5, 7);
 * CyclicElement sum = z7.operate(a, b); // 3 + 5 = 8 ≡ 1 (mod 7)
 * }</pre>
 * 
 * @see d021248.group.cyclic.CyclicGroup
 * @see d021248.group.cyclic.CyclicElement
 */
package d021248.group.cyclic;
