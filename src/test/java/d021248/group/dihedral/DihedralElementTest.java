package d021248.group.dihedral;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DihedralElementTest {
    @Test
    void testInverseRotation() {
        DihedralElement e = new DihedralElement(2, Flip.ROTATION, 5);
        assertEquals(new DihedralElement(3, Flip.ROTATION, 5), e.inverse());
        assertEquals(e, e.inverse().inverse());
    }

    @Test
    void testInverseReflection() {
        DihedralElement e = new DihedralElement(1, Flip.REFLECTION, 7);
        assertEquals(e, e.inverse());
    }

    @Test
    void testToString() {
        assertEquals("r^3", new DihedralElement(3, Flip.ROTATION, 6).toString());
        assertEquals("r^2 s", new DihedralElement(2, Flip.REFLECTION, 6).toString());
    }
}
