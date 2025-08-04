package d021248.group;

import java.util.Set;

public interface Group<T extends Element> {

    Set<T> elements();

    Operation<T> operation();

}
