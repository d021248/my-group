
package d021248.group.groups.dihedral;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(Main.class);
        logger.info("Application started.");

        // Example 1: Create D_4 and list all elements
        DihedralGroup d4 = new DihedralGroup(4);
        Set<DihedralElement> elements = d4.elements();
        logger.info("Elements of D_4 ({} total): {}", elements.size(), elements);

        // Example 2: Show generators
        Set<DihedralElement> generators = new DihedralGroupGeneratingSystem(4).get();
        logger.info("Generators of D_4: {}", generators);

        // Example 3: Multiply two elements
        DihedralElement a = elements.iterator().next();
        DihedralElement b = generators.iterator().next();
        DihedralElement product = d4.operation().apply(a, b);
        logger.info("Product of {} and {} is {}", a, b, product);

        // Example 4: Inverse of an element
        DihedralElement inv = a.inverse();
        logger.info("Inverse of {} is {}", a, inv);
    }
}
