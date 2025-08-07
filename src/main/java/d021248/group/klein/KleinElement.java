package d021248.group.klein;

import d021248.group.api.Element;

/**
 * Represents an element of the Klein four-group V_4.
 * Elements are labeled 0, 1, 2, 3.
 */
public record KleinElement(int value) implements Element {
    @Override
    public KleinElement inverse() {
        return this; // All elements are self-inverse in V_4
    }

    @Override
    public String toString() {
        return "v" + value;
    }
}
