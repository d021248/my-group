package d021248.group;

@FunctionalInterface
public interface Operation<T extends Element> {

    T calculate(T left, T right);
}
