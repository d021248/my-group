package d021248.group.dihedral;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DihedralElementTest {
    @Test
    void testInverseRotation() {
        DihedralElement e = new DihedralElement(2, 0, 5);
        assertEquals(new DihedralElement(3, 0, 5), e.inverse());
        assertEquals(e, e.inverse().inverse());
    }

    @Test
    void testInverseReflection() {
        DihedralElement e = new DihedralElement(1, 1, 7);
        assertEquals(e, e.inverse());
    }

    @Test
    void testToString() {
        assertEquals("r^3", new DihedralElement(3, 0, 6).toString());
        assertEquals("r^2 s", new DihedralElement(2, 1, 6).toString());
    }
}
