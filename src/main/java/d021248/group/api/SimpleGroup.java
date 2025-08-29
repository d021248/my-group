package d021248.group.api;

import java.util.Set;
import java.util.function.BinaryOperator;

public class SimpleGroup<T> {
    private final Set<T> elements;
    private final BinaryOperator<T> operation;

    public SimpleGroup(Set<T> elements, BinaryOperator<T> operation) {
        this.elements = elements;
        this.operation = operation;
    }

    public Set<T> elements() {
        return elements;
    }

    public T operate(T a, T b) {
        return operation.apply(a, b);
    }

    public int order() {
        return elements.size();
    }
}
