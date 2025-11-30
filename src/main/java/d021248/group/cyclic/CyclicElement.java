package d021248.group.cyclic;

import d021248.group.MathUtil;
import d021248.group.api.Element;

public record CyclicElement(int value, int modulus) implements Element {
    public CyclicElement {
        if (modulus <= 0)
            throw new IllegalArgumentException("modulus must be positive");
        value = MathUtil.mod(value, modulus); // normalize
    }

    @Override
    public String toString() {
        return value + " (mod " + modulus + ")";
    }
}
