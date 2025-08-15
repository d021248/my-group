package d021248.group.groups.klein;

import java.util.HashSet;
import java.util.Set;

import d021248.group.api.GeneratingSystem;

public class KleinGroupGeneratingSystem implements GeneratingSystem<KleinElement> {
    @Override
    public Set<KleinElement> get() {
        Set<KleinElement> gens = new HashSet<>();
        // Add two generators for V_4
        gens.add(new KleinElement("a"));
        gens.add(new KleinElement("b"));
        return gens;
    }
}
