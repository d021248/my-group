package d021248.group.symmetric;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import d021248.group.api.Element;

public record Permutation(Map<Integer, Integer> mapping) implements Element {

    public Permutation(List<List<Integer>> cycles) {
        this(Collections.unmodifiableMap(PermutationUtil.fromCycles(PermutationUtil.validated(cycles))));
    }

    @Override
    public Permutation inverse() {
        Map<Integer, Integer> inv = new HashMap<>();
        for (var entry : mapping.entrySet()) {
            inv.put(entry.getValue(), entry.getKey());
        }
        return new Permutation(inv);
    }

    @Override
    public String toString() {
        return mapping.toString();
    }
}
