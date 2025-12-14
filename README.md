# my-group

Educational miniature algebra library for exploring finite group theory through code and interactive visualizations.

## ✨ What You Can Do

### 🖥️ Interactive REPL (NEW!)
Explore group theory through an interactive Computer Algebra System:
```bash
./repl.sh
```

Example session:
```
group> g = Z(6)
Z₍6₎ (order 6)

group> isAbelian(g)
true

group> h = D(4)
D₍4₎ (order 8)

group> subgroups(h)
[ ... list of all subgroups ... ]

group> center(h)
(center subgroup)
```

Features:
- **Create groups**: Z(n), D(n), S(n), A(n), Product(g1, g2)
- **Analyze properties**: order, isAbelian, isCyclic, center, subgroups
- **Compute**: inverse, power, element operations
- **Interactive shell**: variables, help, history

See [REPL_GUIDE.md](REPL_GUIDE.md) for complete documentation.

### 🎨 Interactive Visualizations
Explore groups visually with three interactive tools:
- **Cayley Tables**: Color-coded operation tables with hover tooltips
- **Subgroup Lattices**: Hasse diagrams showing containment hierarchies
- **Cayley Graphs**: Generator-based graph representations

```bash
./viz.sh S 4      # Visualize S_4
./viz.sh D 5      # Visualize D_5
./viz.sh demo     # Interactive menu
./viz.sh gui      # GUI launcher
```

Or use the unified demo launcher directly:
```bash
mvn compile
java -cp target/classes d021248.group.GroupDemo       # Interactive menu
java -cp target/classes d021248.group.GroupDemo --gui # GUI launcher
java -cp target/classes d021248.group.GroupDemo viz S 4  # Quick viz
```

Or use examples programmatically:
```java
import d021248.group.viz.VisualizationExamples;

VisualizationExamples.cyclicGroupExample();  // Z_8
VisualizationExamples.comparisonExample();    // Z_6 vs D_3
```

### 🔬 Learn Group Theory Concepts
Interactive demonstrations of:
- Homomorphisms & kernels (First Isomorphism Theorem)
- Conjugacy classes & class equations
- Group actions & Orbit-Stabilizer Theorem
- Cayley's Theorem (every group is a permutation group)

### 🧮 Compute with Groups
Work with cyclic, dihedral, symmetric, and alternating groups. Build direct products, generate subgroups, compute homomorphisms, and more.

## Why
Designed as a teaching aid: see how abstract algebra definitions map to clean Java code using modern language features (records, pattern matching, streams) while keeping friction low.

## Quick Start

### Build and Run
```bash
mvn compile
java -cp target/classes d021248.group.GroupDemo    # Interactive menu
./viz.sh S 4                                        # Quick visualization
```

### Basic Group Operations
```java
import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;

// Create a cyclic group Z_7
var z7 = GroupFactory.cyclic(7);
var a = new CyclicElement(3, 7);
var b = new CyclicElement(6, 7);
var sum = z7.operate(a, b);  // 3 + 6 ≡ 2 (mod 7)

System.out.println(sum);          // 2 (mod 7)
System.out.println(a.inverse());  // 4 (mod 7)
System.out.println(a.order(z7));  // 7
```

### Generate and Visualize Groups
```java
var s4 = GroupFactory.symmetric(4);           // S_4 (order 24)
var d5 = GroupFactory.dihedral(5);            // D_5 (order 10)
var v4 = GroupFactory.directProduct(          // Klein four-group
    GroupFactory.cyclic(2), 
    GroupFactory.cyclic(2)
);

// Launch interactive visualizations
CayleyTableViewer.show(s4, "S_4");
SubgroupLatticeViewer.show(s4, "S_4 Subgroups");
CayleyGraphViewer.show(s4, "S_4 Generators");
```

