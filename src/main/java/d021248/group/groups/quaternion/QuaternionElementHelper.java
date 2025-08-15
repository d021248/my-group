package d021248.group.groups.quaternion;

public class QuaternionElementHelper {
    private QuaternionElementHelper() {
    }

    public static boolean isValid(QuaternionElement e) {
        switch (e.value()) {
            case "1", "-1", "i", "-i", "j", "-j", "k", "-k":
                return true;
            default:
                return false;
        }
    }

    public static QuaternionElement inverse(QuaternionElement e) {
        switch (e.value()) {
            case "1":
                return e;
            case "-1":
                return e;
            case "i":
                return new QuaternionElement("-i");
            case "-i":
                return new QuaternionElement("i");
            case "j":
                return new QuaternionElement("-j");
            case "-j":
                return new QuaternionElement("j");
            case "k":
                return new QuaternionElement("-k");
            case "-k":
                return new QuaternionElement("k");
            default:
                throw new IllegalArgumentException("Invalid QuaternionElement: " + e.value());
        }
    }
}
