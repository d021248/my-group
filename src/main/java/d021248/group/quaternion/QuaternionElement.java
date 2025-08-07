package d021248.group.quaternion;

import d021248.group.api.Element;

/**
 * Represents an element of the quaternion group Q_8.
 * Elements: 1, -1, i, -i, j, -j, k, -k (encoded as 0..7).
 */
public record QuaternionElement(int value) implements Element {
    @Override
    public QuaternionElement inverse() {
        return QuaternionHelper.inverse(this);
    }

    @Override
    public String toString() {
        return QuaternionHelper.toString(this);
    }
}
