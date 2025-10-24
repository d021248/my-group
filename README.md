# my-group

Educational miniature algebra library focusing on finite groups. Provides:

* Minimal interfaces: `Element`, `Operation<E>`, `Group<E>`, `FiniteGroup<E>`
* Concrete implementations: cyclic groups (Z_n), dihedral groups (D_n), symmetric groups (S_n)
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

## Design Notes

* Immutability: All elements are records; operations produce new instances.
* No external logging dependency (removed for simplicity).
* `FiniteGroup#order()` provided as a default method (size of element set).
* Symmetric group generation guarded (n ≤ 9) to avoid factorial blow-up.
* `Permutation` optimizes composition by avoiding unnecessary array copies.

## Limitations / Future Ideas

| Idea | Description | Effort |
|------|-------------|--------|
| Cycle notation | Represent permutations as disjoint cycles | Medium |
| Subgroup generation | Build subgroup from generator set | Medium |
| Direct products | Cartesian product group implementation | Low |
| Conjugacy classes | Compute classes & class equation | Medium |
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
