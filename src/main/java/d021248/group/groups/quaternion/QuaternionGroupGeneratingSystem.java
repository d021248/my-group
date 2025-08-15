package d021248.group.groups.quaternion;

import java.util.HashSet;
import java.util.Set;

import d021248.group.api.GeneratingSystem;

public class QuaternionGroupGeneratingSystem implements GeneratingSystem<QuaternionElement> {
    @Override
    public Set<QuaternionElement> get() {
        Set<QuaternionElement> gens = new HashSet<>();
        // Add two generators for Q_8
        gens.add(new QuaternionElement("i"));
        gens.add(new QuaternionElement("j"));
        return gens;
    }
}
