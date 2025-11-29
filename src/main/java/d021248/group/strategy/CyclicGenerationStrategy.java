package d021248.group.strategy;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import d021248.group.Group;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;

/**
 * Picks all generators (primitive elements) of Z_n: elements coprime with
 * modulus.
 * <p>
 * Replaces former CyclicGroupHelper.getGenerators.
 * </p>
 */
public final class CyclicGenerationStrategy implements GenerationStrategy<CyclicElement> {
    private static final CyclicGenerationStrategy INSTANCE = new CyclicGenerationStrategy();

    private CyclicGenerationStrategy() {
    }

    public static CyclicGenerationStrategy get() {
        return INSTANCE;
    }

    @Override
    public Set<CyclicElement> generators(Group<CyclicElement> group) {
        int n = ((CyclicGroup) group).order();
        return IntStream.range(0, n).filter(i -> gcd(i, n) == 1)
                .mapToObj(i -> new CyclicElement(i, n))
                .collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public String name() {
        return "cyclic-gcd-generators";
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return Math.abs(a);
    }
}
