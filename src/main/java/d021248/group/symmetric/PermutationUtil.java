package d021248.group.symmetric;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PermutationUtil {
    private PermutationUtil() {
    }

    public static List<List<Integer>> validated(List<List<Integer>> cycles) {
        if (cycles == null) {
            throw new IllegalArgumentException("Cycles list cannot be null");
        }
        for (List<Integer> cycle : cycles) {
            if (cycle == null || cycle.isEmpty()) {
                throw new IllegalArgumentException("Each cycle must be non-null and non-empty");
            }
            Set<Integer> seenInCycle = new HashSet<>();
            for (Integer elem : cycle) {
                if (elem == null) {
                    throw new IllegalArgumentException("Cycle elements must be non-null");
                }
                if (!seenInCycle.add(elem)) {
                    throw new IllegalArgumentException("Duplicate element in a cycle: " + elem);
                }
            }
        }
        return cycles;
    }

    /**
     * Converts cycles to a mapping from integer to integer.
     */
    public static Map<Integer, Integer> fromCycles(List<List<Integer>> cycles) {
        Map<Integer, Integer> map = new HashMap<>();
        // Initialize all elements to map to themselves
        for (List<Integer> cycle : cycles) {
            for (Integer elem : cycle) {
                map.putIfAbsent(elem, elem);
            }
        }
        // Apply cycles
        for (List<Integer> cycle : cycles) {
            int n = cycle.size();
            for (int i = 0; i < n; i++) {
                int from = cycle.get(i);
                int to = cycle.get((i + 1) % n);
                map.put(from, to);
            }
        }
        return map;
    }
}
