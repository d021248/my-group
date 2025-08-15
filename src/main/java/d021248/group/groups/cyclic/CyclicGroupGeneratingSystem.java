package d021248.group.groups.cyclic;

import java.util.HashSet;
import java.util.Set;

import d021248.group.api.GeneratingSystem;

public class CyclicGroupGeneratingSystem implements GeneratingSystem<CyclicElement> {

    private final int n;

    CyclicGroupGeneratingSystem(int n) {
        this.n = n;
    }

    /**
     * Returns the set of generators for the cyclic group of order n.
     * The generator is 1 (mod n) if n > 1.
     */
    public Set<CyclicElement> get() {
        Set<CyclicElement> generators = new HashSet<>();
        if (n > 1) {
            generators.add(new CyclicElement(1, n));
        }
        return generators;
    }
}
