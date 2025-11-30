package d021248.group;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Tests enforcing strict group semantics (non-null identity and inverse,
 * closure).
 */
class GroupSemanticsTest {
    @Test
    void cyclicIdentityInverse() {
        CyclicGroup c8 = new CyclicGroup(8);
        var id = c8.identity();
        assertNotNull(id);
        for (var e : c8.elements()) {
            var inv = c8.inverse(e);
            assertNotNull(inv);
            assertEquals(id, c8.operation().calculate(e, inv));
            assertEquals(id, c8.operation().calculate(inv, e));
        }
    }

    @Test
    void dihedralIdentityInverse() {
        DihedralGroup d4 = new DihedralGroup(4);
        var id = d4.identity();
        for (var e : d4.elements()) {
            var inv = d4.inverse(e);
            assertEquals(id, d4.operation().calculate(e, inv));
        }
    }

    @Test
    void symmetricIdentityInverse() {
        SymmetricGroup s3 = new SymmetricGroup(3);
        var id = s3.identity();
        for (Permutation p : s3.elements()) {
            Permutation inv = s3.inverse(p);
            assertEquals(id, s3.operation().calculate(p, inv));
        }
    }

    @Test
    void generatorClosureContainsIdentity() {
        CyclicGroup c5 = new CyclicGroup(5);
        Set<CyclicElement> closure = Generator.generate(c5, Set.of(new CyclicElement(2, 5)));
        assertTrue(closure.contains(c5.identity()));
    }
}
