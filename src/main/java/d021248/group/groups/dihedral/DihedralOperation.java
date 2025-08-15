package d021248.group.groups.dihedral;

import d021248.group.api.Operation;

public class DihedralOperation implements Operation<DihedralElement> {
    @Override
    public DihedralElement apply(DihedralElement a, DihedralElement b) {
        int n = a.getN();
        if (!a.isReflection() && !b.isReflection()) {
            // rotation * rotation
            return new DihedralElement(n, (a.getR() + b.getR()) % n, false);
        } else if (!a.isReflection() && b.isReflection()) {
            // rotation * reflection
            return new DihedralElement(n, (a.getR() + b.getR()) % n, true);
        } else if (a.isReflection() && !b.isReflection()) {
            // reflection * rotation
            return new DihedralElement(n, (a.getR() - b.getR() + n) % n, true);
        } else {
            // reflection * reflection
            return new DihedralElement(n, (a.getR() - b.getR() + n) % n, false);
        }
    }
}
