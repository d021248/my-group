package d021248.group.groups.cyclic;

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
        return n > 1 ? Set.of(new CyclicElement(1, n)) : Set.of();
    }
}
