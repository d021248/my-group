package d021248.group.dihedral;

/**
 * Utility methods for DihedralElement logic (inverse, toString, etc).
 */
public class DihedralHelper {
    private DihedralHelper() {
    }

    public static DihedralElement inverse(DihedralElement e) {
        if (e.reflection() == 0) {
            return new DihedralElement((e.order() - e.rotation()) % e.order(), 0, e.order());
        } else {
            return e;
        }
    }

    public static String toString(DihedralElement e) {
        return e.reflection() == 0 ? String.format("r^%d", e.rotation()) : String.format("r^%d s", e.rotation());
    }
}
