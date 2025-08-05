package d021248.group.api;

@FunctionalInterface
public interface Operation<T extends Element> {

    T calculate(T left, T right);
}
