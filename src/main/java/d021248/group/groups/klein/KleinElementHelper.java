package d021248.group.groups.klein;

public class KleinElementHelper {
    private KleinElementHelper() {
    }

    public static boolean isValid(KleinElement e) {
        return e.value().equals("e") || e.value().equals("a") || e.value().equals("b")
                || e.value().equals("ab");
    }

    public static KleinElement inverse(KleinElement e) {
        // All elements are their own inverse in V_4
        return e;
    }
}
