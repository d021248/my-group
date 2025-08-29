package d021248.group.groups.quaternion;

public record QuaternionElement(String value) {
    public QuaternionElement inverse() {
        return QuaternionElementHelper.inverse(this);
    }
}
