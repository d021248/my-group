package d021248.group.groups.dihedral;

import d021248.group.api.Element;

public class DihedralElement implements Element {
    private final int n; // order of the group
    private final int r; // rotation (0..n-1)
    private final boolean s; // true = reflection, false = rotation

    public DihedralElement(int n, int r, boolean s) {
        this.n = n;
        this.r = ((r % n) + n) % n;
        this.s = s;
    }

    public int getN() {
        return n;
    }

    public int getR() {
        return r;
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof DihedralElement))
            return false;
        DihedralElement other = (DihedralElement) o;
        return n == other.n && r == other.r && s == other.s;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(n, r, s);
    }

    @Override
    public String toString() {
        return s ? "s" + r : "r" + r;
    }
}
