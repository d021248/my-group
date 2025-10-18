package d021248.group.dihedral;

import java.util.HashSet;
import java.util.Set;

import d021248.group.FiniteGroup;
import d021248.group.api.Operation;

public final class DihedralGroup implements FiniteGroup<DihedralElement> {
    private final int n;
    private final Set<DihedralElement> elements;
    private final Operation<DihedralElement> op = this::operateInternal;
    private final DihedralElement identity;

    public DihedralGroup(int n) {
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        this.n = n;
        this.elements = generateAll(n);
        this.identity = new DihedralElement(0, 0, n);
    }

    private static Set<DihedralElement> generateAll(int n) {
        Set<DihedralElement> set = new HashSet<>();
        for (int r = 0; r < n; r++) {
            set.add(new DihedralElement(r, 0, n));
            set.add(new DihedralElement(r, 1, n));
        }
        return Set.copyOf(set);
    }

    private DihedralElement operateInternal(DihedralElement a, DihedralElement b) {
        // Presentation relations: r^n = e, s^2 = e, s r = r^{-1} s
        // Encoded as (rotation r, flip f) where f=0 rotation, f=1 reflection (r^k s).
        int flip = (a.flip() + b.flip()) & 1; // parity of reflections
        int rot = (a.flip() == 0)
                ? (a.rotation() + b.rotation()) // rotation * (rotation/reflection)
                : (a.rotation() - b.rotation()); // reflection * (rotation/reflection)
        rot %= n;
        if (rot < 0)
            rot += n;
        return new DihedralElement(rot, flip, n);
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
    public DihedralElement inverse(DihedralElement e) {
        return e.inverse();
    }

    @Override
    public int order() {
        return elements.size();
    }
}
