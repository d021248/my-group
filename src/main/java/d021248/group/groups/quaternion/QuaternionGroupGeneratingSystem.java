package d021248.group.groups.quaternion;

import d021248.group.api.GeneratingSystem;

public class QuaternionGroupGeneratingSystem implements GeneratingSystem<QuaternionElement> {
    @Override
    public java.util.Set<QuaternionElement> get() {
        java.util.Set<QuaternionElement> gens = new java.util.HashSet<>();
        // Add two generators for Q_8
        gens.add(new QuaternionElement("i"));
        gens.add(new QuaternionElement("j"));
        return gens;
    }
}
