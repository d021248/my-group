package d021248.group.dihedral;

import d021248.group.api.Element;

/**
 * Represents an element of the dihedral group D_n.
 * Each element is a pair (r, s) where r is the rotation (0 <= r < n),
 * and s is 0 (rotation) or 1 (reflection).
 */
public record DihedralElement(int rotation, int reflection, int order) implements Element {
    @Override
    public DihedralElement inverse() {
        return DihedralHelper.inverse(this);
    }

    @Override
    public String toString() {
        return DihedralHelper.toString(this);
    }
}
