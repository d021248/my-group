package d021248.group.strategy;

import java.util.Set;

import d021248.group.Group;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;

/**
 * Standard generating set for D_n: rotation r and reflection s.
 * <p>
 * Replaces former DihedralGroupHelper.getGenerators.
 * </p>
 */
public final class DihedralGenerationStrategy implements GenerationStrategy<DihedralElement> {
    private static final DihedralGenerationStrategy INSTANCE = new DihedralGenerationStrategy();

    private DihedralGenerationStrategy() {
    }

    public static DihedralGenerationStrategy get() {
        return INSTANCE;
    }

    @Override
    public Set<DihedralElement> generators(Group<DihedralElement> group) {
        int n = ((DihedralGroup) group).order() / 2; // order = 2n
        return Set.of(new DihedralElement(1, d021248.group.dihedral.Flip.ROTATION, n),
                new DihedralElement(0, d021248.group.dihedral.Flip.REFLECTION, n));
    }

    @Override
    public String name() {
        return "dihedral-standard";
    }
}
