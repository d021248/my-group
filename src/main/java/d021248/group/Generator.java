package d021248.group;

import java.util.Set;

@FunctionalInterface
public interface Generator<T extends Element> {

    Set<T> generate(Set<T> generators);
    
}
