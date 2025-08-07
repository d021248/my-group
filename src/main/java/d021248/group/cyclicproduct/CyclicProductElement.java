package d021248.group.cyclicproduct;

import d021248.group.api.Element;
import d021248.group.cyclic.CyclicElement;

/**
 * Element of Z_n × Z_m.
 */
public record CyclicProductElement(CyclicElement first, CyclicElement second) implements Element {
    @Override
    public CyclicProductElement inverse() {
        return new CyclicProductElement(first.inverse(), second.inverse());
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }
}
