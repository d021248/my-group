
package d021248.group.groups.klein;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Main.class);
        logger.info("Application started.");

        // Example 1: Create V_4 and list all elements
        KleinGroup v4 = new KleinGroup();
        Set<KleinElement> elements = v4.elements();
        logger.info("Elements of V_4 ({} total): {}", elements.size(), elements);

        // Example 2: Show generators
        Set<KleinElement> generators = Set.of(
                new KleinElement("a"),
                new KleinElement("b"));
        logger.info("Generators of V_4: {}", generators);

        // Example 3: Multiply two elements
        KleinElement a = elements.iterator().next();
        KleinElement b = generators.iterator().next();
        KleinElement product = v4.operate(a, b);
        logger.info("Product of {} and {} is {}", a, b, product);

        // Example 4: Inverse of an element
        KleinElement inv = a.inverse();
        logger.info("Inverse of {} is {}", a, inv);
    }
}
