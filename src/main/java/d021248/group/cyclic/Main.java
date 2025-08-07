package d021248.group.cyclic;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import d021248.group.base.Cayley;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Cyclic group demo started.");

        // Example 1: Create C_6 and list all elements
        CyclicGroup c6 = new CyclicGroup(6);
        Set<CyclicElement> elements = c6.elements();
        logger.info("Elements of C_6 ({} total): {}", elements.size(), elements);

        // Example 2: Show generators
        Set<CyclicElement> generators = new CyclicGroupGeneratingSystem(6).get();
        logger.info("Generators of C_6: {}", generators);

        // Example 3: Add two elements
        CyclicElement a = new CyclicElement(2, 6);
        CyclicElement b = new CyclicElement(5, 6);
        CyclicElement sum = c6.operation().apply(a, b);
        logger.info("Sum of {} and {} is {}", a, b, sum);

        // Example 4: Inverse of an element
        CyclicElement inv = a.inverse();
        logger.info("Inverse of {} is {}", a, inv);

        // Example 5: Cayley representation of C_6
        Cayley<CyclicElement> cayley = new Cayley<>(c6);
        Set<d021248.group.symmetric.Permutation> cayleySubgroup = cayley.cayleySubgroup();
        logger.info("Cayley subgroup of C_6 ({} permutations): {}", cayleySubgroup.size(), cayleySubgroup);
    }
}
