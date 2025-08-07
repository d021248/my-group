package d021248.group.api;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Operation<T extends Element> extends BiFunction<T, T, T> {

}
