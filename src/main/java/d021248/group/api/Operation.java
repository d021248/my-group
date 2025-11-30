package d021248.group.api;

@FunctionalInterface
public interface Operation<E extends Element> {
    E calculate(E left, E right);
}
