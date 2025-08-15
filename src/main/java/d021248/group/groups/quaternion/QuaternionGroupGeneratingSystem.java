package d021248.group.groups.quaternion;

import java.util.Set;

import d021248.group.api.GeneratingSystem;

public class QuaternionGroupGeneratingSystem implements GeneratingSystem<QuaternionElement> {
    @Override
    public Set<QuaternionElement> get() {
        // Add two generators for Q_8
        return Set.of(new QuaternionElement("i"), new QuaternionElement("j"));
    }
}
