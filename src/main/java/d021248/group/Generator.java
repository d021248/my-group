package d021248.group;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class Generator {

    // Private constructor to prevent instantiation
    private Generator() {
    }

    public static <T extends Element> Set<T> generate(Set<T> generators, Operation<T> operation) {

        Set<T> result = new HashSet<>();
        Queue<T> queue = new ArrayDeque<>();

        List<T> allGens = new ArrayList<>();
        for (T g : generators) {
            allGens.add(g);
            @SuppressWarnings("unchecked")
            T inverse = (T) g.inverse();
            allGens.add(inverse);
        }

        // Add all generators to the queue and result to start the process
        for (T g : allGens) {
            if (result.add(g)) {
                queue.add(g);
            }
        }

        while (!queue.isEmpty()) {
            T current = queue.poll();
            for (T g : allGens) {
                T next = operation.calculate(current, g);
                if (result.add(next)) {
                    queue.add(next);
                }
            }
        }
        return result;
    }

}
