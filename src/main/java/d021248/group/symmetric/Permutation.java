package d021248.group.symmetric;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import d021248.group.api.Element;

public record Permutation(List<List<Integer>> cycles) implements Element {
    public Permutation(List<List<Integer>> cycles) {
        validate(cycles);
        this.cycles = normalize(cycles);
    }

    private static void validate(List<List<Integer>> cycles) {
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
    }

    /**
     * Normalize cycles to a sequence of transpositions.
     * Each cycle (a1 a2 ... an) becomes (a1 a2), (a1 a3), ..., (a1 an)
     * 
     * @param cycles input cycles
     * @return list of transpositions (each as a 2-element list)
     */
    private static List<List<Integer>> normalize(List<List<Integer>> cycles) {
        List<List<Integer>> transpositions = new ArrayList<>();
        for (List<Integer> cycle : cycles) {
            if (cycle.size() <= 1)
                continue;
            Integer first = cycle.get(0);
            for (int i = cycle.size() - 1; i > 0; i--) {
                List<Integer> trans = new ArrayList<>();
                trans.add(first);
                trans.add(cycle.get(i));
                transpositions.add(trans);
            }
        }
        return transpositions;
    }

    /**
     * Returns the permutation as a mapping from integer to integer.
     * The mapping is computed by applying all transpositions in order.
     */
    private Map<Integer, Integer> asMapping() {
        Map<Integer, Integer> map = new HashMap<>();
        // Collect all elements
        for (List<Integer> t : cycles) {
            map.putIfAbsent(t.get(0), t.get(0));
            map.putIfAbsent(t.get(1), t.get(1));
        }
        // Apply transpositions in order
        for (List<Integer> t : cycles) {
            int a = t.get(0);
            int b = t.get(1);
            int temp = map.get(a);
            map.put(a, map.get(b));
            map.put(b, temp);
        }
        return map;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Permutation other))
            return false;
        return this.asMapping().equals(other.asMapping());
    }

    @Override
    public int hashCode() {
        return Objects.hash(asMapping());
    }

    @Override
    public Permutation inverse() {
        List<List<Integer>> reversed = new ArrayList<>();
        for (int i = cycles.size() - 1; i >= 0; i--) {
            List<Integer> t = cycles.get(i);
            // Swap elements for clarity, though (a b) = (b a)
            reversed.add(List.of(t.get(1), t.get(0)));
        }
        return new Permutation(reversed);
    }

    @Override
    public String toString() {
        return cycles.toString();
    }
}
