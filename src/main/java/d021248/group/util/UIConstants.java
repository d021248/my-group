package d021248.group.util;

/**
 * UI-related string and configuration constants used across visualization
 * and demonstration components.
 */
public final class UIConstants {

    // Font constants
    public static final String FONT_SANS_SERIF = "SansSerif";

    // User prompts
    public static final String CHOICE_PROMPT = "\nChoice: ";
    public static final String BACK_OPTION = "B. Back";
    public static final String QUIT_OPTION = "Q. Quit";

    // Visualization messages
    public static final String CLICK_ELEMENTS_MSG = "Click elements to highlight their generator paths";
    public static final String HOVER_FOR_INFO_MSG = "Hover for element info";

    // Group type labels
    public static final String CYCLIC_GROUP = "Cyclic (Z_n)";
    public static final String DIHEDRAL_GROUP = "Dihedral (D_n)";
    public static final String SYMMETRIC_GROUP = "Symmetric (S_n)";
    public static final String ALTERNATING_GROUP = "Alternating (A_n)";
    public static final String DIRECT_PRODUCT_GROUP = "Direct Product";
    public static final String QUOTIENT_GROUP = "Quotient Group";

    // Demo output formatting
    public static final String INDENT_ELEMENTS = "  Elements: ";
    public static final String INDENT_SIGN = "  sign(";

    // Private constructor prevents instantiation
    private UIConstants() {
        throw new AssertionError("Utility class should not be instantiated");
    }
}
