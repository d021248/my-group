package d021248.group.elementaryabelian;

import java.util.HashSet;
import java.util.Set;

public class ElementaryAbelianGroupHelper {
    private ElementaryAbelianGroupHelper() {
    }

    public static Set<ElementaryAbelianElement> getGenerators(int p, int n) {
        Set<ElementaryAbelianElement> gens = new HashSet<>();
        for (int i = 0; i < n; i++) {
            int[] values = new int[n];
            values[i] = 1;
            gens.add(new ElementaryAbelianElement(values, p));
        }
        return gens;
    }
}
