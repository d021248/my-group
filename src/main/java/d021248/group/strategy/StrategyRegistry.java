package d021248.group.strategy;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import d021248.group.FiniteGroup;
import d021248.group.api.Element;

/**
 * Central registry mapping concrete finite group classes to generation
 * strategies.
 */
public final class StrategyRegistry {
    private static final Map<Class<?>, GenerationStrategy<?>> REGISTRY = new HashMap<>();
    static {
        REGISTRY.put(d021248.group.cyclic.CyclicGroup.class, CyclicGenerationStrategy.get());
        REGISTRY.put(d021248.group.symmetric.SymmetricGroup.class, SymmetricGenerationStrategy.get());
        REGISTRY.put(d021248.group.dihedral.DihedralGroup.class, DihedralGenerationStrategy.get());
    }

    private StrategyRegistry() {
    }

    public static <E extends Element> void register(Class<? extends FiniteGroup<E>> clazz,
            GenerationStrategy<E> strategy) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(strategy);
        REGISTRY.put(clazz, strategy);
    }

    public static void unregister(Class<?> clazz) {
        REGISTRY.remove(clazz);
    }

    public static void replaceAll(Map<Class<?>, GenerationStrategy<?>> newMap) {
        REGISTRY.clear();
        REGISTRY.putAll(newMap);
    }

    @SuppressWarnings("unchecked")
    public static <E extends Element> GenerationStrategy<E> lookup(Class<?> clazz) {
        return (GenerationStrategy<E>) REGISTRY.get(clazz);
    }

    public static Map<Class<?>, GenerationStrategy<?>> snapshot() {
        return Collections.unmodifiableMap(REGISTRY);
    }

    /** Builder for assembling a custom registry map before replacing. */
    public static final class Builder {
        private final Map<Class<?>, GenerationStrategy<?>> local = new HashMap<>();

        public <E extends Element> Builder register(Class<? extends FiniteGroup<E>> clazz,
                GenerationStrategy<E> strategy) {
            local.put(clazz, strategy);
            return this;
        }

        public Map<Class<?>, GenerationStrategy<?>> build() {
            return Collections.unmodifiableMap(new HashMap<>(local));
        }

        public void install() {
            StrategyRegistry.replaceAll(local);
        }
    }
}
