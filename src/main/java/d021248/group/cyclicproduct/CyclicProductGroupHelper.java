package d021248.group.cyclicproduct;

import java.util.HashSet;
import java.util.Set;

import d021248.group.cyclic.CyclicElement;

public class CyclicProductGroupHelper {
    private CyclicProductGroupHelper() {
    }

    public static Set<CyclicProductElement> getGenerators(int n, int m) {
        Set<CyclicProductElement> gens = new HashSet<>();
        gens.add(new CyclicProductElement(new CyclicElement(1, n), new CyclicElement(0, m)));
        gens.add(new CyclicProductElement(new CyclicElement(0, n), new CyclicElement(1, m)));
        return gens;
    }
}
