package d021248.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import d021248.group.api.Element;

/** Reusable assertion helpers for group tests. */
public final class GroupAssertions {
    private GroupAssertions() {
    }

    public static <E extends Element> void assertIdentityLaw(Group<E> group) {
        var id = group.identity();
        for (E e : group.elements()) {
            assertEquals(e, group.operate(id, e), "Left identity fails for " + e);
            assertEquals(e, group.operate(e, id), "Right identity fails for " + e);
        }
    }

    public static <E extends Element> void assertInverseLaw(Group<E> group) {
        var id = group.identity();
        for (E e : group.elements()) {
            E inv = group.inverse(e);
            assertTrue(group.elements().contains(inv), "Inverse not in set for " + e);
            assertEquals(id, group.operate(e, inv), "e * e^{-1} != id for " + e);
            assertEquals(id, group.operate(inv, e), "e^{-1} * e != id for " + e);
        }
    }
}