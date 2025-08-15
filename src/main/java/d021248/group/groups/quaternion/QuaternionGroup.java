package d021248.group.groups.quaternion;

import d021248.group.base.AbstractGroup;

public class QuaternionGroup extends AbstractGroup<QuaternionElement> {
    // ...existing code...

    public QuaternionGroup() {
        super(new QuaternionGroupGeneratingSystem(), new QuaternionOperation());
    }

}
