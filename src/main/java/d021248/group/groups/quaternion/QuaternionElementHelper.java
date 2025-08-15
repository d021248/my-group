package d021248.group.groups.quaternion;

public class QuaternionElementHelper {
    private QuaternionElementHelper() {
    }

    public static boolean isValid(QuaternionElement e) {
        switch (e.getValue()) {
            case "1", "-1", "i", "-i", "j", "-j", "k", "-k":
                return true;
            default:
                return false;
        }
    }

    public static QuaternionElement inverse(QuaternionElement e) {
        return e.inverse();
    }
}
