package d021248.group.groups.dihedral;

import java.util.Set;

import d021248.group.api.GeneratingSystem;

public class DihedralGroupGeneratingSystem implements GeneratingSystem<DihedralElement> {
    public DihedralGroupGeneratingSystem(int n) {
        // n is not used, but kept for API consistency
    }

    @Override
    public Set<DihedralElement> get() {
        // Add rotation and reflection generators for D_n
        int n = 4; // or pass n from constructor if needed
        return Set.of(
                new DihedralElement(n, 1, false), // rotation generator r
                new DihedralElement(n, 0, true) // reflection generator s
        );
    }
}
