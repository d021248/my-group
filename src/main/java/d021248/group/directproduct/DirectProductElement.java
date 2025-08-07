package d021248.group.directproduct;

import d021248.group.api.Element;

/**
 * Represents an element of the direct product of two groups.
 */
@SuppressWarnings("unchecked")
public record DirectProductElement<A extends Element, B extends Element>(A first, B second) implements Element {
    @Override
    public DirectProductElement<A, B> inverse() {
        return new DirectProductElement<>((A) first.inverse(), (B) second.inverse());
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }
}
