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

    /**
     * Compute the greatest common divisor (GCD) of two integers using Euclidean
     * algorithm.
     * 
     * @param a first integer
     * @param b second integer
     * @return the GCD of a and b (always non-negative)
     */
    public static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }

    /**
     * Compute the least common multiple (LCM) of two positive integers.
     * 
     * @param a first integer
     * @param b second integer
     * @return the LCM of a and b
     */
    public static int lcm(int a, int b) {
        return (a / gcd(a, b)) * b;
    }
}