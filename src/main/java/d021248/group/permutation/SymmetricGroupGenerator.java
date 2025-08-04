package d021248.group.permutation;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import d021248.group.Generator;

public class SymmetricGroupGenerator implements Generator<Permutation> {

    @Override
    public Set<Permutation> generate(Set<Permutation> generators) {
        Set<Permutation> result = new HashSet<>();
        Queue<Permutation> queue = new ArrayDeque<>();
        Permutation identity = new Permutation(List.of());
        result.add(identity);
        queue.add(identity);
        List<Permutation> allGens = new ArrayList<>();
        for (Permutation g : generators) {
            allGens.add(g);
            allGens.add(g.inverse());
        }
        var multiplication = new PermutationMultiplication();
        while (!queue.isEmpty()) {
            Permutation current = queue.poll();
            for (Permutation g : allGens) {
                Permutation next = multiplication.calculate(current, g);
                if (result.add(next)) {
                    queue.add(next);
                }
            }
        }
        return result;
    }
}
