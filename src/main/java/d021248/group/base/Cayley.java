package d021248.group.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Group;
import d021248.group.api.Operation;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.PermutationOperation;

/**
 * Cayley representation of a group: embeds the group as a subgroup of the
 * symmetric group.
 * For each element g in G, the left-multiplication map L_g: x -> g * x is a
 * permutation of G.
 */
public class Cayley<T extends Element> {
    private final Group<T> group;
    private final List<T> elements;
    private final Map<T, Permutation> cayleyMap;

    /**
     * Constructs the Cayley representation for the given group.
     * 
     * @param group the group to embed
     */
    public Cayley(Group<T> group) {
        this.group = group;
        this.elements = new ArrayList<>(group.elements());
        this.cayleyMap = new HashMap<>();
        buildCayleyMap();
    }

    private void buildCayleyMap() {
        int n = elements.size();
        for (int i = 0; i < n; i++) {
            T g = elements.get(i);
            int[] mapping = new int[n];
            for (int j = 0; j < n; j++) {
                T x = elements.get(j);
                T gx = group.operation().apply(g, x);
                int k = elements.indexOf(gx);
                mapping[j] = k + 1; // Permutation uses 1-based indices
            }
            cayleyMap.put(g, new Permutation(mapping));
        }
    }

    /**
     * Returns the permutation corresponding to left-multiplication by g.
     */
    public Permutation getPermutation(T g) {
        return cayleyMap.get(g);
    }

    /**
     * Returns the set of all Cayley permutations (isomorphic image).
     */
    public Set<Permutation> cayleySubgroup() {
        return new HashSet<>(cayleyMap.values());
    }

    /**
     * Returns the underlying group.
     */
    public Group<T> getGroup() {
        return group;
    }

    /**
     * Returns the Cayley isomorphic image as a Group<Permutation> (subgroup of the
     * symmetric group).
     */
    public Group<Permutation> asSymmetricGroup() {
        Set<Permutation> perms = cayleySubgroup();
        Operation<Permutation> op = new PermutationOperation();
        return new Group<Permutation>() {
            @Override
            public Set<Permutation> elements() {
                return perms;
            }

            @Override
            public Operation<Permutation> operation() {
                return op;
            }
        };
    }
}
