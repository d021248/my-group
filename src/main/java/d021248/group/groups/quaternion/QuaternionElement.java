package d021248.group.groups.quaternion;

import d021248.group.api.Element;

public record QuaternionElement(String value) implements Element {
    @Override
    public QuaternionElement inverse() {
        return QuaternionElementHelper.inverse(this);
    }
}
