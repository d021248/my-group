package d021248.group.groups.klein;

public class KleinElementHelper {
    private KleinElementHelper() {
    }

    public static boolean isValid(KleinElement e) {
        return e.getValue().equals("e") || e.getValue().equals("a") || e.getValue().equals("b")
                || e.getValue().equals("ab");
    }

    public static KleinElement inverse(KleinElement e) {
        return e.inverse();
    }
}