### Explore Subgroups
```java
var z12 = GroupFactory.cyclic(12);
var g = new CyclicElement(3, 12);

// Generate subgroup from element
var h = SubgroupGenerator.generate(z12, Set.of(g));
System.out.println("Order: " + h.order());    // 4
System.out.println("Index: " + h.index());    // 3 (Lagrange)

// Find all subgroups
var allSubs = SubgroupGenerator.allSubgroups(z12);
var center = SubgroupGenerator.center(z12);
```

### Study Homomorphisms
```java
var s3 = GroupFactory.symmetric(3);
var z2 = GroupFactory.cyclic(2);

// Sign homomorphism
var sign = new Homomorphism<>(s3, z2,
    p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

var kernel = HomomorphismAnalyzer.kernel(sign);  // A_3
var image = HomomorphismAnalyzer.image(sign);    // Z_2

// First Isomorphism Theorem: S_3/A_3 ≅ Z_2
```

## What's Included

### Group Types
- **Cyclic** (Z_n): Addition mod n
- **Dihedral** (D_n): Symmetries of regular n-gons
- **Symmetric** (S_n): All permutations
- **Alternating** (A_n): Even permutations
- **Direct Products**: G₁ × G₂ from any two groups

### Advanced Features
- **Subgroups**: Generate, enumerate, test normality
- **Homomorphisms**: Kernels, images, First Isomorphism Theorem
- **Conjugacy**: Classes, centralizers, class equations
- **Group Actions**: Orbits, stabilizers, Burnside's Lemma
- **Cayley's Theorem**: Convert any group to permutations

### Utilities
- Operation table formatter (Markdown/LaTeX/plain text)
- Group axiom verifier
- Property-based testing (jqwik)

📖 **See [FEATURES.md](FEATURES.md) for complete documentation**

## Project Structure
```
src/main/java/d021248/group/
├── Group.java, Element.java          # Core interfaces
├── GroupFactory.java                 # Convenience factory
├── GroupDemo.java                    # 🎯 Unified demo launcher
├── cyclic/                           # Z_n implementation
├── dihedral/                         # D_n implementation  
├── symmetric/                        # S_n, A_n, permutations
├── product/                          # Direct products
├── subgroup/                         # Subgroup generation
├── homomorphism/                     # Homomorphisms
├── conjugacy/                        # Conjugacy classes
├── action/                           # Group actions
├── viz/                              # 🎨 Visualizations
│   ├── CayleyTableViewer.java        # Interactive Cayley table
│   ├── SubgroupLatticeViewer.java    # Subgroup lattice viewer
│   ├── CayleyGraphViewer.java        # Cayley graph viewer
│   └── examples/                     # 📚 Usage examples
│       ├── VisualizationExamples.java  # Example methods
│       └── VizLauncher.java            # GUI launcher
└── demo/archive/                     # Archived old demos
```

## Requirements
- Java 21+
- Maven 3.6+

## Tests
```bash
mvn test  # Run 168 tests (all passing)
```

Includes property-based tests verifying group axioms for random instances.

## References
- Dummit & Foote, *Abstract Algebra* (3rd ed.)
- Judson, *Abstract Algebra: Theory and Applications*


// Find maximal subgroups (proper subgroups that are not contained in any other proper subgroup)
List<Subgroup<CyclicElement>> maximal = SubgroupGenerator.maximalSubgroups(z12);
System.out.println("Maximal subgroups: " + maximal.size()); // 2 for Z_12

// Compute Frattini subgroup Φ(G) = intersection of all maximal subgroups
Subgroup<CyclicElement> frattini = SubgroupGenerator.frattiniSubgroup(z12);
System.out.println("Φ(Z_12) order: " + frattini.order()); // 1 (trivial)

// Compute center Z(G) = elements commuting with all elements
Subgroup<CyclicElement> center = SubgroupGenerator.center(z12);
System.out.println("Z(Z_12) order: " + center.order()); // 12 (abelian group)

