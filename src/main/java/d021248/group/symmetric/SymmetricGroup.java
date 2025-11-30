package d021248.group.symmetric;

import java.util.HashSet;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Operation;

public final class SymmetricGroup implements Group<Permutation> {
    private final int n;
    private final Set<Permutation> elements;
    // Group operation is composition: left ∘ right
    private final Operation<Permutation> op = Permutation::compose;
    private final Permutation identity;

    public SymmetricGroup(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be >= 1");
        if (n > 9)
            throw new IllegalArgumentException("n too large (factorial explosion for enumeration; max 9)");
        this.n = n;
        this.elements = generateAll(n);
        this.identity = buildIdentity(n);
    }

    private static Set<Permutation> generateAll(int n) {
        Set<Permutation> all = new HashSet<>();
        int[] base = new int[n];
        for (int i = 0; i < n; i++)
            base[i] = i + 1;
        permute(all, base, 0);
        return Set.copyOf(all);
    }

    private static void permute(Set<Permutation> acc, int[] arr, int idx) {
        if (idx == arr.length) {
            acc.add(new Permutation(arr));
            return;
        }
        for (int i = idx; i < arr.length; i++) {
            swap(arr, idx, i);
            permute(acc, arr, idx + 1);
            swap(arr, idx, i);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    @Override
    public Set<Permutation> elements() {
        return elements;
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

    private static Permutation buildIdentity(int n) {
        int[] id = new int[n];
        for (int i = 0; i < n; i++)
            id[i] = i + 1;
        return new Permutation(id);
    }

    /** Degree n of S_n. */
    public int degree() {
        return n;
    }
}
