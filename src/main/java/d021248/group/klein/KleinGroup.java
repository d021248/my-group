package d021248.group.klein;

import java.util.HashSet;
import java.util.Set;

import d021248.group.base.AbstractGroup;

/**
 * The Klein four-group V_4.
 */
public class KleinGroup extends AbstractGroup<KleinElement> {
    public KleinGroup() {
        super(KleinGroupHelper.getGenerators(), new KleinOperation());
    }

    @Override
    public Set<KleinElement> elements() {
        Set<KleinElement> elements = new HashSet<>();
        for (int i = 0; i < 4; i++) {
            elements.add(new KleinElement(i));
        }
        return elements;
    }
}