// Compute commutator subgroup [G,G] = subgroup generated by commutators
SymmetricGroup s3 = GroupFactory.symmetric(3);
Subgroup<Permutation> commutator = SubgroupGenerator.commutatorSubgroup(s3);
System.out.println("[S_3,S_3] order: " + commutator.order()); // 3 (equals A_3)
```

Compute conjugacy classes and analyze group structure:

```java
import d021248.group.conjugacy.ConjugacyAnalyzer;
import d021248.group.conjugacy.ConjugacyClass;

SymmetricGroup s3 = GroupFactory.symmetric(3);

// Partition group into conjugacy classes
List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s3);
System.out.println("Number of classes: " + classes.size()); // 3

// Get conjugacy class of specific element
Permutation g = new Permutation(new int[]{2, 1, 3}); // (1 2)
Set<Permutation> cl = ConjugacyAnalyzer.conjugacyClass(s3, g);
System.out.println("Class size: " + cl.size()); // 3 (all transpositions)

// Class equation: |G| = sum of |cl(g)|
Map<Integer, Long> equation = ConjugacyAnalyzer.classEquation(s3);
equation.forEach((size, count) -> 
    System.out.println(count + " class(es) of size " + size));
// Output: 1 class(es) of size 1
//         1 class(es) of size 2
//         1 class(es) of size 3

// Check if two elements are conjugate
Permutation h1 = new Permutation(new int[]{2, 1, 3}); // (1 2)
Permutation h2 = new Permutation(new int[]{1, 3, 2}); // (2 3)
boolean conjugate = ConjugacyAnalyzer.areConjugate(s3, h1, h2); // true

// Compute centralizer C_G(g) using orbit-stabilizer: |cl(g)| × |C_G(g)| = |G|
Subgroup<Permutation> centralizer = ConjugacyAnalyzer.elementCentralizer(s3, g);
System.out.println("|cl(g)| × |C_G(g)| = " + cl.size() * centralizer.order()); // 6
```

Work with group homomorphisms and the First Isomorphism Theorem:

```java
import d021248.group.homomorphism.Homomorphism;
import d021248.group.homomorphism.HomomorphismAnalyzer;

SymmetricGroup s3 = GroupFactory.symmetric(3);
CyclicGroup z2 = GroupFactory.cyclic(2);

// Sign homomorphism: S_3 → Z_2
Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
    s3, z2,
    p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2));

// Verify it's a valid homomorphism
boolean valid = HomomorphismAnalyzer.isHomomorphism(sign); // true

// Analyze properties
boolean injective = HomomorphismAnalyzer.isInjective(sign); // false
boolean surjective = HomomorphismAnalyzer.isSurjective(sign); // true
boolean isomorphism = HomomorphismAnalyzer.isIsomorphism(sign); // false

// Compute kernel and image
Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);
System.out.println("ker(sign) = A_3, order: " + kernel.order()); // 3

Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(sign);
System.out.println("im(sign) = Z_2, order: " + image.order()); // 2

// First Isomorphism Theorem: S_3/A_3 ≅ Z_2
int quotientOrder = HomomorphismAnalyzer.firstIsomorphismTheorem(sign);
System.out.println("|S_3/A_3| = |Z_2| = " + quotientOrder); // 2

// Kernel is always normal
boolean kernelNormal = SubgroupGenerator.isNormal(s3, kernel); // true

// Compose homomorphisms
CyclicGroup z12 = GroupFactory.cyclic(12);
CyclicGroup z4 = GroupFactory.cyclic(4);

Homomorphism<CyclicElement, CyclicElement> phi = new Homomorphism<>(
    z12, z4,
    e -> new CyclicElement(e.value() % 4, 4));

Homomorphism<CyclicElement, CyclicElement> psi = new Homomorphism<>(
    z4, z2,
    e -> new CyclicElement(e.value() % 2, 2));

