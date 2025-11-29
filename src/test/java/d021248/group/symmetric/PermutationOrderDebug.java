package d021248.group.symmetric;

import d021248.group.api.Element;

public class PermutationOrderDebug {
    public static void main(String[] args) {
        SymmetricGroup s5 = new SymmetricGroup(5);

        // Test identity
        Permutation id = Permutation.identity(5);
        System.out.println("Identity: " + id);
        System.out.println("Group identity: " + s5.identity());
        System.out.println("Are they equal? " + id.equals(s5.identity()));

        // Test one step
        Element current = id;
        Element groupId = (Element) s5.identity();
        System.out.println("\nManual order computation:");
        int steps = 0;
        while (!current.equals(groupId) && steps < 5) {
            System.out.println("Step " + steps + ": current = " + current);
            current = (Element) s5.operate((Permutation) current, id);
            steps++;
        }
        System.out.println("Final: current = " + current + ", steps = " + steps);

        // Test transposition
        System.out.println("\n\nTesting transposition:");
        Permutation trans = Permutation.transposition(1, 2, 5);
        System.out.println("Transposition: " + trans);

        current = trans;
        steps = 0;
        while (!current.equals(groupId) && steps < 5) {
            System.out.println("Step " + steps + ": current = " + current);
            current = (Element) s5.operate((Permutation) current, trans);
            steps++;
        }
        System.out.println("Final: current = " + current + ", steps = " + steps);
        // Test 3-cycle
        System.out.println("\n\nTesting 3-cycle:");
        Permutation cycle3 = Permutation.cycle(1, 2, 3);
        System.out.println("3-cycle: " + cycle3);
        System.out.println("3-cycle size: " + cycle3.size());
        System.out.println("Group size: 5");
        System.out.println("Are sizes compatible? " + (cycle3.size() == 5));
    }
}
