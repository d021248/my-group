package d021248.group.dihedral;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DihedralElementTest {
    @Test
    void testInverseRotation() {
        DihedralGroup g = new DihedralGroup(5);
        DihedralElement e = new DihedralElement(2, Flip.ROTATION, 5);
        assertEquals(new DihedralElement(3, Flip.ROTATION, 5), g.inverse(e));
        assertEquals(e, g.inverse(g.inverse(e)));
    }

    @Test
    void testInverseReflection() {
        DihedralGroup g = new DihedralGroup(7);
        DihedralElement e = new DihedralElement(1, Flip.REFLECTION, 7);
        assertEquals(e, g.inverse(e));
    }

    @Test
    void testToString() {
        assertEquals("r^3", new DihedralElement(3, Flip.ROTATION, 6).toString());
        assertEquals("r^2 s", new DihedralElement(2, Flip.REFLECTION, 6).toString());
    }
}
