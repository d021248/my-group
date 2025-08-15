package d021248.group.groups.quaternion;

import java.util.Set;

public class QuaternionGroupHelper {
    private QuaternionGroupHelper() {
    }

    public static Set<QuaternionElement> generate() {
        return Set.of(
                new QuaternionElement("1"),
                new QuaternionElement("-1"),
                new QuaternionElement("i"),
                new QuaternionElement("-i"),
                new QuaternionElement("j"),
                new QuaternionElement("-j"),
                new QuaternionElement("k"),
                new QuaternionElement("-k"));
    }
}
