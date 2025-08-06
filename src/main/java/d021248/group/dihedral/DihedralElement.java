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
        if (reflection == 0) {
            // Inverse of rotation: (n - rotation) mod n
            return new DihedralElement((order - rotation) % order, 0, order);
        } else {
            // Inverse of reflection: itself
            return this;
        }
    }

    @Override
    public String toString() {
        return reflection == 0 ? String.format("r^%d", rotation) : String.format("r^%d s", rotation);
    }
}
