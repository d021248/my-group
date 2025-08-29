package d021248.group.groups.dihedral;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import d021248.group.api.SimpleGroup;

public class DihedralGroup extends SimpleGroup<DihedralElement> {
    private final int order;

    public DihedralGroup(int n) {
        super(
                IntStream.range(0, n)
                        .boxed()
                        .flatMap(r -> Set.of(
                                new DihedralElement(n, r, false),
                                new DihedralElement(n, r, true)).stream())
                        .collect(Collectors.toSet()),
                new DihedralOperation());
        this.order = n;
    }

    public int order() {
        return order;
    }
}
