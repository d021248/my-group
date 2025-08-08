package d021248.group.base;

import java.util.Arrays;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

public class MainWithGenerator {
    private static final Logger logger = LogManager.getLogger(MainWithGenerator.class);

    public static void main(String[] args) {
        // S5: Symmetric group on 5 elements
        SymmetricGroup s5 = new SymmetricGroup(7);
        SubgroupWithGenerator<Permutation> subgroups = new SubgroupWithGenerator<>(s5);
        int count = 1;
        for (Set<Permutation> subgroup : subgroups.getSubgroups()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Subgroup ").append(count).append(":\n");
            for (Permutation p : subgroup) {
                sb.append(Arrays.toString(p.mapping())).append(" ");
            }
            sb.append("\n---");
            if (logger.isInfoEnabled()) {
                logger.info(sb.toString());
            }
            count++;
        }
        logger.info("Total subgroups: {}", subgroups.getSubgroups().size());
    }
}
