/**
 * Minimal core interfaces for group theory: Element, Operation, and Group.
 * <p>
 * This package defines the fundamental abstractions:
 * </p>
 * <ul>
 * <li>{@link d021248.group.api.Element} - Marker interface for group
 * elements</li>
 * <li>{@link d021248.group.api.Operation} - Binary operation on elements</li>
 * <li>{@link d021248.group.Group} - Complete group interface with elements,
 * operation, identity, and inverses</li>
 * </ul>
 * 
 * <p>
 * All group implementations must satisfy the four group axioms:
 * </p>
 * <ol>
 * <li><strong>Closure</strong>: For all a, b in G, a * b is in G</li>
 * <li><strong>Associativity</strong>: (a * b) * c = a * (b * c)</li>
 * <li><strong>Identity</strong>: There exists e such that e * a = a * e =
 * a</li>
 * <li><strong>Inverses</strong>: For all a, there exists a⁻¹ such that a * a⁻¹
 * = a⁻¹ * a = e</li>
 * </ol>
 * 
 * @see d021248.group.util.GroupVerifier
 */
package d021248.group.api;
