package d021248.group.groups.dihedral;

import java.util.Set;

public class DihedralGroupHelper {
    private DihedralGroupHelper() {
    }

    public static Set<DihedralElement> generate(int n) {
        java.util.Set<DihedralElement> elements = new java.util.HashSet<>();

        for (int i = 0; i < n; i++) {
            elements.add(new DihedralElement(n, i, false));
        }

        for (int i = 0; i < n; i++) {
            elements.add(new DihedralElement(n, i, true));
        }
        return elements;
    }
}
