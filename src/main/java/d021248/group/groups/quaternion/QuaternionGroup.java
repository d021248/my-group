package d021248.group.groups.quaternion;

import java.util.Set;

import d021248.group.api.SimpleGroup;

public class QuaternionGroup extends SimpleGroup<QuaternionElement> {
    public QuaternionGroup() {
        super(
                Set.of(
                        new QuaternionElement("1"),
                        new QuaternionElement("-1"),
                        new QuaternionElement("i"),
                        new QuaternionElement("-i"),
                        new QuaternionElement("j"),
                        new QuaternionElement("-j"),
                        new QuaternionElement("k"),
                        new QuaternionElement("-k")),
                new QuaternionOperation());
    }
}
