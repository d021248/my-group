package d021248.group.dihedral;

import java.util.Set;

public final class DihedralGroupHelper {
    private DihedralGroupHelper() {
    }

    public static Set<DihedralElement> getGenerators(int n) {
        if (n < 2)
            throw new IllegalArgumentException("n must be >= 2");
        return Set.of(new DihedralElement(1, 0, n), // rotation r
                new DihedralElement(0, 1, n)); // reflection s
    }
}
