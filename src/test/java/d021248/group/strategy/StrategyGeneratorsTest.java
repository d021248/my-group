package d021248.group.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.GroupHelper;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

class StrategyGeneratorsTest {

    @Test
    void cyclicGeneratorsGenerateGroup() {
        CyclicGroup g = new CyclicGroup(10);
        GroupHelper<CyclicElement> helper = new GroupHelper<>(g);
        Set<CyclicElement> gens = helper.strategyGenerators();
        assertFalse(gens.isEmpty());
        assertTrue(helper.isGeneratingSet(gens));
    }

    @Test
    void symmetricGeneratorsGenerateGroup() {
        SymmetricGroup g = new SymmetricGroup(4);
        GroupHelper<Permutation> helper = new GroupHelper<>(g);
        Set<Permutation> gens = helper.strategyGenerators();
        assertEquals(2, gens.size());
        assertTrue(helper.isGeneratingSet(gens));
    }

    @Test
    void dihedralGeneratorsGenerateGroup() {
        DihedralGroup g = new DihedralGroup(6);
        GroupHelper<DihedralElement> helper = new GroupHelper<>(g);
        Set<DihedralElement> gens = helper.strategyGenerators();
        assertEquals(2, gens.size());
        assertTrue(helper.isGeneratingSet(gens));
    }
}
