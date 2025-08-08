package d021248.group.base;

import java.util.Arrays;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        // S4: Symmetric group on 4 elements
        SymmetricGroup s4 = new SymmetricGroup(4);
        Subgroup<Permutation> subgroups = new Subgroup<>(s4);
        int count = 1;
        for (Set<Permutation> subgroup : subgroups.getSubgroups()) {
            StringBuilder sb = new StringBuilder();
            sb.append("Subgroup ").append(count).append(":\n");
            for (Permutation p : subgroup) {
                sb.append(Arrays.toString(p.mapping())).append(" ");
            }
            sb.append("\n---");
            logger.info(sb.toString());
            count++;
        }
        logger.info("Total subgroups: {}", subgroups.getSubgroups().size());
    }
}
