package d021248.group.symmetric;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import d021248.group.api.Element;

public record Permutation(int[] mapping) implements Element, Comparable<Permutation> {
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

    /**
     * Minimal number of transpositions needed to express this permutation.
     * A cycle of length k decomposes into (k-1) transpositions, hence for a
     * permutation on n symbols with c disjoint cycles the minimal number is
     * n - c. (Same exponent used in sign parity computation.)
     */
    public int transpositionLength() {
        return size() - cycles().size();
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

    /**
     * Canonical cycle string: each cycle rotated so smallest element first; cycles
     * sorted lexicographically.
     */
    public String toCanonicalCycleString() {
        List<List<Integer>> normalized = normalizeCycles(cycles());
        sortCycles(normalized);
        return cyclesToString(normalized);
    }

    private static List<List<Integer>> normalizeCycles(List<List<Integer>> raw) {
        List<List<Integer>> out = new ArrayList<>(raw.size());
        for (List<Integer> cycle : raw)
            out.add(rotateToMinFirst(cycle));
        return out;
    }

    private static List<Integer> rotateToMinFirst(List<Integer> cycle) {
        int minIdx = 0;
        int minVal = cycle.get(0);
        for (int i = 1; i < cycle.size(); i++) {
            int v = cycle.get(i);
            if (v < minVal) {
                minVal = v;
                minIdx = i;
            }
        }
        List<Integer> rotated = new ArrayList<>(cycle.size());
        for (int i = 0; i < cycle.size(); i++)
            rotated.add(cycle.get((minIdx + i) % cycle.size()));
        return rotated;
    }

    private static void sortCycles(List<List<Integer>> cycles) {
        cycles.sort((a, b) -> {
            int cmp = Integer.compare(a.get(0), b.get(0));
            return (cmp != 0) ? cmp : Integer.compare(a.size(), b.size());
        });
    }

    private static String cyclesToString(List<List<Integer>> cycles) {
        StringBuilder sb = new StringBuilder();
        for (List<Integer> cycle : cycles) {
            sb.append('(');
            for (int i = 0; i < cycle.size(); i++) {
                if (i > 0)
                    sb.append(' ');
                sb.append(cycle.get(i));
            }
            sb.append(')');
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

    /** Tuple style string: (1,3,2,4,5) */
    public String toTupleString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        for (int i = 0; i < mapping.length; i++) {
            if (i > 0)
                sb.append(',');
            sb.append(mapping[i]);
        }
        sb.append(')');
        return sb.toString();
    }

    /**
     * Natural order: first by size (arity), then lexicographically by mapping
     * values.
     */
    @Override
    public int compareTo(Permutation other) {
        int nCmp = Integer.compare(this.size(), other.size());
        if (nCmp != 0)
            return nCmp;
        int[] a = this.mapping; // use internal array (already validated)
        int[] b = other.raw();
        for (int i = 0; i < a.length; i++) {
            int cmp = Integer.compare(a[i], b[i]);
            if (cmp != 0)
                return cmp;
        }
        return 0;
    }

    /** Comparator exposing the same logic as compareTo (for method references). */
    public static Comparator<Permutation> byMapping() {
        return Comparator.naturalOrder();
    }

    /**
     * Comparator ordering permutations first by minimal number of transpositions,
     * then by canonical cycle string to provide deterministic ordering within
     * equal transposition classes.
     */
    public static Comparator<Permutation> byTranspositions() {
        return Comparator
                .comparingInt(Permutation::transpositionLength)
                .thenComparing(Permutation::toCanonicalCycleString);
    }
}
