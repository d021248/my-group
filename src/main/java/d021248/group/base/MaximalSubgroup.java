package d021248.group.base;

import d021248.group.api.Group;
import d021248.group.api.Element;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

/**
 * Utility class to compute all maximal subgroups of a group.
 */
public class MaximalSubgroup {
    private MaximalSubgroup() {}

    /**
     * Returns a list of all maximal subgroups of the given group.
     *
     * @param group the group
     * @return list of maximal subgroups
     */
    public static <T extends Element> List<Set<T>> compute(Group<T> group) {
        List<Set<T>> allSubgroups = new Subgroup<>(group).getSubgroups();
        List<Set<T>> maximalSubgroups = new ArrayList<>();
        Set<T> groupElements = group.elements();
        for (Set<T> sg : allSubgroups) {
            if (sg.size() == groupElements.size()) continue; // skip the group itself
            boolean isMaximal = true;
            for (Set<T> other : allSubgroups) {
                if (other == sg || other.size() == groupElements.size()) continue;
                if (sg.size() < other.size() && other.containsAll(sg)) {
                    isMaximal = false;
                    break;
                }
            }
            if (isMaximal) {
                maximalSubgroups.add(sg);
            }
        }
        return maximalSubgroups;
    }
}
