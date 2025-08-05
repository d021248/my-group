package d021248.group.symmetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import d021248.group.api.Element;

public record Permutation(List<List<Integer>> cycles) implements Element {

    public Permutation {
        validate(cycles);
    }

    private static void validate(List<List<Integer>> cycles) {
        if (cycles == null) {
            throw new IllegalArgumentException("Cycles list cannot be null");
        }
        Set<Integer> seen = new HashSet<>();
        for (List<Integer> cycle : cycles) {
            if (cycle == null || cycle.isEmpty()) {
                throw new IllegalArgumentException("Each cycle must be non-null and non-empty");
            }
            for (Integer elem : cycle) {
                if (elem == null) {
                    throw new IllegalArgumentException("Cycle elements must be non-null");
                }
                if (!seen.add(elem)) {
                    throw new IllegalArgumentException("Duplicate element in cycles: " + elem);
                }
            }
        }
    }

    @Override
    public Permutation inverse() {
        var map = PermutationUtil.toMap(this);
        var invMap = new HashMap<Integer, Integer>();
        for (var e : map.entrySet()) {
            invMap.put(e.getValue(), e.getKey());
        }
        // Reconstruct cycles from inverse map
        Set<Integer> all = new HashSet<>(map.keySet());
        List<List<Integer>> resultCycles = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (Integer start : all) {
            if (seen.contains(start)) {
                continue;
            }
            List<Integer> cycle = new ArrayList<>();
            int x = start;
            do {
                cycle.add(x);
                seen.add(x);
                x = invMap.getOrDefault(x, x);
            } while (x != start && !seen.contains(x));
            if (cycle.size() > 1) {
                resultCycles.add(cycle);
            }
        }
        return new Permutation(resultCycles);
    }

    @Override
    public String toString() {
        return cycles.toString();
    }
}
