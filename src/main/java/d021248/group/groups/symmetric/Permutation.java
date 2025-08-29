package d021248.group.groups.symmetric;

import java.util.Arrays;

/**
 * Represents a permutation of n elements, where the mapping array defines the
 * image of each element.
 * <p>
 * The mapping array is of size n, and indices go from 1 to n:
 * <ul>
 * <li>mapping[i - 1] gives the image of i under the permutation (i.e.,
 * mapping[0] is the image of 1, mapping[1] is the image of 2, ..., mapping[n-1]
 * is the image of n).</li>
 * <li>Example: mapping = [2, 3, 1] represents the permutation 1→2, 2→3,
 * 3→1.</li>
 * </ul>
 */
public record Permutation(int[] mapping) {

    public Permutation {
        PermutationHelper.validate(mapping);
    }

    public Permutation inverse() {
        return PermutationHelper.inverse(mapping);
    }

    public boolean equals(Object obj) {
        return PermutationHelper.equals(this, obj);
    }

    public int hashCode() {
        return PermutationHelper.hashCode(this);
    }

    @Override
    public String toString() {
        return Arrays.toString(mapping);
    }

    public boolean isEven() {
        return PermutationHelper.isEven(this.mapping);
    }
}
