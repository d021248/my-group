package d021248.group.strategy;

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

class StrategyValidationTest {
    @Test
    void cyclicStrategyNonEmptyAndGenerates() {
        var g = new CyclicGroup(12); // generators correspond to values coprime with 12
        var helper = new GroupHelper<CyclicElement>(g);
        Set<CyclicElement> gens = helper.strategyGenerators();
        assertFalse(gens.isEmpty());
        assertTrue(helper.isGeneratingSet(gens));
    }

    @Test
    void symmetricStrategyNonEmptyAndGenerates() {
        var g = new SymmetricGroup(5);
        var helper = new GroupHelper<Permutation>(g);
        Set<Permutation> gens = helper.strategyGenerators();
        assertFalse(gens.isEmpty());
        assertTrue(helper.isGeneratingSet(gens));
    }

    @Test
    void dihedralStrategyNonEmptyAndGenerates() {
        var g = new DihedralGroup(8);
        var helper = new GroupHelper<DihedralElement>(g);
        Set<DihedralElement> gens = helper.strategyGenerators();
        assertFalse(gens.isEmpty());
        assertTrue(helper.isGeneratingSet(gens));
    }
}
