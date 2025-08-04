package d021248.group.permutation;

import java.util.Set;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        int n = 5;
        Set<Permutation> gens = SymmetricGroupGeneratorFactory.generators(n);
        SymmetricGroupGenerator generator = new SymmetricGroupGenerator();
        Set<Permutation> group = generator.generate(gens);
        if (logger.isLoggable(java.util.logging.Level.INFO)) {
            logger.log(java.util.logging.Level.INFO, "Generators for S_{0}: {1}", new Object[] { n, gens });
            logger.log(java.util.logging.Level.INFO, "All elements of S_{0}:", n);
            for (Permutation p : group) {
                logger.log(java.util.logging.Level.INFO, "{0}", p);
            }
            logger.log(java.util.logging.Level.INFO, "Order of S_{0}: {1}", new Object[] { n, group.size() });
        }
    }
}
