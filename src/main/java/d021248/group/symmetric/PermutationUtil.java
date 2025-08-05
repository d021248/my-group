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
     * Converts cycles to a mapping from integer to integer, ensuring all points
     * 1..n are included.
     */
    public static Map<Integer, Integer> fromCycles(List<List<Integer>> cycles, int n) {
        Map<Integer, Integer> map = new HashMap<>();
        // Initialize all elements 1..n to map to themselves
        for (int i = 1; i <= n; i++) {
            map.put(i, i);
        }
        // Apply cycles
        for (List<Integer> cycle : cycles) {
            int m = cycle.size();
            for (int i = 0; i < m; i++) {
                int from = cycle.get(i);
                int to = cycle.get((i + 1) % m);
                map.put(from, to);
            }
        }
        return map;
    }
}
