package d021248.group.klein;

import java.util.HashSet;
import java.util.Set;

/**
 * Utility methods for Klein four-group.
 */
public class KleinGroupHelper {
    private KleinGroupHelper() {
    }

    public static Set<KleinElement> getGenerators() {
        Set<KleinElement> gens = new HashSet<>();
        gens.add(new KleinElement(1));
        gens.add(new KleinElement(2));
        return gens;
    }
}
