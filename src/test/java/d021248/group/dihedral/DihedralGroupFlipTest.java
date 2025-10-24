package d021248.group.dihedral;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import d021248.group.GroupAssertions;

class DihedralGroupFlipTest {

    @Test
    void identityIsRotation() {
        DihedralGroup g = new DihedralGroup(6);
        assertEquals(Flip.ROTATION, g.identity().flip());
    }

    @Test
    void reflectionsSelfInverse() {
        DihedralGroup g = new DihedralGroup(5);
        for (DihedralElement e : g.elements()) {
            if (e.flip() == Flip.REFLECTION) {
                assertEquals(e, e.inverse(), "Reflection should be self-inverse");
            }
        }
    }

    @Test
    void rotationInverseWorks() {
        DihedralGroup g = new DihedralGroup(7);
        for (DihedralElement e : g.elements()) {
            if (e.flip() == Flip.ROTATION) {
                DihedralElement inv = e.inverse();
                // e * inv = identity
                DihedralElement prod = g.operation().calculate(e, inv);
                assertEquals(g.identity(), prod);
            }
        }
    }

    @Test
    void groupAssertionsHold() {
        DihedralGroup g = new DihedralGroup(4);
        GroupAssertions.assertIdentityLaw(g);
        GroupAssertions.assertInverseLaw(g);
    }
}
