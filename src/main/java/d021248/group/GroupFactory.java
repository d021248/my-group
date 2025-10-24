package d021248.group;

import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.SymmetricGroup;

/** Convenience factory for common finite groups. */
public final class GroupFactory {
    private GroupFactory() {
    }

    public static CyclicGroup cyclic(int modulus) {
        return new CyclicGroup(modulus);
    }

    public static DihedralGroup dihedral(int n) {
        return new DihedralGroup(n);
    }

    public static SymmetricGroup symmetric(int n) {
        return new SymmetricGroup(n);
    }
}
