package d021248.group.symmetric;

import d021248.group.api.Operation;

public class PermutationMultiplication implements Operation<Permutation> {

    @Override
    public Permutation calculate(Permutation left, Permutation right) {
        // Compose right after left: result(x) = right(left(x))
        var resultMap = new java.util.HashMap<Integer, Integer>();
        for (Integer key : left.mapping().keySet()) {
            Integer mid = left.mapping().get(key);
            Integer val = right.mapping().getOrDefault(mid, mid);
            resultMap.put(key, val);
        }
        // Also include keys from right that may not be in left
        for (Integer key : right.mapping().keySet()) {
            resultMap.computeIfAbsent(key, k -> right.mapping().get(k));
        }
        return new Permutation(resultMap);
    }
}
