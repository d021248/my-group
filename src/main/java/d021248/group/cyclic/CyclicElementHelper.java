package d021248.group.cyclic;

/**
 * Utility methods for CyclicElement logic (inverse, etc).
 */
public class CyclicElementHelper {
    private CyclicElementHelper() {
    }

    public static CyclicElement inverse(CyclicElement e) {
        int inv = (e.order() - e.value()) % e.order();
        if (inv < 0) {
            inv += e.order();
        }
        return new CyclicElement(inv, e.order());
    }

    public static void validate(int value, int order) {
        if (order <= 0) {
            throw new IllegalArgumentException("Order must be positive.");
        }
        if (value < 0 || value >= order) {
            throw new IllegalArgumentException("Value must be in [0, order-1].");
        }
    }
}
