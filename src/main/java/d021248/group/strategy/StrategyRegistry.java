package d021248.group.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Central registry mapping concrete finite group classes to generation
 * strategies.
 * <p>
 * <strong>Thread Safety:</strong> This registry is mutable and NOT thread-safe.
 * Registration methods ({@link #register}, {@link #unregister},
 * {@link #replaceAll})
 * are intended for application initialization or testing only. Concurrent
 * modifications during lookup operations may result in undefined behavior.
 * </p>
 * <p>
 * <strong>Default Registrations:</strong> The following strategies are
 * registered
 * at class initialization:
 * </p>
 * <ul>
 * <li>{@link CyclicGroup} → {@link CyclicGenerationStrategy}</li>
 * <li>{@link SymmetricGroup} → {@link SymmetricGenerationStrategy}</li>
 * <li>{@link DihedralGroup} → {@link DihedralGenerationStrategy}</li>
 * </ul>
 */
public final class StrategyRegistry {
    private static final Map<Class<?>, GenerationStrategy<? extends Element>> REGISTRY = new HashMap<>();
    static {
        REGISTRY.put(CyclicGroup.class, CyclicGenerationStrategy.get());
        REGISTRY.put(SymmetricGroup.class, SymmetricGenerationStrategy.get());
        REGISTRY.put(DihedralGroup.class, DihedralGenerationStrategy.get());
    }

    private StrategyRegistry() {
    }

    public static <E extends Element> void register(Class<? extends Group<E>> clazz,
            GenerationStrategy<E> strategy) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(strategy);
        REGISTRY.put(clazz, strategy);
    }

    public static void unregister(Class<?> clazz) {
        REGISTRY.remove(clazz);
    }

    public static void replaceAll(Map<Class<?>, GenerationStrategy<? extends Element>> newMap) {
        REGISTRY.clear();
        REGISTRY.putAll(newMap);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Element> GenerationStrategy<E> lookup(Class<?> clazz) {
        return (GenerationStrategy<E>) REGISTRY.get(clazz);
    }

    // Snapshot and builder removed for simplification.
}
