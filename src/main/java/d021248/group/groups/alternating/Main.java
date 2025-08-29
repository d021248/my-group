
package d021248.group.groups.alternating;

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import d021248.group.groups.symmetric.Permutation;
import d021248.group.groups.symmetric.PermutationHelper;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Application started.");

        // Example 1: Create A_4 and list all elements
        AlternatingGroup a4 = new AlternatingGroup(4);
        Set<Permutation> elements = a4.elements();
        logger.info("Elements of A_4 ({} total): {}", elements.size(), elements);

        // Example 2: Multiply two elements
        Permutation a = elements.iterator().next();
        Permutation b = elements.iterator().next();
        Permutation product = a4.operate(a, b);
        logger.info("Product of {} and {} is {}", a, b, product);

        // Example 3: Inverse of an element
        Permutation inv = a.inverse();
        logger.info("Inverse of {} is {}", a, inv);

        // Example 4: Apply a permutation to a string using PermutationHelper.apply
        String input = "ABCD";
        Permutation perm = elements.iterator().next(); // Use the first permutation as an example
        String permutedString = PermutationHelper.apply(perm, input);
        logger.info("Applying permutation {} to '{}' using apply() gives '{}'", perm, input, permutedString);
    }

}
