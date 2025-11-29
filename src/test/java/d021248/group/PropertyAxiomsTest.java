package d021248.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.util.GroupVerifier;
import net.jqwik.api.Arbitraries;
import net.jqwik.api.Arbitrary;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.Provide;

/** Property-based verification of group axioms for small cyclic groups. */
class PropertyAxiomsTest {

    // Arbitrary positive modulus up to 20 (kept small for test speed)
    @Provide
    Arbitrary<Integer> moduli() {
        return Arbitraries.integers().between(1, 20);
    }

    @Property
    @DisplayName("Cyclic groups satisfy axioms")
    void cyclicGroupAxioms(@ForAll("moduli") int m) {
        CyclicGroup g = new CyclicGroup(m);
        var result = GroupVerifier.verify(g);
        assertTrue(result.ok(), () -> "Violations for modulus=" + m + "\n" + result.summary());
    }

    @Test
    @DisplayName("Sample cayley table contains identity row and column")
    void cayleyIdentityPattern() {
        CyclicGroup g = new CyclicGroup(5);
        // Build table using GroupTableFormatter (more comprehensive than CayleyTable)
        List<CyclicElement> elems = new ArrayList<>(g.elements());
        CyclicElement id = g.identity();

        // Verify identity row: id * a = a for all a
        for (CyclicElement elem : elems) {
            assertEquals(elem, g.operate(id, elem));
        }

        // Verify identity column: a * id = a for all a
        for (CyclicElement elem : elems) {
            assertEquals(elem, g.operate(elem, id));
        }
    }
}
