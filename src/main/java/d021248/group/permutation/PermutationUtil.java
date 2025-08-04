package d021248.group.permutation;

import java.util.HashMap;
import java.util.Map;

class PermutationUtil {
    private PermutationUtil() {
    }

    static Map<Integer, Integer> toMap(Permutation perm) {
        var map = new HashMap<Integer, Integer>();
        for (var cycle : perm.cycles()) {
            int len = cycle.size();
            for (int i = 0; i < len; i++) {
                map.put(cycle.get(i), cycle.get((i + 1) % len));
            }
        }
        return map;
    }
}
