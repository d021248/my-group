/**
 * Core API and concrete implementations for finite group theory.
 * <p>
 * This package provides:
 * </p>
 * <ul>
 * <li>Factory methods for creating groups
 * ({@link d021248.group.GroupFactory})</li>
 * <li>Table formatting utilities
 * ({@link d021248.group.GroupTableFormatter})</li>
 * <li>Generator closure computation ({@link d021248.group.Generator})</li>
 * <li>Helper utilities ({@link d021248.group.GroupHelper})</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>{@code
 * CyclicGroup z6 = GroupFactory.cyclic(6);
 * SymmetricGroup s4 = GroupFactory.symmetric(4);
 * DirectProduct<CyclicElement, CyclicElement> z2xz3 = GroupFactory.directProduct(GroupFactory.cyclic(2),
 *         GroupFactory.cyclic(3));
 * }</pre>
 * 
 * @see d021248.group.api
 * @see d021248.group.cyclic
 * @see d021248.group.symmetric
 * @see d021248.group.dihedral
 */
package d021248.group;
