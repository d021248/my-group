package d021248.group.symmetric;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started.");

        // Example 1: Create S_4 and list all elements
        SymmetricGroup s4 = new SymmetricGroup(4);
        Set<Permutation> elements = s4.elements();
        logger.info("Elements of S_4 ({} total): {}", elements.size(), elements);

        // Example 2: Show generators
        Set<Permutation> generators = SymmetricGroup.generators(4);
        logger.info("Generators of S_4: {}", generators);

        // Example 3: Multiply two elements
        Permutation a = elements.iterator().next();
        Permutation b = generators.iterator().next();
        Permutation product = s4.operation().calculate(a, b);
        logger.info("Product of {} and {} is {}", a, b, product);

        // Example 4: Inverse of an element
        Permutation inv = a.inverse();
        logger.info("Inverse of {} is {}", a, inv);
    }

}
