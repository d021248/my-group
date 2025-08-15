package d021248.group.groups.dihedral;

import d021248.group.api.Operation;

public class DihedralOperation implements Operation<DihedralElement> {
    @Override
    public DihedralElement apply(DihedralElement a, DihedralElement b) {
        int n = a.n();
        if (!a.s() && !b.s()) {
            // rotation * rotation
            return new DihedralElement(n, (a.r() + b.r()) % n, false);
        } else if (!a.s() && b.s()) {
            // rotation * reflection
            return new DihedralElement(n, (a.r() + b.r()) % n, true);
        } else if (a.s() && !b.s()) {
            // reflection * rotation
            return new DihedralElement(n, (a.r() - b.r() + n) % n, true);
        } else {
            // reflection * reflection
            return new DihedralElement(n, (a.r() - b.r() + n) % n, false);
        }
    }
}
