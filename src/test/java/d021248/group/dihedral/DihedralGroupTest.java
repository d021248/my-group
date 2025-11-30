package d021248.group.dihedral;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.strategy.DihedralGenerationStrategy;

class DihedralGroupTest {
    @Test
    void testElements() {
        DihedralGroup d4 = new DihedralGroup(4);
        Set<DihedralElement> elements = d4.elements();
        assertEquals(8, elements.size());
        for (int r = 0; r < 4; r++) {
            assertTrue(elements.contains(new DihedralElement(r, Flip.ROTATION, 4)));
            assertTrue(elements.contains(new DihedralElement(r, Flip.REFLECTION, 4)));
        }
    }

    @Test
    void testOperation() {
        DihedralGroup d3 = new DihedralGroup(3);
        DihedralElement a = new DihedralElement(1, Flip.ROTATION, 3); // r
        DihedralElement b = new DihedralElement(0, Flip.REFLECTION, 3); // s
        assertEquals(new DihedralElement(1, Flip.REFLECTION, 3), d3.operation().calculate(a, b));
        assertEquals(new DihedralElement(0, Flip.ROTATION, 3), d3.operation().calculate(b, b)); // expect r^0 (identity)
    }

    @Test
    void testGenerators() {
        DihedralGroup d5 = new DihedralGroup(5);
        var gens = DihedralGenerationStrategy.get().generators(d5);
        assertTrue(gens.contains(new DihedralElement(1, Flip.ROTATION, 5)));
        assertTrue(gens.contains(new DihedralElement(0, Flip.REFLECTION, 5)));
    }
}
