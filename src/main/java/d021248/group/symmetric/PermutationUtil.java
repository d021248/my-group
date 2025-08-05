package d021248.group.symmetric;

import java.util.HashMap;
import java.util.Map;

class PermutationUtil {
    private PermutationUtil() {
    }

    static Map<Integer, Integer> toMap(Permutation perm) {
        // Build the set of all elements involved
        var elements = new java.util.HashSet<Integer>();
        for (var trans : perm.cycles()) {
            elements.addAll(trans);
        }
        // Start with the identity mapping
        var map = new HashMap<Integer, Integer>();
        for (var e : elements) {
            map.put(e, e);
        }
        // Apply each transposition in order
        for (var trans : perm.cycles()) {
            if (trans.size() == 2) {
                int a = trans.get(0);
                int b = trans.get(1);
                // Swap the images of a and b
                int temp = map.get(a);
                map.put(a, map.get(b));
                map.put(b, temp);
            }
        }
        return map;
    }
}
