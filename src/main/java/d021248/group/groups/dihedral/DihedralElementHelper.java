package d021248.group.groups.dihedral;

public class DihedralElementHelper {
    private DihedralElementHelper() {
    }

    public static boolean isValid(DihedralElement e) {
        return e.r() >= 0 && e.r() < e.n();
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
