package d021248.group.util;

/**
 * Global constants used throughout the library.
 */
public final class Constants {

    private Constants() {
        // Utility class
    }

    // Display constants
    public static final String SEPARATOR_60 = "=".repeat(60);
    public static final int DEFAULT_SLEEP_MS = 300;

    // Algorithm constants
    public static final int MAX_SUBGROUP_ENUMERATION_SIZE = 20;
    public static final int MAX_SYMMETRIC_GROUP_DEGREE = 9;

    // Null check messages
    public static final String NULL_PARENT = "parent group must not be null";
    public static final String NULL_ELEMENT = "element must not be null";
    public static final String NULL_GENERATORS = "generators must not be null";
    public static final String NULL_SUBGROUP = "subgroup must not be null";
    public static final String NULL_GROUP = "group must not be null";
}
