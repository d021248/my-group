package d021248.group.groups.klein;

import java.util.Set;

import d021248.group.api.GeneratingSystem;

public class KleinGroupGeneratingSystem implements GeneratingSystem<KleinElement> {
    @Override
    public Set<KleinElement> get() {
        // Add two generators for V_4
        return Set.of(new KleinElement("a"), new KleinElement("b"));
    }
}
