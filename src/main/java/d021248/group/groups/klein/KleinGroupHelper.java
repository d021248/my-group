package d021248.group.groups.klein;

import java.util.Set;

public class KleinGroupHelper {
    private KleinGroupHelper() {
    }

    public static Set<KleinElement> generate() {
        return Set.of(
                new KleinElement("e"),
                new KleinElement("a"),
                new KleinElement("b"),
                new KleinElement("ab"));
    }
}
