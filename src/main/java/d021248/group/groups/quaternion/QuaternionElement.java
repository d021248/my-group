package d021248.group.groups.quaternion;

import d021248.group.api.Element;

public class QuaternionElement implements Element {
    private final String value;

    public QuaternionElement(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public QuaternionElement inverse() {
        switch (value) {
            case "1":
                return this;
            case "-1":
                return this;
            case "i":
                return new QuaternionElement("-i");
            case "-i":
                return new QuaternionElement("i");
            case "j":
                return new QuaternionElement("-j");
            case "-j":
                return new QuaternionElement("j");
            case "k":
                return new QuaternionElement("-k");
            case "-k":
                return new QuaternionElement("k");
            default:
                throw new IllegalArgumentException("Invalid QuaternionElement: " + value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof QuaternionElement))
            return false;
        QuaternionElement other = (QuaternionElement) o;
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
