# my-group

Educational miniature algebra library focusing on finite groups. Provides:

* Minimal interfaces: `Element`, `Operation<E>`, `Group<E>`
* Concrete implementations: cyclic groups (Z_n), dihedral groups (D_n), symmetric groups (S_n)
* **Direct products**: construct G₁ × G₂ from any two finite groups
* **Subgroup generation**: generate subgroups from generators, enumerate all subgroups, compute normalizers/centralizers
* Utilities: group verifier (axioms), operation (Cayley) table formatter (plain text / Markdown / LaTeX)
* Property-based tests (jqwik) asserting axioms for randomly chosen cyclic groups

## Why
Designed as a teaching aid: show how abstract algebra definitions map to small, immutable Java types using modern language features (records, pattern matching, streams) while keeping API friction low.

## Getting Started

```java
import d021248.group.cyclic.CyclicGroup;
import d021248.group.cyclic.CyclicElement;

var g = new CyclicGroup(7); // Z_7 under addition mod 7
var a = new CyclicElement(3, 7);
var b = new CyclicElement(6, 7);
var sum = g.operate(a, b); // 3 + 6 = 9 ≡ 2 (mod 7)
System.out.println(sum); // 2 (mod 7)
System.out.println(a.inverse()); // 4 (mod 7)
```

Generate / visualize an operation (Cayley) table:

```java
import d021248.group.GroupTableFormatter;
import d021248.group.FiniteGroup;

FiniteGroup<?> fg = g; // any finite group
var cfg = GroupTableFormatter.forGroup(fg).build();
System.out.println(GroupTableFormatter.toMarkdown(cfg)); // Markdown
System.out.println(GroupTableFormatter.toPlainText(cfg)); // tab-separated
System.out.println(GroupTableFormatter.toLatex(cfg)); // LaTeX tabular
```

Custom ordering & identity highlighting control:

```java
var cfgCustom = GroupTableFormatter.forGroup(fg)
	.ordering((e1, e2) -> e1.toString().compareTo(e2.toString()))
	.highlightIdentity(false)
	.build();
System.out.println(GroupTableFormatter.toMarkdown(cfgCustom));
```

Verify axioms programmatically:

```java
import d021248.group.util.GroupVerifier;
var result = GroupVerifier.verify(g);
System.out.println(result.summary());
```

Build direct products:

```java
import d021248.group.GroupFactory;
import d021248.group.product.DirectProduct;
import d021248.group.product.ProductElement;

var z2 = GroupFactory.cyclic(2);
var z3 = GroupFactory.cyclic(3);
var z2xz3 = GroupFactory.directProduct(z2, z3);
System.out.println("Z_2 × Z_3 order: " + z2xz3.order()); // 6

// Klein four-group V_4 = Z_2 × Z_2
var v4 = GroupFactory.directProduct(GroupFactory.cyclic(2), GroupFactory.cyclic(2));
```

Generate and analyze subgroups:

```java
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.subgroup.Subgroup;

var z12 = GroupFactory.cyclic(12);
// Generate subgroup from generators
Subgroup<CyclicElement> h = SubgroupGenerator.generate(z12, Set.of(new CyclicElement(3, 12)));
System.out.println("Order: " + h.order()); // 4
System.out.println("Index: " + h.index()); // 3 (Lagrange's theorem)

// Find all cyclic subgroups
List<Subgroup<CyclicElement>> cyclicSubs = SubgroupGenerator.cyclicSubgroups(z12);

// Check normality
boolean normal = SubgroupGenerator.isNormal(z12, h); // true (abelian group)

// Compute normalizer and centralizer
Subgroup<CyclicElement> normalizer = SubgroupGenerator.normalizer(z12, h);
Subgroup<CyclicElement> centralizer = SubgroupGenerator.centralizer(z12, h);
```

## Design Notes

* Immutability: All elements are records; operations produce new instances.
* No external logging dependency (removed for simplicity).
* `FiniteGroup#order()` provided as a default method (size of element set).
* Symmetric group generation guarded (n ≤ 9) to avoid factorial blow-up.
* `Permutation` optimizes composition by avoiding unnecessary array copies.
* Direct products work with any two finite groups (type-safe via generics).
* Subgroup verification ensures mathematical validity at construction time.

## Features

| Category | Feature | Status |
|----------|---------|--------|
| Core Groups | Cyclic (Z_n) | ✅ Complete |
| | Dihedral (D_n) | ✅ Complete |
| | Symmetric (S_n, n≤9) | ✅ Complete |
| Constructions | Direct products | ✅ Complete |
| | Subgroup generation | ✅ Complete |
| Analysis | Axiom verification | ✅ Complete |
| | Cayley tables (text/MD/LaTeX) | ✅ Complete |
| | Cayley images (visual) | ✅ Complete |
| Subgroup Tools | Generator closure | ✅ Complete |
| | All subgroups enumeration | ✅ Complete |
| | Cyclic subgroups | ✅ Complete |
| | Normality testing | ✅ Complete |
| | Normalizer/Centralizer | ✅ Complete |
| | Subgroup index | ✅ Complete |

## Limitations / Future Ideas

| Idea | Description | Effort |
|------|-------------|--------|
| Cycle notation | Represent permutations as disjoint cycles | ✅ Done |
| Subgroup generation | Build subgroup from generator set | ✅ Done |
| Direct products | Cartesian product group implementation | ✅ Done |
| Quotient groups | G/H for normal subgroups | Medium |
| Homomorphisms | Group morphisms and kernels | Medium |
| Conjugacy classes | Compute classes & class equation | Medium |
| Group actions | Orbits and stabilizers | Medium |
| Web demo | HTTP endpoints exposing Cayley tables | Medium |
| CLI REPL | Interactive exploration and verification | Low |

## Running Tests

Property-based and unit tests (requires Maven):

```bash
mvn test
```

## License

Educational / personal use. Adapt freely.

## References

* Dummit & Foote: Abstract Algebra (for deeper theory)
* Gallian: Contemporary Abstract Algebra
* Wikipedia: Group theory overview
