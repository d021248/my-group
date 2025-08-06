package d021248.group.dihedral;

import d021248.group.api.Operation;

/**
 * The group operation for the dihedral group D_n.
 */
public class DihedralOperation implements Operation<DihedralElement> {
    @Override
    public DihedralElement calculate(DihedralElement a, DihedralElement b) {
        int n = a.order();
        if (a.reflection() == 0 && b.reflection() == 0) {
            // rotation * rotation
            return new DihedralElement((a.rotation() + b.rotation()) % n, 0, n);
        } else if (a.reflection() == 0 && b.reflection() == 1) {
            // rotation * reflection
            return new DihedralElement((a.rotation() + b.rotation()) % n, 1, n);
        } else if (a.reflection() == 1 && b.reflection() == 0) {
            // reflection * rotation
            return new DihedralElement((a.rotation() - b.rotation() + n) % n, 1, n);
        } else {
            // reflection * reflection
            return new DihedralElement((a.rotation() - b.rotation() + n) % n, 0, n);
        }
    }
}
