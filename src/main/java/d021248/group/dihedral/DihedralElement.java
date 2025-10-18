package d021248.group.dihedral;

import d021248.group.api.Element;

public record DihedralElement(int rotation, int flip, int n) implements Element {
    public DihedralElement {
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        if (flip != 0 && flip != 1)
            throw new IllegalArgumentException("flip must be 0 or 1");
        rotation = ((rotation % n) + n) % n; // normalize
    }

    @Override
    public DihedralElement inverse() {
        if (flip == 0) {
            return new DihedralElement((n - rotation) % n, 0, n);
        }
        return this; // reflections are self-inverse
    }

    @Override
    public String toString() {
        return flip == 0 ? "r^" + rotation : "r^" + rotation + " s";
    }
}
