package d021248.group.quaternion;

import d021248.group.api.Operation;

public class QuaternionOperation implements Operation<QuaternionElement> {
    // Cayley table for Q_8 (values 0..7)
    private static final int[][] TABLE = {
            { 0, 1, 2, 3, 4, 5, 6, 7 },
            { 1, 0, 3, 2, 5, 4, 7, 6 },
            { 2, 3, 1, 0, 6, 7, 5, 4 },
            { 3, 2, 0, 1, 7, 6, 4, 5 },
            { 4, 5, 7, 6, 1, 0, 2, 3 },
            { 5, 4, 6, 7, 0, 1, 3, 2 },
            { 6, 7, 4, 5, 3, 2, 1, 0 },
            { 7, 6, 5, 4, 2, 3, 0, 1 }
    };

    @Override
    public QuaternionElement calculate(QuaternionElement a, QuaternionElement b) {
        return new QuaternionElement(TABLE[a.value()][b.value()]);
    }
}
