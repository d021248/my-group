package d021248.group.symmetric;

import java.util.Arrays;

/**
 * Helper class for Permutation logic.
 */
public class PermutationHelper {
    private PermutationHelper() {
    }

    public static boolean equals(Permutation self, Object obj) {
        if (self == obj)
            return true;
        if (!(obj instanceof

        Permutation(var otherMapping)))
            return false;
        return Arrays.equals(self.mapping(), otherMapping);
    }

    public static int hashCode(Permutation self) {
        return Arrays.hashCode(self.mapping());
    }

    public static String toString(Permutation self) {
        return "Permutation" + Arrays.toString(self.mapping());
    }

    public static Permutation inverse(int[] mapping) {
        int n = mapping.length;
        int[] inverse = new int[n];
        for (int i = 0; i < n; i++) {
            inverse[mapping[i] - 1] = i + 1;
        }
        return new Permutation(inverse);
    }

    public static Permutation multiply(Permutation left, Permutation right) {
        int n = left.mapping().length;
        if (right.mapping().length != n) {
            throw new IllegalArgumentException("Permutations must be of the same size");
        }
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = left.mapping()[right.mapping()[i] - 1];
        }
        return new Permutation(result);
    }

    public static Object[] apply(Permutation perm, Object[] input) {
        if (input.length != perm.mapping().length) {
            throw new IllegalArgumentException("Input array length and permutation size must match");
        }
        Object[] result = new Object[input.length];
        int[] mapping = perm.mapping();
        for (int i = 0; i < input.length; i++) {
            result[i] = input[mapping[i] - 1]; // mapping is 1-based
        }
        return result;
    }

    // Permute a string using the given permutation and return the permuted string
    public static String apply(Permutation perm, String input) {
        Object[] chars = new Object[input.length()];
        for (int i = 0; i < input.length(); i++) {
            chars[i] = input.charAt(i);
        }
        Object[] permuted = apply(perm, chars);
        StringBuilder sb = new StringBuilder(input.length());
        for (Object c : permuted) {
            sb.append(c);
        }
        return sb.toString();
    }
}
