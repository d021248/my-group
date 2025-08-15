package d021248.group.groups.dihedral;

public class DihedralElementHelper {
    private DihedralElementHelper() {
    }

    public static boolean isValid(DihedralElement e) {
        return e.getR() >= 0 && e.getR() < e.getN();
    }

    public static DihedralElement inverse(DihedralElement e) {
        return e.inverse();
    }

    public static DihedralElement rotation(int n, int r) {
        return new DihedralElement(n, r, false);
    }

    public static DihedralElement reflection(int n, int r) {
        return new DihedralElement(n, r, true);
    }
}
