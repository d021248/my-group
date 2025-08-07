package d021248.group.quaternion;

import java.util.HashSet;
import java.util.Set;

import d021248.group.base.AbstractGroup;

public class QuaternionGroup extends AbstractGroup<QuaternionElement> {
    public QuaternionGroup() {
        super(QuaternionGroupHelper.getGenerators(), new QuaternionOperation());
    }

    @Override
    public Set<QuaternionElement> elements() {
        Set<QuaternionElement> elements = new HashSet<>();
        for (int i = 0; i < 8; i++) {
            elements.add(new QuaternionElement(i));
        }
        return elements;
    }
}
