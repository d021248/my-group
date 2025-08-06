package d021248.group.dihedral;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Dihedral group demo started.");

        // Example 1: Create D_4 and list all elements
        DihedralGroup d4 = new DihedralGroup(4);
        Set<DihedralElement> elements = d4.elements();
        logger.info("Elements of D_4 ({} total): {}", elements.size(), elements);

        // Example 2: Show generators
        Set<DihedralElement> generators = DihedralGroupHelper.getGenerators(4);
        logger.info("Generators of D_4: {}", generators);

        // Example 3: Multiply two elements
        DihedralElement a = new DihedralElement(1, 0, 4); // r
        DihedralElement b = new DihedralElement(0, 1, 4); // s
        DihedralElement product = d4.operation().calculate(a, b);
        logger.info("Product of {} and {} is {}", a, b, product);

        // Example 4: Inverse of an element
        DihedralElement inv = a.inverse();
        logger.info("Inverse of {} is {}", a, inv);
    }
}
