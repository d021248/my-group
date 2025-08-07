package d021248.group.api;

import java.util.Set;
import java.util.function.Supplier;

@FunctionalInterface
public interface GeneratingSystem<T extends Element> extends Supplier<Set<T>> {

}
