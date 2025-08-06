package d021248.group.base;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import d021248.group.api.Element;
import d021248.group.api.Operation;

/**
 * Utility class for generating all elements of a group from its generators.
 */
public class Generator {
    // Private constructor to prevent instantiation
    private Generator() {
    }

    /**
     * Generates the closure of a set of generators under the given operation.
     *
     * @param generators the set of generators
     * @param operation  the group operation
     * @return the set of all elements generated
     */
    public static <T extends Element> Set<T> generate(Set<T> generators, Operation<T> operation) {
        var result = new HashSet<T>();
        var queue = new ArrayDeque<T>();

        // Add all generators and their inverses
        var allGens = generators.stream()
                .flatMap(g -> Arrays.stream(new Element[] { g, g.inverse() }))
                .map(e -> (T) e)
                .toList();

        for (var g : allGens) {
            if (result.add(g)) {
                queue.add(g);
            }
        }

        while (!queue.isEmpty()) {
            var current = queue.poll();
            for (var g : allGens) {
                var next = operation.calculate(current, g);
                if (result.add(next)) {
                    queue.add(next);
                }
            }
        }
        return Set.copyOf(result);
    }
}
