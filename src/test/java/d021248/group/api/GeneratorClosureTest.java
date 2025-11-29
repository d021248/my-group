package d021248.group.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import org.junit.jupiter.api.Test;

import d021248.group.Generator;
import d021248.group.Group;

class GeneratorClosureTest {
    static class ModN implements Element {
        private final int n; // modulus
        private final int v; // value in [0,n)

        ModN(int n, int v) {
            this.n = n;
            this.v = ((v % n) + n) % n;
        }

        int value() {
            return v;
        }

        int modulus() {
            return n;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof ModN m && m.n == n && m.v == v;
        }

        @Override
        public int hashCode() {
            return 31 * n + v;
        }

        @Override
        public String toString() {
            return v + " (mod " + n + ")";
        }
    }

    static class ModNGroup implements Group<ModN> {
        private final int n;

        ModNGroup(int n) {
            this.n = n;
        }

        @Override
        public java.util.Set<ModN> elements() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Operation<ModN> operation() {
            return (a, b) -> new ModN(n, a.value() + b.value());
        }

        @Override
        public ModN identity() {
            return new ModN(n, 0);
        }

        @Override
        public ModN inverse(ModN element) {
            return new ModN(n, n - element.value());
        }
    }

    @Test
    void closureOfSingleGeneratorMod3() {
        ModNGroup group = new ModNGroup(3);
        Set<ModN> gens = Set.of(new ModN(3, 1));
        Set<ModN> closure = Generator.generate(group, gens);
        assertEquals(Set.of(new ModN(3, 0), new ModN(3, 1), new ModN(3, 2)), closure);
    }

    @Test
    void emptyGeneratorSetProducesIdentityOnly() {
        ModNGroup group = new ModNGroup(5);
        Set<ModN> closure = Generator.generate(group, java.util.Set.of());
        assertEquals(Set.of(new ModN(5, 0)), closure);
    }

    @Test
    void twoGeneratorsMod4ProduceWholeGroup() {
        ModNGroup group = new ModNGroup(4);
        Set<ModN> closure = Generator.generate(group, Set.of(new ModN(4, 1), new ModN(4, 2)));
        assertEquals(Set.of(new ModN(4, 0), new ModN(4, 1), new ModN(4, 2), new ModN(4, 3)), closure);
    }
}
