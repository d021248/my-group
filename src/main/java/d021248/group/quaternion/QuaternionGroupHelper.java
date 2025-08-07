package d021248.group.quaternion;

import java.util.HashSet;
import java.util.Set;

public class QuaternionGroupHelper {
    private QuaternionGroupHelper() {
    }

    public static Set<QuaternionElement> getGenerators() {
        Set<QuaternionElement> gens = new HashSet<>();
        gens.add(new QuaternionElement(2)); // i
        gens.add(new QuaternionElement(4)); // j
        return gens;
    }
}
