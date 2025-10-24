package d021248.group.symmetric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import d021248.group.api.Element;

public record Permutation(int[] mapping) implements Element {
    public Permutation {
        if (mapping == null || mapping.length == 0)
            throw new IllegalArgumentException("mapping must be non-empty");
        int n = mapping.length;
        boolean[] seen = new boolean[n + 1];
        for (int v : mapping) {
            if (v < 1 || v > n || seen[v])
                throw new IllegalArgumentException("invalid permutation");
            seen[v] = true;
        }
        mapping = Arrays.copyOf(mapping, mapping.length); // defensive copy
    }

    public int size() {
        return mapping.length;
    }

    /** Public accessor returns a defensive copy to preserve immutability. */
    public int[] mapping() {
        return Arrays.copyOf(mapping, mapping.length);
    }

    /** Package-private raw access for internal operations to reduce allocations. */
    int[] raw() {
        return mapping; // safe because callers are trusted inside package
    }

    public Permutation compose(Permutation other) {
        if (other.size() != size())
            throw new IllegalArgumentException("size mismatch");
        int[] otherMap = other.raw(); // single array access, no copy
        int[] result = new int[size()];
        for (int i = 0; i < size(); i++) {
            result[i] = mapping[otherMap[i] - 1];
        }
        return new Permutation(result);
    }

    /**
     * Decompose permutation into disjoint cycles (each as list of 1-based
     * positions).
     */
    public List<List<Integer>> cycles() {
        boolean[] seen = new boolean[size()];
        List<List<Integer>> cycles = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            if (!seen[i]) {
                List<Integer> cycle = new ArrayList<>();
                int cur = i;
                do {
                    seen[cur] = true;
                    cycle.add(cur + 1);
                    cur = mapping[cur] - 1;
                } while (cur != i);
                cycles.add(cycle);
            }
        }
        return cycles;
    }

    /**
     * Sign (+1 or -1) determined by parity of permutation (even -> +1, odd -> -1).
     */
    public int sign() {
        // Sign = (-1)^{n - c} where c is number of disjoint cycles (including 1-cycles)
        int c = cycles().size();
        int exponent = size() - c;
        return (exponent & 1) == 0 ? 1 : -1;
    }

    /** Cycle notation string, e.g. (1 3 2)(4)(5 6). */
    public String toCycleString() {
        StringBuilder sb = new StringBuilder();
        for (List<Integer> cycle : cycles()) {
            if (cycle.size() == 1) {
                sb.append('(').append(cycle.get(0)).append(')');
            } else {
                sb.append('(');
                for (int i = 0; i < cycle.size(); i++) {
                    if (i > 0)
                        sb.append(' ');
                    sb.append(cycle.get(i));
                }
                sb.append(')');
            }
        }
        return sb.toString();
    }

    @Override
    public Permutation inverse() {
        int[] inv = new int[size()];
        for (int i = 0; i < size(); i++) {
            inv[mapping[i] - 1] = i + 1;
        }
        return new Permutation(inv);
    }

    @Override
    public String toString() {
        return Arrays.toString(mapping);
    }

    // Records use reference equality for array components; override for deep
    // equality.
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Permutation(int[] otherMap)))
            return false;
        return Arrays.equals(this.mapping, otherMap);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mapping);
    }
}
