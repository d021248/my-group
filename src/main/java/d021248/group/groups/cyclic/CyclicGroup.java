package d021248.group.groups.cyclic;

import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import d021248.group.api.SimpleGroup;

/**
 * The cyclic group of order n (integers mod n under addition).
 */
public class CyclicGroup extends SimpleGroup<CyclicElement> {
    private final int order;

    public CyclicGroup(int n) {
        super(
                IntStream.range(0, n)
                        .mapToObj(i -> new CyclicElement(i, n))
                        .collect(Collectors.toSet()),
                (BinaryOperator<CyclicElement>) (a, b) -> {
                    int value = (a.value() + b.value()) % n;
                    return new CyclicElement(value, n);
                });
        this.order = n;
    }

    public int order() {
        return order;
    }
}
