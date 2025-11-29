package d021248.group;

import d021248.group.api.Element;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.product.DirectProduct;
import d021248.group.quotient.QuotientGroup;
import d021248.group.subgroup.Subgroup;
import d021248.group.symmetric.AlternatingGroup;
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

    public static AlternatingGroup alternating(int n) {
        return new AlternatingGroup(n);
    }

    public static <E1 extends Element, E2 extends Element> DirectProduct<E1, E2> directProduct(
            Group<E1> group1, Group<E2> group2) {
        return new DirectProduct<>(group1, group2);
    }

    public static <E extends Element> QuotientGroup<E> quotient(Group<E> parent, Subgroup<E> normalSubgroup) {
        return new QuotientGroup<>(parent, normalSubgroup);
    }
}
