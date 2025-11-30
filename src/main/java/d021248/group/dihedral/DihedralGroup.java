package d021248.group.dihedral;

import java.util.HashSet;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Operation;

public final class DihedralGroup implements Group<DihedralElement> {
    private final int n;
    private final Set<DihedralElement> elements;
    private final Operation<DihedralElement> op = this::operateInternal;
    private final DihedralElement identity;

    public DihedralGroup(int n) {
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        this.n = n;
        this.elements = generateAll(n);
        this.identity = new DihedralElement(0, Flip.ROTATION, n);
    }

    private static Set<DihedralElement> generateAll(int n) {
        Set<DihedralElement> set = new HashSet<>();
        for (int r = 0; r < n; r++) {
            set.add(new DihedralElement(r, Flip.ROTATION, n));
            set.add(new DihedralElement(r, Flip.REFLECTION, n));
        }
        return Set.copyOf(set);
    }

    private DihedralElement operateInternal(DihedralElement a, DihedralElement b) {
        // Presentation relations: r^n = e, s^2 = e, s r = r^{-1} s
        // Encoded as (rotation r, flip f) where f=0 rotation, f=1 reflection (r^k s).
        boolean reflectionParity = (a.flip() == Flip.REFLECTION) ^ (b.flip() == Flip.REFLECTION);
        int rot = (a.flip() == Flip.ROTATION)
                ? (a.rotation() + b.rotation())
                : (a.rotation() - b.rotation());
        rot %= n;
        if (rot < 0)
            rot += n;
        Flip newFlip = reflectionParity ? Flip.REFLECTION : Flip.ROTATION;
        return new DihedralElement(rot, newFlip, n);
    }

    @Override
    public Set<DihedralElement> elements() {
        return elements;
    }

    @Override
    public Operation<DihedralElement> operation() {
        return op;
    }

    @Override
    public DihedralElement identity() {
        return identity;
    }

    @Override
    public DihedralElement inverse(DihedralElement element) {
        return element.flip() == Flip.ROTATION
                ? new DihedralElement((n - element.rotation()) % n, Flip.ROTATION, n)
                : element; // reflections are self-inverse
    }

    /** Degree n of D_n. */
    public int degree() {
        return n;
    }
}
