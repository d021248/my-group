package d021248.group;

import d021248.group.api.Element;

/**
 * Extension of {@link Group} for finite groups providing their order.
 * <p>
 * Order equals the size of {@link Group#elements()} and is strictly positive.
 * By Lagrange's theorem, the order of any element or subgroup divides the group
 * order.
 * </p>
 * <p>
 * Example:
 * </p>
 * 
 * <pre>
 * {
 *     &#64;code
 *     SymmetricGroup s3 = new SymmetricGroup(3);
 *     System.out.println(s3.order()); // 6 (= 3!)
 * 
 *     DihedralGroup d4 = new DihedralGroup(4);
 *     System.out.println(d4.order()); // 8 (rotations + reflections)
 * }
 * </pre>
 */
public interface FiniteGroup<E extends Element> extends Group<E> {
    /** Order (number of elements) of the finite group. */
    default int order() {
        return elements().size();
    }
}
