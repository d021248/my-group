package d021248.group.symmetric;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import d021248.group.Group;
import d021248.group.api.Operation;

/**
 * Alternating group A_n consisting of all even permutations in S_n.
 * <p>
 * A permutation is even if it can be represented as an even number of
 * transpositions. A_n has order n!/2 for n ≥ 2.
 * </p>
 *
 * <p>
 * Example: A_3 has three elements: identity, (1 2 3), and (1 3 2). The two
 * 3-cycles are even permutations, while transpositions (1 2), (1 3), (2 3) are
 * odd and excluded.
 * </p>
 *
 * <p>
 * For n ≥ 5, A_n is simple (has no nontrivial normal subgroups).
 * </p>
 *
 * <pre>
 * {
 *     &#64;code
 *     AlternatingGroup a4 = new AlternatingGroup(4);
 *     System.out.println(a4.order()); // 12 (= 24 / 2)
 * }
 * </pre>
 */
public final class AlternatingGroup implements Group<Permutation> {
    private final int n;
    private final Set<Permutation> elements;
    private final Operation<Permutation> op = Permutation::compose;
    private final Permutation identity;

    /**
     * Create alternating group A_n.
     * 
     * @param n degree (number of elements being permuted, must be >= 1)
     * @throws IllegalArgumentException if n < 1 or n > 9
     */
    public AlternatingGroup(int n) {
        if (n < 1)
            throw new IllegalArgumentException("n must be >= 1");
        if (n > 9)
            throw new IllegalArgumentException("n too large (max 9 for enumeration)");
        this.n = n;
        this.elements = generateEvenPermutations(n);
        this.identity = buildIdentity(n);
    }

    private static Set<Permutation> generateEvenPermutations(int n) {
        SymmetricGroup sn = new SymmetricGroup(n);
        return sn.elements().stream()
                .filter(p -> p.sign() == 1)
                .collect(Collectors.toUnmodifiableSet());
    }

    private static Permutation buildIdentity(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
            arr[i] = i + 1;
        return new Permutation(arr);
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

    /**
     * Return the degree n (number of elements being permuted).
     */
    public int degree() {
        return n;
    }

    @Override
    public String toString() {
        return "A_" + n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AlternatingGroup other))
            return false;
        return n == other.n;
    }

    @Override
    public int hashCode() {
        return Objects.hash(n);
    }
}
