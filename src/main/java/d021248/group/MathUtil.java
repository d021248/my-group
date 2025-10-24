package d021248.group;

/** Common small math helpers. */
public final class MathUtil {
    private MathUtil() {
    }

    /** Normalize value into range [0, modulus-1]. Modulus must be positive. */
    public static int mod(int value, int modulus) {
        int r = value % modulus;
        return r < 0 ? r + modulus : r;
    }
}