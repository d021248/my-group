package d021248.group.cyclic;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import d021248.group.FiniteGroup;
import d021248.group.api.Operation;

public final class CyclicGroup implements FiniteGroup<CyclicElement> {
    private final int modulus; // retained for external inspection via modulus()
    private final Set<CyclicElement> elements;
    private final Operation<CyclicElement> op;
    private final CyclicElement identity;

    public CyclicGroup(int modulus) {
        if (modulus <= 0)
            throw new IllegalArgumentException("modulus must be positive");
        this.modulus = modulus;
        this.elements = IntStream.range(0, modulus)
                .mapToObj(i -> new CyclicElement(i, modulus))
                .collect(Collectors.toUnmodifiableSet());
        this.op = (a, b) -> new CyclicElement(a.value() + b.value(), modulus);
        this.identity = new CyclicElement(0, modulus);
    }

    @Override
    public Set<CyclicElement> elements() {
        return elements;
    }

    @Override
    public Operation<CyclicElement> operation() {
        return op;
    }

    @Override
    public CyclicElement identity() {
        return identity;
    }

    @Override
    public CyclicElement inverse(CyclicElement e) {
        return e.inverse();
    }

    /** Modulus of the underlying Z_n. */
    public int modulus() {
        return modulus;
    }
}
