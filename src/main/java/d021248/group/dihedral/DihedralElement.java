package d021248.group.dihedral;

import d021248.group.MathUtil;
import d021248.group.api.Element;

public record DihedralElement(int rotation, Flip flip, int n) implements Element {
    public DihedralElement {
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        rotation = MathUtil.mod(rotation, n); // normalize
    }

    @Override
    public DihedralElement inverse() {
        return flip == Flip.ROTATION
                ? new DihedralElement((n - rotation) % n, Flip.ROTATION, n)
                : this; // reflections are self-inverse
    }

    @Override
    public String toString() {
        return flip == Flip.ROTATION ? "r^" + rotation : "r^" + rotation + " s";
    }
}
