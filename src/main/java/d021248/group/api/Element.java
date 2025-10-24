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
}
