package d021248.group.elementaryabelian;

import java.util.Arrays;

import d021248.group.api.Element;

/**
 * Element of (Z_p)^n, represented as an int array mod p.
 */
public record ElementaryAbelianElement(int[] values, int p) implements Element {
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof ElementaryAbelianElement(var otherValues, var otherP)))
            return false;
        return p == otherP && Arrays.equals(values, otherValues);
    }

    @Override
    public int hashCode() {
        return 31 * Arrays.hashCode(values) + p;
    }

    @Override
    public ElementaryAbelianElement inverse() {
        int[] inv = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            inv[i] = (p - values[i]) % p;
        }
        return new ElementaryAbelianElement(inv, p);
    }

    @Override
    public String toString() {
        return Arrays.toString(values) + " (mod " + p + ")";
    }
}
