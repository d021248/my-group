package d021248.group.symmetric;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.api.Operation;

/**
 * Finite permutation group obtained from an arbitrary finite group G using the
 * left regular action (Cayley representation). Each element g in G maps to the
 * permutation L_g: x -> g * x on the underlying set of elements of G.
 * <p>
 * Construction details:
 * <ul>
 * <li>Enumerate elements of the source group in stable iteration order.</li>
 * <li>Assign each element an index 1..n (1-based for {@link Permutation}).</li>
 * <li>For each element g build an array mapping[i] = index of (g *
 * element_i).</li>
 * <li>Collect all such permutations; operation is permutation composition.</li>
 * </ul>
 * The mapping is injective; thus the resulting permutation set has the same
 * cardinality as the source group (Cayley's theorem).
 */
public final class CayleyPermutationGroup<E extends Element> implements Group<Permutation> {
    private final Group<E> source;
    private final List<E> ordered; // deterministic ordering of source elements
    private final Map<E, Integer> index; // element -> 1-based index
    private final Set<Permutation> perms; // image of left regular representation
    private final Operation<Permutation> op;
    private final Permutation identity;

    public CayleyPermutationGroup(Group<E> source) {
        if (source == null)
            throw new IllegalArgumentException("source group must not be null");
        this.source = source;
        this.ordered = new ArrayList<>(source.elements());
        // preserve insertion order but provide deterministic iteration (LinkedHashMap)
        this.index = new LinkedHashMap<>();
        for (int i = 0; i < ordered.size(); i++) {
            index.put(ordered.get(i), i + 1); // 1-based
        }
        this.perms = buildPermutations();
        this.op = new PermutationOperation();
        this.identity = permutationOf(source.identity());
    }

    private Set<Permutation> buildPermutations() {
        return ordered.stream()
                .map(this::permutationOf)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Permutation permutationOf(E g) {
        int n = ordered.size();
        int[] mapping = new int[n];
        for (int i = 0; i < n; i++) {
            E x = ordered.get(i);
            E gx = source.operation().calculate(g, x);
            Integer idx = index.get(gx);
            mapping[i] = idx; // idx is 1-based
        }
        return new Permutation(mapping);
    }

    @Override
    public Set<Permutation> elements() {
        return perms;
    }

    @Override
    public Operation<Permutation> operation() {
        return op;
    }

    @Override
    public Permutation identity() {
        return identity;
    }

    @Override
    public Permutation inverse(Permutation element) {
        int[] inv = new int[element.size()];
        int[] mapping = element.mapping();
        for (int i = 0; i < element.size(); i++) {
            inv[mapping[i] - 1] = i + 1;
        }
        return new Permutation(inv);
    }

    /** Source group used for Cayley representation. */
    public Group<E> sourceGroup() {
        return source;
    }

    /** Ordered list of source elements corresponding to positions 1..n. */
    public List<E> orderedElements() {
        return Collections.unmodifiableList(ordered);
    }
}
