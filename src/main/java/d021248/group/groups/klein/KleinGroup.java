package d021248.group.groups.klein;

import d021248.group.base.AbstractGroup;

public class KleinGroup extends AbstractGroup<KleinElement> {
    // ...existing code...

    public KleinGroup() {
        super(new KleinGroupGeneratingSystem(), new KleinOperation());
    }

}
