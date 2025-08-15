package d021248.group.groups.klein;

import d021248.group.api.Element;

// V_4 = {e, a, b, ab}, all elements are their own inverse
public record KleinElement(String value) implements Element {
    @Override
    public KleinElement inverse() {
        // All elements are their own inverse in V_4
        return this;
    }
}
