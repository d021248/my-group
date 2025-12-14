package d021248.group.util;

/**
 * Utility methods for thread operations.
 */
public final class ThreadUtil {

    private ThreadUtil() {
        // Utility class
    }

    /**
     * Sleep for the specified number of milliseconds.
     * Handles InterruptedException by restoring the interrupt flag.
     *
     * @param ms milliseconds to sleep
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
