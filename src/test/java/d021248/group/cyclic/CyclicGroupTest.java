package d021248.group.cyclic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

class CyclicGroupTest {
    @Test
    void testElements() {
        CyclicGroup c4 = new CyclicGroup(4);
        Set<CyclicElement> elements = c4.elements();
        assertEquals(4, elements.size());
        for (int i = 0; i < 4; i++) {
            assertTrue(elements.contains(new CyclicElement(i, 4)));
        }
    }

    @Test
    void testOperation() {
        CyclicGroup c6 = new CyclicGroup(6);
        CyclicElement a = new CyclicElement(2, 6);
        CyclicElement b = new CyclicElement(5, 6);
        assertEquals(new CyclicElement(1, 6), c6.operation().apply(a, b));
    }

    @Test
    void testGenerators() {
        Set<CyclicElement> gens = CyclicGroupHelper.getGenerators(5);
        assertTrue(gens.contains(new CyclicElement(1, 5)));
    }
}
