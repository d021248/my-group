package d021248.group.elementaryabelian;

import d021248.group.api.Operation;

public class ElementaryAbelianOperation implements Operation<ElementaryAbelianElement> {
    @Override
    public ElementaryAbelianElement calculate(ElementaryAbelianElement left, ElementaryAbelianElement right) {
        int n = left.values().length;
        int p = left.p();
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
            result[i] = (left.values()[i] + right.values()[i]) % p;
        }
        return new ElementaryAbelianElement(result, p);
    }
}
