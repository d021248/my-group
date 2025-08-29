package d021248.group.groups.symmetric;

import java.util.Set;

import d021248.group.api.SimpleGroup;

public class SymmetricGroup extends SimpleGroup<Permutation> {
    public SymmetricGroup(int n) {
        super(
                generateAllPermutations(n),
                new PermutationOperation());
    }

    private static Set<Permutation> generateAllPermutations(int n) {
        // Generate all permutations of n elements
        Set<Permutation> perms = new java.util.HashSet<>();
        permute(perms, new int[n], new boolean[n], 0);
        return perms;
    }

    private static void permute(Set<Permutation> perms, int[] arr, boolean[] used, int idx) {
        int n = arr.length;
        if (idx == n) {
            perms.add(new Permutation(arr.clone()));
            return;
        }
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                arr[idx] = i + 1;
                used[i] = true;
                permute(perms, arr, used, idx + 1);
                used[i] = false;
            }
        }
    }
}