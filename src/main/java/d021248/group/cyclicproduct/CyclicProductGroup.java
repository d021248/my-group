package d021248.group.cyclicproduct;

import java.util.HashSet;
import java.util.Set;

import d021248.group.base.AbstractGroup;
import d021248.group.cyclic.CyclicElement;

public class CyclicProductGroup extends AbstractGroup<CyclicProductElement> {
    private final int n;
    private final int m;

    public CyclicProductGroup(int n, int m) {
        super(null, new CyclicProductOperation());
        this.n = n;
        this.m = m;
    }

    @Override
    public Set<CyclicProductElement> elements() {
        Set<CyclicProductElement> elements = new HashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                elements.add(new CyclicProductElement(new CyclicElement(i, n), new CyclicElement(j, m)));
            }
        }
        return elements;
    }
}
