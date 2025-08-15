package d021248.group.groups.klein;

import d021248.group.api.Element;

public class KleinElement implements Element {
    // V_4 = {e, a, b, ab}, all elements are their own inverse
    private final String value; // "e", "a", "b", "ab"

    public KleinElement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public KleinElement inverse() {
        // All elements are their own inverse in V_4
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof KleinElement))
            return false;
        KleinElement other = (KleinElement) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
