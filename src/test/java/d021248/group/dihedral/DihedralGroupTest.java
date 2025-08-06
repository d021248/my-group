package d021248.group.dihedral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

class DihedralGroupTest {
    @Test
    void testElements() {
        DihedralGroup d4 = new DihedralGroup(4);
        Set<DihedralElement> elements = d4.elements();
        assertEquals(8, elements.size());
        for (int r = 0; r < 4; r++) {
            assertTrue(elements.contains(new DihedralElement(r, 0, 4)));
            assertTrue(elements.contains(new DihedralElement(r, 1, 4)));
        }
    }

    @Test
    void testOperation() {
        DihedralGroup d3 = new DihedralGroup(3);
        DihedralElement a = new DihedralElement(1, 0, 3); // r
        DihedralElement b = new DihedralElement(0, 1, 3); // s
        assertEquals(new DihedralElement(1, 1, 3), d3.operation().calculate(a, b));
        assertEquals(new DihedralElement(2, 0, 3), d3.operation().calculate(b, b));
    }

    @Test
    void testGenerators() {
        Set<DihedralElement> gens = DihedralGroupHelper.getGenerators(5);
        assertTrue(gens.contains(new DihedralElement(1, 0, 5)));
        assertTrue(gens.contains(new DihedralElement(0, 1, 5)));
    }
}
