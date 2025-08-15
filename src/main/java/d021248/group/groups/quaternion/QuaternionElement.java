package d021248.group.groups.quaternion;

import d021248.group.api.Element;

public record QuaternionElement(String value) implements Element {
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
}
