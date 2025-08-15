package d021248.group.groups.klein;

import d021248.group.api.GeneratingSystem;

public class KleinGroupGeneratingSystem implements GeneratingSystem<KleinElement> {
    @Override
    public java.util.Set<KleinElement> get() {
        java.util.Set<KleinElement> gens = new java.util.HashSet<>();
        // Add two generators for V_4
        gens.add(new KleinElement("a"));
        gens.add(new KleinElement("b"));
        return gens;
    }
}
