
package d021248.group.groups.quaternion;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Main.class);
        logger.info("Application started.");

        // Example 1: Create Q_8 and list all elements
        QuaternionGroup q8 = new QuaternionGroup();
        Set<QuaternionElement> elements = q8.elements();
        logger.info("Elements of Q_8 ({} total): {}", elements.size(), elements);

        // Example 2: Show generators
        Set<QuaternionElement> generators = new QuaternionGroupGeneratingSystem().get();
        logger.info("Generators of Q_8: {}", generators);

        // Example 3: Multiply two elements
        QuaternionElement a = elements.iterator().next();
        QuaternionElement b = generators.iterator().next();
        QuaternionElement product = q8.operate(a, b);
        logger.info("Product of {} and {} is {}", a, b, product);

        // Example 4: Inverse of an element
        QuaternionElement inv = a.inverse();
        logger.info("Inverse of {} is {}", a, inv);
    }
}
