package d021248.group.cyclic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class CyclicGroupHelper {
    private CyclicGroupHelper() {
    }

    public static Set<CyclicElement> getGenerators(int modulus) {
        if (modulus <= 0)
            throw new IllegalArgumentException("modulus must be positive");
        // Generators for additive cyclic group Z_n are elements whose value and modulus
        // are coprime.
        return IntStream.range(0, modulus)
                .filter(i -> gcd(i, modulus) == 1)
                .mapToObj(i -> new CyclicElement(i, modulus))
                .collect(Collectors.toUnmodifiableSet());
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
