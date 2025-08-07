package d021248.group.elementaryabelian;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import d021248.group.base.AbstractGroup;

public class ElementaryAbelianGroup extends AbstractGroup<ElementaryAbelianElement> {
    private final int p;
    private final int n;

    public ElementaryAbelianGroup(int p, int n) {
        super(null, new ElementaryAbelianOperation());
        this.p = p;
        this.n = n;
    }

    @Override
    public Set<ElementaryAbelianElement> elements() {
        Set<ElementaryAbelianElement> elements = new HashSet<>();
        int[] values = new int[n];
        generate(elements, values, 0);
        return elements;
    }

    private void generate(Set<ElementaryAbelianElement> elements, int[] values, int idx) {
        if (idx == n) {
            elements.add(new ElementaryAbelianElement(Arrays.copyOf(values, n), p));
            return;
        }
        for (int i = 0; i < p; i++) {
            values[idx] = i;
            generate(elements, values, idx + 1);
        }
    }
}
