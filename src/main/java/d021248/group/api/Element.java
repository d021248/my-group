package d021248.group.api;

/**
 * Minimal marker interface for group elements.
 * <p>
 * Implementations should be immutable and obey the group law relative to the
 * {@link Operation} they participate in. This interface has no methods - all
 * group operations (including inverse) are defined on the
 * {@link d021248.group.Group}
 * interface to ensure proper type safety.
 * </p>
 */
public interface Element {
}
