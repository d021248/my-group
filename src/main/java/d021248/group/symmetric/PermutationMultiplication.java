package d021248.group.symmetric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import d021248.group.api.Operation;

public class PermutationMultiplication implements Operation<Permutation> {

    @Override
    public Permutation calculate(Permutation left, Permutation right) {
        var leftMap = PermutationUtil.toMap(left);
        var rightMap = PermutationUtil.toMap(right);
        var all = new HashSet<>(leftMap.keySet());
        all.addAll(rightMap.keySet());
        var resultCycles = new ArrayList<List<Integer>>();
        var seen = new HashSet<Integer>();
        for (var start : all) {
            if (seen.contains(start)) {
                continue;
            }
            var cycle = new ArrayList<Integer>();
            var x = start;
            do {
                cycle.add(x);
                seen.add(x);
                int y = rightMap.getOrDefault(x, x);
                x = leftMap.getOrDefault(y, y);
            } while (!x.equals(start) && !seen.contains(x));
            if (cycle.size() > 1) {
                resultCycles.add(cycle);
            }
        }
        return new Permutation(resultCycles);
    }
}