Homomorphism<CyclicElement, CyclicElement> composed = HomomorphismAnalyzer.compose(phi, psi);
// (ψ ∘ φ): Z_12 → Z_2
```

Explore group actions, orbits, and stabilizers:

```java
import d021248.group.action.Action;
import d021248.group.action.ActionAnalyzer;
import d021248.group.action.Orbit;

SymmetricGroup s3 = GroupFactory.symmetric(3);

// Conjugation action: G acts on itself by g · h = ghg⁻¹
Action<Permutation, Permutation> conjugation = new Action<>(
    s3,
    s3.elements(),
    (g, h) -> {
        Permutation gInv = s3.inverse(g);
        return s3.operate(s3.operate(g, h), gInv);
    });

// Verify it's a valid action
boolean valid = ActionAnalyzer.isAction(conjugation); // true

// Partition into orbits (= conjugacy classes for conjugation)
List<Orbit<Permutation>> orbits = ActionAnalyzer.orbits(conjugation);
System.out.println("Number of orbits: " + orbits.size()); // 3

// Compute orbit and stabilizer for specific element
Permutation x = new Permutation(new int[]{2, 1, 3}); // (1 2)
Orbit<Permutation> orb = ActionAnalyzer.orbit(conjugation, x);
Subgroup<Permutation> stab = ActionAnalyzer.stabilizer(conjugation, x);

// Orbit-Stabilizer Theorem: |orb(x)| × |Stab(x)| = |G|
System.out.println("|orb| × |Stab| = " + orb.size() * stab.order()); // 6
boolean theoremHolds = ActionAnalyzer.verifyOrbitStabilizer(conjugation, x); // true

// Permutation action on integers
Set<Integer> integers = Set.of(1, 2, 3);
Action<Permutation, Integer> permAction = new Action<>(
    s3,
    integers,
    (p, i) -> p.mapping()[i - 1]);

boolean transitive = ActionAnalyzer.isTransitive(permAction); // true
boolean free = ActionAnalyzer.isFree(permAction); // false (stabilizers non-trivial)

// Fixed points
Permutation trans = new Permutation(new int[]{2, 1, 3}); // (1 2)
Set<Integer> fixed = ActionAnalyzer.fixedPoints(permAction, trans);
System.out.println("Fixed by (1 2): " + fixed); // {3}

// Burnside's Lemma: count orbits using average fixed points
int numOrbits = ActionAnalyzer.burnsideLemma(conjugation);
System.out.println("Number of orbits: " + numOrbits); // 3
```

#### Automorphisms

```java
import d021248.group.automorphism.*;

// Inner automorphism via conjugation
SymmetricGroup s3 = GroupFactory.symmetric(3);
Permutation g = new Permutation(new int[]{2, 1, 3}); // (1 2)
Automorphism<Permutation> inner = AutomorphismAnalyzer.innerAutomorphism(s3, g);

Permutation h = new Permutation(new int[]{2, 3, 1}); // (1 2 3)
Permutation conjugate = inner.apply(h); // g·h·g⁻¹ = (1 3 2)

boolean isAuto = AutomorphismAnalyzer.isAutomorphism(inner); // true
boolean isInner = AutomorphismAnalyzer.isInner(inner); // true

// All inner automorphisms
Set<Automorphism<Permutation>> innerAutos = AutomorphismAnalyzer.innerAutomorphisms(s3);
int distinctCount = AutomorphismAnalyzer.countDistinctInnerAutomorphisms(s3);
System.out.println("|Inn(S_3)| = " + distinctCount); // 6

// Inn(G) ≅ G/Z(G)
System.out.println("|S_3|/|Z(S_3)| = 6/1 = " + distinctCount);

// Composition
Permutation g2 = new Permutation(new int[]{2, 3, 1}); // (1 2 3)
Automorphism<Permutation> inner2 = AutomorphismAnalyzer.innerAutomorphism(s3, g2);
Automorphism<Permutation> composed = AutomorphismAnalyzer.compose(inner, inner2);

