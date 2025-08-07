package d021248.group.symmetric;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility class for permutation logic and operations.
 * <p>
 * Provides static methods for equality, hash code, string conversion,
 * inversion,
 * multiplication, and application of permutations.
 */
public final class PermutationHelper {
    private PermutationHelper() {
    }

    /**
     * Checks equality of two Permutation objects using record pattern matching
     * (Java 21+).
     *
     * @param self the permutation to compare
     * @param obj  the object to compare with
     * @return true if equal, false otherwise
     */
    public static boolean equals(final Permutation self, final Object obj) {
        if (self == obj)
            return true;
        if (!(obj instanceof Permutation(var otherMapping)))
            return false;
        return Arrays.equals(self.mapping(), otherMapping);
    }

    /**
     * Computes hash code for a Permutation.
     *
     * @param self the permutation
     * @return the hash code
     */
    public static int hashCode(final Permutation self) {
        return Arrays.hashCode(self.mapping());
    }

    /**
     * Returns a string representation of the permutation.
     *
     * @param self the permutation
     * @return string representation
     */
    public static String toString(final Permutation self) {
        return "Permutation" + Arrays.toString(self.mapping());
    }

    /**
     * Returns the inverse of a permutation mapping.
     *
     * @param mapping the permutation mapping
     * @return the inverse permutation
     */
    public static Permutation inverse(final int[] mapping) {
        final int n = mapping.length;
        final int[] inverse = new int[n];
        Arrays.setAll(inverse, i -> 0); // initialize to 0 (optional, for clarity)
        for (int i = 0; i < n; i++) {
            inverse[mapping[i] - 1] = i + 1;
        }
        return new Permutation(inverse);
    }

    /**
     * Applies a permutation to an Object array.
     *
     * @param perm  the permutation
     * @param input the input array
     * @return the permuted array
     * @throws IllegalArgumentException if sizes do not match
     */
    public static Object[] apply(final Permutation perm, final Object[] input) {
        if (input.length != perm.mapping().length) {
            throw new IllegalArgumentException("Input array length and permutation size must match");
        }
        final Object[] result = new Object[input.length];
        final int[] mapping = perm.mapping();
        for (int i = 0; i < input.length; i++) {
            result[i] = input[mapping[i] - 1]; // mapping is 1-based
        }
        return result;
    }

    /**
     * Permute a string using the given permutation and return the permuted string.
     *
     * @param perm  the permutation
     * @param input the input string
     * @return the permuted string
     */
    public static String apply(final Permutation perm, final String input) {
        final Object[] chars = new Object[input.length()];
        for (int i = 0; i < input.length(); i++) {
            chars[i] = input.charAt(i);
        }
        final Object[] permuted = apply(perm, chars);
        final StringBuilder sb = new StringBuilder(input.length());
        for (final Object c : permuted) {
            sb.append(c);
        }
        return sb.toString();
    }

    /**
     * Returns true if the permutation is even, false if odd.
     *
     * @param mapping the permutation mapping (1-based)
     */
    public static boolean isEven(int[] mapping) {
        int n = mapping.length;
        boolean[] visited = new boolean[n];
        int transpositions = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                int len = 0;
                int j = i;
                while (!visited[j]) {
                    visited[j] = true;
                    j = mapping[j] - 1;
                    len++;
                }
                if (len > 0) {
                    transpositions += len - 1;
                }
            }
        }
        return transpositions % 2 == 0;
    }

    /**
     * Validates a permutation mapping array.
     * Throws IllegalArgumentException if invalid.
     */
    public static void validate(int[] mapping) {
        int n = mapping.length;
        if (n == 0) {
            throw new IllegalArgumentException("Permutation mapping must not be empty");
        }
        Set<Integer> seen = new HashSet<>();
        for (int v : mapping) {
            if (v < 1 || v > n || !seen.add(v)) {
                throw new IllegalArgumentException(
                        "Invalid permutation mapping: must contain each integer from 1 to " + n + " exactly once");
            }
        }
    }
}
