package d021248.group.subgroup;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.util.Constants;

/**
 * Utilities for analyzing properties of subgroups.
 * <p>
 * This class provides methods for testing subgroup properties such as
 * normality, and computing normalizers and centralizers.
 * </p>
 */
public final class SubgroupAnalyzer {

    private SubgroupAnalyzer() {
        // Utility class
    }

    /**
     * Test if a subgroup is normal in the parent group.
     * <p>
     * A subgroup H is normal in G if gHg⁻¹ = H for all g ∈ G.
     * </p>
     *
     * @param parent   the parent group
     * @param subgroup the subgroup to test
     * @return true if the subgroup is normal
     */
    public static <E extends Element> boolean isNormal(Group<E> parent, Subgroup<E> subgroup) {
        Objects.requireNonNull(parent, Constants.NULL_PARENT);
        Objects.requireNonNull(subgroup, Constants.NULL_SUBGROUP);

        Set<E> h = subgroup.elements();
        for (E g : parent.elements()) {
            Set<E> conjugated = new HashSet<>();
            for (E h_elem : h) {
                E conj = parent.operate(parent.operate(g, h_elem), parent.inverse(g));
                conjugated.add(conj);
            }
            if (!conjugated.equals(h)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compute the normalizer of a subgroup in the parent group.
     * <p>
     * N_G(H) = {g ∈ G | gHg⁻¹ = H}
     * </p>
     *
     * @param parent   the parent group
     * @param subgroup the subgroup
     * @return the normalizer as a subgroup
     */
    public static <E extends Element> Subgroup<E> normalizer(Group<E> parent, Subgroup<E> subgroup) {
        Objects.requireNonNull(parent, Constants.NULL_PARENT);
        Objects.requireNonNull(subgroup, Constants.NULL_SUBGROUP);

        Set<E> normalizer = new HashSet<>();
        Set<E> h = subgroup.elements();

        for (E g : parent.elements()) {
            Set<E> conjugated = new HashSet<>();
            for (E h_elem : h) {
                E conj = parent.operate(parent.operate(g, h_elem), parent.inverse(g));
                conjugated.add(conj);
            }
            if (conjugated.equals(h)) {
                normalizer.add(g);
            }
        }

        return SubgroupGenerator.generate(parent, normalizer);
    }

    /**
     * Compute the centralizer of a subgroup in the parent group.
     * <p>
     * C_G(H) = {g ∈ G | gh = hg for all h ∈ H}
     * </p>
     *
     * @param parent   the parent group
     * @param subgroup the subgroup
     * @return the centralizer as a subgroup
     */
    public static <E extends Element> Subgroup<E> centralizer(Group<E> parent, Subgroup<E> subgroup) {
        Objects.requireNonNull(parent, Constants.NULL_PARENT);
        Objects.requireNonNull(subgroup, Constants.NULL_SUBGROUP);

        Set<E> centralizer = new HashSet<>();
        Set<E> h = subgroup.elements();

        for (E g : parent.elements()) {
            boolean commutes = true;
            for (E h_elem : h) {
                E gh = parent.operate(g, h_elem);
                E hg = parent.operate(h_elem, g);
                if (!gh.equals(hg)) {
                    commutes = false;
                    break;
                }
            }
            if (commutes) {
                centralizer.add(g);
            }
        }

        return SubgroupGenerator.generate(parent, centralizer);
    }
}