// Inverse
Automorphism<Permutation> inv = AutomorphismAnalyzer.inverse(inner);
Automorphism<Permutation> identity = AutomorphismAnalyzer.compose(inner, inv);

// Outer automorphisms (for abelian groups, all inner autos are identity)
CyclicGroup z4 = GroupFactory.cyclic(4);
GroupHomomorphism<CyclicElement, CyclicElement> phi = e -> 
    new CyclicElement((e.value() * 3) % 4, 4); // φ(n) = 3n mod 4

Automorphism<CyclicElement> outer = new Automorphism<>(z4, phi);
boolean isOuter = !AutomorphismAnalyzer.isInner(outer); // true (not identity)
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
| | Maximal subgroups | ✅ Complete |
| | Frattini subgroup | ✅ Complete |
| | Center Z(G) | ✅ Complete |
| | Commutator subgroup [G,G] | ✅ Complete |
| | Conjugacy classes | ✅ Complete |
| Homomorphisms | Group homomorphisms | ✅ Complete |
| | Kernel and image | ✅ Complete |
| | Injectivity/surjectivity | ✅ Complete |
| | First Isomorphism Theorem | ✅ Complete |
| | Composition | ✅ Complete |
| Group Actions | Actions on sets | ✅ Complete |
| | Orbits and stabilizers | ✅ Complete |
| | Orbit-Stabilizer Theorem | ✅ Complete |
| | Transitive/free actions | ✅ Complete |
| | Burnside's Lemma | ✅ Complete |
| Automorphisms | Inner automorphisms | ✅ Complete |
| | Automorphism composition | ✅ Complete |
| | Inn(G) ≅ G/Z(G) | ✅ Complete |
| | Outer automorphisms | ✅ Complete |
| | Inverse automorphisms | ✅ Complete |
| Group Properties | isAbelian() check | ✅ Complete |
| | exponent() computation | ✅ Complete |

## Interactive Visualizations

Three Swing-based GUI visualizations are available:

1. **Cayley Table Viewer**: Interactive operation table with hover tooltips
2. **Subgroup Lattice Viewer**: Hasse diagram showing containment hierarchy
3. **Cayley Graph Viewer**: Graph representation with colored generator edges

```java
import d021248.group.viz.*;

// Launch all three visualizations
SymmetricGroup s4 = GroupFactory.symmetric(4);
CayleyTableViewer.show(s4, "S_4 Cayley Table");
SubgroupLatticeViewer.show(s4, "S_4 Subgroup Lattice");
CayleyGraphViewer.show(s4, "S_4 Cayley Graph");
```

Or run from command line:
```bash
mvn compile
java -cp target/classes d021248.group.viz.VizDemo S 4    # S_4
java -cp target/classes d021248.group.viz.VizDemo D 5    # D_5
java -cp target/classes d021248.group.viz.VizDemo Z 12   # Z_12
```

See [VISUALIZATIONS.md](VISUALIZATIONS.md) for detailed documentation.

## Limitations / Future Ideas

| Idea | Description | Effort |
|------|-------------|--------|
| Cycle notation | Represent permutations as disjoint cycles | ✅ Done |
| Subgroup generation | Build subgroup from generator set | ✅ Done |
| Direct products | Cartesian product group implementation | ✅ Done |
| Quotient groups | G/H for normal subgroups | ✅ Done |
| Center & Commutator | Z(G) and [G,G] computation | ✅ Done |
| Conjugacy classes | Compute classes & class equation | ✅ Done |
| Homomorphisms | Group morphisms, kernels, and First Isomorphism Theorem | ✅ Done |
| Group actions | Orbits, stabilizers, and Burnside's Lemma | ✅ Done |
| Automorphisms | Aut(G), Inn(G), and Inn(G) ≅ G/Z(G) | ✅ Done |
| Interactive visualizations | Swing-based Cayley tables, lattices, and graphs | ✅ Done |
| Sylow subgroups | Sylow p-subgroup computation | Medium |
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
