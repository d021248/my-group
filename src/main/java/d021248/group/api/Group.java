package d021248.group.api;

import java.util.Set;

public interface Group<T extends Element> {

    Set<T> elements();

    Operation<T> operation();

}
