package d021248.group.groups.dihedral;

import d021248.group.api.Element;

public record DihedralElement(int n, int r, boolean s) implements Element {
    @Override
    public DihedralElement inverse() {
        return DihedralElementHelper.inverse(this);
    }
}
