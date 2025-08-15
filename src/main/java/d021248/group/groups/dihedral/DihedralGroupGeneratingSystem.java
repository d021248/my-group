package d021248.group.groups.dihedral;

import java.util.HashSet;
import java.util.Set;

import d021248.group.api.GeneratingSystem;

public class DihedralGroupGeneratingSystem implements GeneratingSystem<DihedralElement> {
    public DihedralGroupGeneratingSystem(int n) {
        // n is not used, but kept for API consistency
    }

    @Override
    public Set<DihedralElement> get() {
        Set<DihedralElement> gens = new HashSet<>();
        // Add rotation and reflection generators for D_n
        int n = 4; // or pass n from constructor if needed
        gens.add(new DihedralElement(n, 1, false)); // rotation generator r
        gens.add(new DihedralElement(n, 0, true)); // reflection generator s
        return gens;
    }
}
