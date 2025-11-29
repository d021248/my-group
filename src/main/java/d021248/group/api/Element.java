package d021248.group.api;

/**
 * Minimal contract for a group element supporting an inverse operation.
 * <p>
 * Implementations should be immutable and obey the group law relative to the
 * {@link d021248.group.api.Operation} they participate in. The library keeps
 * this interface deliberately small to avoid over-constraining concrete algebra
 * objects (e.g. permutations, residues, dihedral elements).
 * </p>
 */
public interface Element {
        /** Return the (group) inverse of this element. */
        Element inverse();

        /**
         * Compute the order of this element in the given group.
         * <p>
         * The order is the smallest positive integer k such that g^k = e (identity).
         * </p>
         * <p>
         * Example:
         * </p>
         * 
         * <pre>
         * {
         *         &#64;code
         *         CyclicGroup z12 = new CyclicGroup(12);
         *         CyclicElement g = new CyclicElement(4, 12);
         *         int order = g.order(z12); // 3, since 4+4+4 ≡ 0 (mod 12)
         * }
         * </pre>
         * 
         * @param group the group in which to compute the order
         * @return the order of this element (always >= 1)
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        default int order(d021248.group.Group<?> group) {
                Element current = this;
                Element identity = group.identity();
                int order = 1;
                while (!current.equals(identity)) {
                        current = ((d021248.group.Group) group).operate(current, this);
                        order++;
                        if (order > 10000) // safety guard against infinite loops
                                throw new IllegalStateException(
                                                "Order computation exceeded limit (possible infinite group)");
                }
                return order;
        }
}
