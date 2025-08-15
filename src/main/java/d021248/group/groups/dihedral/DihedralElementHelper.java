package d021248.group.groups.dihedral;

public class DihedralElementHelper {
    private DihedralElementHelper() {
    }

    public static boolean isValid(DihedralElement e) {
        return e.r() >= 0 && e.r() < e.n();
    }

    public static DihedralElement inverse(DihedralElement e) {
        if (!e.s()) {
            // inverse of rotation: r^-1 = n - r
            return new DihedralElement(e.n(), (e.n() - e.r()) % e.n(), false);
        } else {
            // inverse of reflection: itself
            return new DihedralElement(e.n(), e.r(), true);
        }
    }

    public static DihedralElement rotation(int n, int r) {
        return new DihedralElement(n, r, false);
    }

    public static DihedralElement reflection(int n, int r) {
        return new DihedralElement(n, r, true);
    }
}
