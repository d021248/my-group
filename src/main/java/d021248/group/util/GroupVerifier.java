package d021248.group.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import d021248.group.Group;
import d021248.group.api.Element;

/**
 * Utility to verify core group axioms for a given concrete {@link Group}.
 * Intended for educational use; performs exhaustive checks over the provided
 * finite element set. For infinite groups, caller must provide a finite
 * representative subset (closure then becomes approximate).
 */
public final class GroupVerifier {
    private GroupVerifier() {
    }

    public static <E extends Element> Result verify(Group<E> group) {
        List<String> violations = new ArrayList<>();
        Set<E> elems = group.elements();
        if (elems.isEmpty()) {
            violations.add("Element set is empty");
            return new Result(false, violations);
        }
        checkIdentity(group, elems, violations);
        checkClosureAndInverses(group, elems, violations);
        checkAssociativity(group, elems, violations);
        return new Result(violations.isEmpty(), violations);
    }

    private static <E extends Element> void checkIdentity(Group<E> group, Set<E> elems, List<String> violations) {
        E id = group.identity();
        if (id == null) {
            violations.add("Identity is null");
            return;
        }
        for (E a : elems) {
            if (!group.operate(id, a).equals(a)) {
                violations.add("Left identity fails for element: " + a);
            }
            if (!group.operate(a, id).equals(a)) {
                violations.add("Right identity fails for element: " + a);
            }
        }
    }

    private static <E extends Element> void checkClosureAndInverses(Group<E> group, Set<E> elems,
            List<String> violations) {
        for (E a : elems) {
            @SuppressWarnings("unchecked")
            E inv = (E) a.inverse();
            if (inv == null) {
                violations.add("Inverse is null for element: " + a);
            } else if (!elems.contains(inv)) {
                violations.add("Inverse not in set for element: " + a);
            }
            for (E b : elems) {
                E prod = group.operate(a, b);
                if (prod == null) {
                    violations.add("Operation returned null for pair: " + a + ", " + b);
                } else if (!elems.contains(prod)) {
                    violations.add("Closure violated for pair: " + a + ", " + b + " -> " + prod);
                }
            }
        }
    }

    private static <E extends Element> void checkAssociativity(Group<E> group, Set<E> elems, List<String> violations) {
        for (E a : elems) {
            for (E b : elems) {
                for (E c : elems) {
                    E left = group.operate(group.operate(a, b), c);
                    E right = group.operate(a, group.operate(b, c));
                    if (!left.equals(right)) {
                        violations.add("Associativity fails for triple: " + a + ", " + b + ", " + c);
                    }
                }
            }
        }
    }

    public record Result(boolean ok, List<String> violations) {
        public String summary() {
            if (ok)
                return "All axioms satisfied.";
            StringBuilder sb = new StringBuilder("Violations (" + violations.size() + "):\n");
            for (String v : violations)
                sb.append(" - ").append(v).append('\n');
            return sb.toString();
        }
    }
}
