package d021248.group.base;

import java.util.Set;
import java.util.function.Supplier;

import d021248.group.api.Element;
import d021248.group.api.Group;
import d021248.group.api.Operation;

public abstract class AbstractGroup<T extends Element> implements Group<T> {

    private final Set<T> elements;
    private final Operation<T> operation;

    protected AbstractGroup(Set<T> elements, Operation<T> operation) {
        this.elements = Generator.generate(elements, operation);
        this.operation = operation;
    }

    protected AbstractGroup(Supplier<Set<T>> supplier, Operation<T> operation) {
        this(supplier.get(), operation);

    }

    @Override
    public Set<T> elements() {
        return elements;
    }

    @Override
    public Operation<T> operation() {
        return operation;
    }

}
