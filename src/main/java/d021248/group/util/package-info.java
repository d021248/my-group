/**
 * Utility classes for group verification, visualization, and analysis.
 * <p>
 * This package provides:
 * </p>
 * <ul>
 * <li>{@link d021248.group.util.GroupVerifier} - Exhaustive verification of
 * group axioms</li>
 * </ul>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * CyclicGroup z6 = new CyclicGroup(6);
 * var result = GroupVerifier.verify(z6);
 * if (result.ok()) {
 *     System.out.println("All axioms satisfied");
 * } else {
 *     System.out.println(result.summary());
 * }
 * }</pre>
 * 
 * @see d021248.group.util.GroupVerifier
 */
package d021248.group.util;
