package d021248.group.groups.dihedral;

import d021248.group.base.AbstractGroup;

public class DihedralGroup extends AbstractGroup<DihedralElement> {
    // ...existing code...

    public DihedralGroup(int n) {
        super(new DihedralGroupGeneratingSystem(n), new DihedralOperation());
    }

}
