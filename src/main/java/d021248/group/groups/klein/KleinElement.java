package d021248.group.groups.klein;

// V_4 = {e, a, b, ab}, all elements are their own inverse
public record KleinElement(String value) {
    public KleinElement inverse() {
        return KleinElementHelper.inverse(this);
    }
}
