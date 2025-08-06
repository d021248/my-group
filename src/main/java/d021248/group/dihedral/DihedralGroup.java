package d021248.group.dihedral;

import java.util.HashSet;
import java.util.Set;

import d021248.group.base.AbstractGroup;

/**
 * The dihedral group D_n (symmetries of a regular n-gon).
 */
public class DihedralGroup extends AbstractGroup<DihedralElement> {
    private final int order;

    public DihedralGroup(int n) {
        super(DihedralGroupHelper.getGenerators(n), new DihedralOperation());
        this.order = n;
    }

    @Override
    public Set<DihedralElement> elements() {
        Set<DihedralElement> elements = new HashSet<>();
        for (int r = 0; r < order; r++) {
            elements.add(new DihedralElement(r, 0, order)); // rotations
            elements.add(new DihedralElement(r, 1, order)); // reflections
        }
        return elements;
    }

    public int order() {
        return order;
    }
}
