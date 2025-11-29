package d021248.group;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import d021248.group.api.Element;
import d021248.group.strategy.GenerationStrategy;
import d021248.group.strategy.StrategyRegistry;
import d021248.group.strategy.ValidatingGenerationStrategy;

/** Instance-oriented helper for a specific group. */
public final class GroupHelper<E extends Element> {
    private final Group<E> group;

    public GroupHelper(Group<E> group) {
        this.group = Objects.requireNonNull(group);
    }

    /** Full closure via {@link Generator}. */
    public Set<E> closure(Set<E> generators) {
        return Generator.generate(group, generators);
    }

    /** True if closure of generators equals group elements (finite groups). */
    public boolean isGeneratingSet(Set<E> generators) {
        return closure(generators).equals(group.elements());
    }

    /** Single element generation check. */
    public boolean isGenerator(E e) {
        return isGeneratingSet(Set.of(e));
    }

    /**
     * Determine a strategy-based generating set if group is a known concrete finite
     * group.
     */
    public Set<E> strategyGenerators() {
        if (!(group instanceof Group<?>)) {
            throw new UnsupportedOperationException("Strategy generation requires a finite group");
        }
        GenerationStrategy<E> strat = StrategyRegistry.lookup(group.getClass());
        if (strat == null) {
            throw new IllegalArgumentException(
                    "No generation strategy registered for type: " + group.getClass().getName());
        }
        Set<E> gens = strat.generators((Group<E>) group);
        if (gens == null || gens.isEmpty()) {
            throw new IllegalStateException("Strategy " + strat.name() + " returned empty generator set for "
                    + group.getClass().getSimpleName());
        }
        if (strat instanceof ValidatingGenerationStrategy<E> v && !v.validates((Group<E>) group, gens)) {
            throw new IllegalStateException(
                    "Strategy " + strat.name() + " failed validation for " + group.getClass().getSimpleName());
        }
        return gens;
    }

    /**
     * Bounded partial closure (hull) expansion by right-multiplication, for
     * exploratory limits.
     */
    public Set<E> partialClosure(Set<E> start, int maxSize) {
        Set<E> hull = new HashSet<>(start);
        Set<E> frontier = new HashSet<>(start);
        while (!shouldStop(frontier, hull, maxSize)) {
            frontier = expandFrontier(hull, frontier, maxSize);
        }
        return hull;
    }

    private boolean shouldStop(Set<E> frontier, Set<E> hull, int maxSize) {
        return frontier.isEmpty() || hull.size() >= maxSize;
    }

    private Set<E> expandFrontier(Set<E> hull, Set<E> frontier, int maxSize) {
        Set<E> next = new HashSet<>();
        for (E a : hull) {
            for (E b : frontier) {
                E prod = group.operate(a, b);
                if (hull.add(prod)) {
                    if (hull.size() >= maxSize)
                        return next; // early exit retains current hull
                    next.add(prod);
                }
            }
        }
        return next;
    }

    /**
     * Simple console table (small groups only).
     * 
     * @deprecated Use {@link GroupTableFormatter} instead for better control and
     *             formatting options.
     *             This method will be removed in a future version.
     */
    @Deprecated(since = "1.0", forRemoval = true)
    public void printTable() {
        Set<E> elements = group.elements();
        System.out.println("Group Table:");
        System.out.print("*\t");
        for (E e : elements)
            System.out.print(e + "\t");
        System.out.println();
        for (E a : elements) {
            System.out.print(a + "\t");
            for (E b : elements)
                System.out.print(group.operate(a, b) + "\t");
            System.out.println();
        }
    }
}
