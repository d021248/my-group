package d021248.group.groups.dihedral;

import d021248.group.api.Element;

public record DihedralElement(int n, int r, boolean s) implements Element {
    public DihedralElement {
        r = ((r % n) + n) % n;
    }

    public boolean isReflection() {
        return s;
    }

    @Override
    public DihedralElement inverse() {
        if (!s) {
            // inverse of rotation: r^-1 = n - r
            return new DihedralElement(n, (n - r) % n, false);
        } else {
            // inverse of reflection: itself
            return new DihedralElement(n, r, true);
        }
    }

    @Override
    public String toString() {
        return s ? "s" + r : "r" + r;
    }
}
