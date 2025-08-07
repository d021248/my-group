package d021248.group.quaternion;

/**
 * Utility methods for quaternion group Q_8.
 */
public class QuaternionHelper {
    private static final String[] NAMES = { "1", "-1", "i", "-i", "j", "-j", "k", "-k" };
    private static final int[] INVERSES = { 0, 1, 3, 2, 5, 4, 7, 6 };

    private QuaternionHelper() {
    }

    public static QuaternionElement inverse(QuaternionElement e) {
        return new QuaternionElement(INVERSES[e.value()]);
    }

    public static String toString(QuaternionElement e) {
        return NAMES[e.value()];
    }
}
