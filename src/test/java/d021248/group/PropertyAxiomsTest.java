package d021248.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        var table = d021248.group.util.CayleyTable.build(g);
        List<CyclicElement> elems = table.elements();
        CyclicElement id = g.identity();
        // Row of identity (id * a) equals a
        int idRow = elems.indexOf(id);
        for (int j = 0; j < elems.size(); j++) {
            assertEquals(elems.get(j), table.values().get(idRow).get(j));
        }
        // Column of identity (a * id) equals a
        int idCol = elems.indexOf(id);
        for (int i = 0; i < elems.size(); i++) {
            assertEquals(elems.get(i), table.values().get(i).get(idCol));
        }
    }
}
