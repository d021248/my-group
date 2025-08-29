package d021248.group.groups.dihedral;

public record DihedralElement(int n, int r, boolean s) {
    // Optionally, keep inverse as a utility method:
    public DihedralElement inverse() {
        return DihedralElementHelper.inverse(this);
    }
}
