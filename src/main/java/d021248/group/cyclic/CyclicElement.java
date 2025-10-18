package d021248.group.cyclic;

import d021248.group.api.Element;

public record CyclicElement(int value, int modulus) implements Element {
    public CyclicElement {
        if (modulus <= 0)
            throw new IllegalArgumentException("modulus must be positive");
        value = ((value % modulus) + modulus) % modulus; // normalize
    }

    @Override
    public CyclicElement inverse() {
        return new CyclicElement(modulus - value, modulus);
    }

    @Override
    public String toString() {
        return value + " (mod " + modulus + ")";
    }
}
