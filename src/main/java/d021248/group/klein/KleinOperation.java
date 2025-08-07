package d021248.group.klein;

import d021248.group.api.Operation;

public class KleinOperation implements Operation<KleinElement> {
    @Override
    public KleinElement apply(KleinElement a, KleinElement b) {
        return new KleinElement(a.value() ^ b.value()); // XOR for V_4
    }
}
