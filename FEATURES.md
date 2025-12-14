# Group Theory Library - Features

Complete reference for all features in the my-group library.

## Table of Contents
1. [Core Group Types](#core-group-types)
2. [Group Operations](#group-operations)
3. [Advanced Features](#advanced-features)
4. [Visualizations](#visualizations)
5. [Testing](#testing)

---

## Core Group Types

### Cyclic Groups (Z_n)
**Files**: `CyclicGroup.java`, `CyclicElement.java`

Addition modulo n groups with order n.

```java
CyclicGroup z12 = GroupFactory.cyclic(12);
CyclicElement a = new CyclicElement(5, 12);
CyclicElement b = new CyclicElement(7, 12);
CyclicElement sum = z12.operate(a, b);  // (5 + 7) mod 12 = 0
```

### Dihedral Groups (D_n)
**Files**: `DihedralGroup.java`, `DihedralElement.java`

Symmetries of regular n-gons. Order = 2n.

```java
DihedralGroup d5 = GroupFactory.dihedral(5);  // Symmetries of pentagon
// Elements: rotations r^k and reflections sr^k
```

### Symmetric Groups (S_n)
**Files**: `SymmetricGroup.java`, `Permutation.java`

All permutations of n elements. Order = n!

```java
SymmetricGroup s4 = GroupFactory.symmetric(4);  // Order 24
Permutation p = Permutation.cycle(1, 3, 2);     // (1 3 2)
Permutation t = Permutation.transposition(1, 2, 4);  // (1 2)
```

**Permutation Factory Methods** (Added in improvements):
- `identity(n)` - Creates [1,2,...,n]
- `cycle(int... elements)` - Creates cycle notation
- `transposition(i, j, n)` - Creates 2-cycle
- `fromCycles(n, cycles)` - Builds from disjoint cycles

### Alternating Groups (A_n)
**Files**: `AlternatingGroup.java`

Even permutations only. Order = n!/2.

```java
AlternatingGroup a5 = GroupFactory.alternating(5);  // Order 60
// All elements have sign +1
```

---

## Group Operations

### Direct Products (G₁ × G₂)
**Files**: `DirectProduct.java`, `ProductElement.java`

Cartesian product with component-wise operation.

```java
CyclicGroup z2 = GroupFactory.cyclic(2);
CyclicGroup z3 = GroupFactory.cyclic(3);
DirectProduct<CyclicElement, CyclicElement> z6 = GroupFactory.directProduct(z2, z3);

// Klein four-group
var v4 = GroupFactory.directProduct(GroupFactory.cyclic(2), GroupFactory.cyclic(2));
```

**Properties**:
- Order: |G₁ × G₂| = |G₁| × |G₂|
- Type-safe generics
- Component access via `.left()` and `.right()`

### Subgroup Generation
**Files**: `Subgroup.java`, `SubgroupGenerator.java`

#### Generation Methods
```java
// Generate from generators
Subgroup<CyclicElement> h = SubgroupGenerator.generate(z12, Set.of(g));

// All subgroups (for small groups)
List<Subgroup<CyclicElement>> all = SubgroupGenerator.allSubgroups(z12);

// All cyclic subgroups
List<Subgroup<CyclicElement>> cyclic = SubgroupGenerator.cyclicSubgroups(z12);
```

#### Analysis Methods
```java
// Test normality
boolean normal = SubgroupGenerator.isNormal(g, h);

// Compute normalizer N_G(H) = {g : gHg⁻¹ = H}
Subgroup<E> normalizer = SubgroupGenerator.normalizer(g, h);

// Compute centralizer C_G(H) = {g : gh = hg ∀h ∈ H}
Subgroup<E> centralizer = SubgroupGenerator.centralizer(g, h);
```

#### Special Subgroups
```java
// Center Z(G) = {g : gx = xg ∀x ∈ G}
Subgroup<E> center = SubgroupGenerator.center(g);

// Commutator subgroup [G,G]
Subgroup<E> commutator = SubgroupGenerator.commutatorSubgroup(g);

// Frattini subgroup Φ(G)
Subgroup<E> frattini = SubgroupGenerator.frattiniSubgroup(g);
```

### Element Order Calculation
**Added**: Default method in `Element.java`

```java
CyclicElement g = new CyclicElement(4, 12);
int order = g.order(z12);  // 3 (since 4+4+4 ≡ 0 mod 12)

// Works for all element types
Permutation p = Permutation.cycle(1, 2, 3);
int pOrder = p.order(s3);  // 3
```

**Safety**: Guards against infinite loops (max 10,000 iterations)

---

## Advanced Features

### Homomorphisms
**Files**: `Homomorphism.java`, `HomomorphismAnalyzer.java`

Structure-preserving maps between groups.

```java
// Sign homomorphism: S_3 → Z_2
Homomorphism<Permutation, CyclicElement> sign = new Homomorphism<>(
    s3, z2,
    p -> new CyclicElement(p.sign() == 1 ? 0 : 1, 2)
);

// Analysis
boolean isHomo = HomomorphismAnalyzer.isHomomorphism(sign);
Subgroup<Permutation> kernel = HomomorphismAnalyzer.kernel(sign);  // A_3
Subgroup<CyclicElement> image = HomomorphismAnalyzer.image(sign);   // Z_2

// Test properties
boolean injective = HomomorphismAnalyzer.isInjective(sign);  // false
boolean surjective = HomomorphismAnalyzer.isSurjective(sign); // true
boolean isomorphism = HomomorphismAnalyzer.isIsomorphism(sign); // false

// Composition
Homomorphism<E1, E3> composed = HomomorphismAnalyzer.compose(phi, psi);
```

**Theorems**:
- **First Isomorphism**: G/ker(φ) ≅ im(φ)
- **Kernel is Normal**: ker(φ) ⊴ G
- **Injective ⟺ Trivial Kernel**: φ injective ⟺ |ker(φ)| = 1

### Conjugacy Classes
**Files**: `ConjugacyClass.java`, `ConjugacyAnalyzer.java`

Partition groups by conjugation.

```java
// All conjugacy classes
List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s3);

// Single class
ConjugacyClass<Permutation> cl = ConjugacyAnalyzer.conjugacyClass(s3, element);

// Test conjugacy
boolean conjugate = ConjugacyAnalyzer.areConjugate(s3, g, h);

// Class equation: |G| = Σ|cl(g)|
Map<Integer, Long> equation = ConjugacyAnalyzer.classEquation(s3);
boolean valid = ConjugacyAnalyzer.verifyClassEquation(s3);

// Centralizer of element: C_G(g) = {x : xgx⁻¹ = g}
Subgroup<E> centralizer = ConjugacyAnalyzer.elementCentralizer(g, element);
```

**Key Properties**:
- Abelian groups: All singleton classes
- Center: Z(G) = union of singleton classes
- Orbit-Stabilizer: |cl(g)| × |C_G(g)| = |G|

### Group Actions
**Files**: `Action.java`, `ActionAnalyzer.java`, `Orbit.java`

Groups acting on sets.

```java
// Conjugation action: G acts on itself
Action<Permutation, Permutation> conjugation = new Action<>(
    s3,
    s3.elements(),
    (g, h) -> s3.operate(s3.operate(g, h), s3.inverse(g))
);

// Verify it's an action
boolean valid = ActionAnalyzer.isAction(conjugation);

// Compute orbits
List<Orbit<Permutation>> orbits = ActionAnalyzer.orbits(conjugation);
Orbit<Permutation> orbit = ActionAnalyzer.orbit(conjugation, element);

// Compute stabilizer
Subgroup<Permutation> stabilizer = ActionAnalyzer.stabilizer(conjugation, element);

// Properties
boolean transitive = ActionAnalyzer.isTransitive(conjugation);
boolean free = ActionAnalyzer.isFree(conjugation);

// Burnside's Lemma
int numOrbits = ActionAnalyzer.burnsideLemma(conjugation);
```

### Cayley's Theorem
**Files**: `CayleyPermutationGroup.java`

Every group is isomorphic to a permutation group.

```java
// Convert any group to permutation representation
CyclicGroup z4 = GroupFactory.cyclic(4);
CayleyPermutationGroup<CyclicElement> permZ4 = new CayleyPermutationGroup<>(z4);

// Same order, isomorphic structure
System.out.println(permZ4.order());  // 4 (same as Z_4)

// Can visualize the permutation group
CayleyTableViewer.show(permZ4, "Z_4 as Permutations");
```

### Automorphisms
**Files**: `Automorphism.java`, `AutomorphismGroup.java`

Isomorphisms from a group to itself.

```java
AutomorphismGroup<CyclicElement> autZ6 = new AutomorphismGroup<>(z6);

// Get all automorphisms
var allAutos = autZ6.elements();

// Automorphism group is itself a group under composition
Automorphism<CyclicElement> auto1 = ...;
Automorphism<CyclicElement> auto2 = ...;
Automorphism<CyclicElement> composed = autZ6.operate(auto1, auto2);
```

### Quotient Groups
**Files**: `QuotientGroup.java`, `CosetElement.java`

Factor groups G/N where N ⊴ G.

```java
// G/N where N is normal
QuotientGroup<Permutation> quotient = new QuotientGroup<>(s3, kernel);

// Natural projection homomorphism
Homomorphism<Permutation, CosetElement<Permutation>> projection = 
    quotient.naturalProjection();
```

---

## Visualizations

### 1. Cayley Table Viewer
**File**: `CayleyTableViewer.java`

Interactive color-coded operation tables.

```java
CayleyTableViewer.show(group, "Group Name");
```

**Features**:
- Unique color per element
- Hover tooltips: "a ∗ b = result"
- Identity row/column highlighting
- Adaptive sizing

### 2. Subgroup Lattice Viewer
**File**: `SubgroupLatticeViewer.java`

Hasse diagram of subgroup structure.

```java
SubgroupLatticeViewer.show(group, "Group Name");
```

**Features**:
- Hierarchical layout by order
- Color coding:
  - 🟡 Gold: Center Z(G)
  - 🔴 Red: Commutator [G,G]
  - 🟣 Purple: Frattini Φ(G)
  - 🟢 Green: Maximal subgroups
  - 🔵 Light blue: Normal subgroups
  - ⚪ White: Regular subgroups
- Containment edges
- Hover details: order, index, normality

### 3. Cayley Graph Viewer
**File**: `CayleyGraphViewer.java`

Generator-based graph representation.

```java
CayleyGraphViewer.show(group, "Group Name");
```

**Features**:
- Circular layout
- Identity at top (gold)
- Colored edges per generator
- Click to highlight paths
- Legend with generators

### Quick Launch Options

**Command-line Demo**:
```bash
java d021248.group.GroupDemo viz S 4  # Launch all 3 visualizations for S_4
```

**Shell Script**:
```bash
./viz.sh S 4      # S_4
./viz.sh D 5      # D_5
./viz.sh Z 12     # Z_12
./viz.sh launcher # GUI launcher
```

---

## Testing

### Test Suite
**168 tests total**, all passing:
- Property-based tests (jqwik) for axiom verification
- Unit tests for all group types
- Integration tests for advanced features
- Theorem verification tests

### Key Test Coverage
- ✅ Group axioms (closure, associativity, identity, inverses)
- ✅ Direct product properties
- ✅ Subgroup generation and Lagrange's theorem
- ✅ Homomorphism properties and First Isomorphism Theorem
- ✅ Conjugacy class partitioning and class equation
- ✅ Group action axioms and Orbit-Stabilizer theorem
- ✅ Element order calculation
- ✅ Cayley's theorem embedding
- ✅ Permutation operations and cycle decomposition

### Property-Based Testing
Uses jqwik for randomized property testing:
```java
@Property
boolean groupAxiomsHold(@ForAll("cyclicGroups") CyclicGroup g) {
    return GroupVerifier.verify(g).isValid();
}
```

---

## Utilities

### Group Verifier
**File**: `GroupVerifier.java`

Validates group axioms:
```java
VerificationResult result = GroupVerifier.verify(group);
System.out.println(result.summary());
```

### Table Formatters
**File**: `GroupTableFormatter.java`

Multiple output formats:
```java
var cfg = GroupTableFormatter.forGroup(group)
    .ordering(customOrder)
    .highlightIdentity(true)
    .build();

System.out.println(GroupTableFormatter.toMarkdown(cfg));
System.out.println(GroupTableFormatter.toPlainText(cfg));
System.out.println(GroupTableFormatter.toLatex(cfg));
```

### Strategy Pattern
**Files**: `GenerationStrategy.java`, `StrategyRegistry.java`

Customize group generation:
```java
StrategyRegistry.register(SymmetricGroup.class, customStrategy);
var helper = new GroupHelper<>(s4);
Set<Permutation> generators = helper.generators();
```

---

## Implementation History

### Phase 1: Core Groups
- Cyclic, dihedral, symmetric groups
- Basic operations and element types
- Group verifier and axiom testing

### Phase 2: Direct Products & Subgroups
- Direct product implementation
- Subgroup generation from generators
- All subgroups enumeration (small groups)
- Normalizer, centralizer computation
- Special subgroups (center, commutator, Frattini)

### Phase 3: Improvements
- Permutation factory methods
- Element order calculation
- AlternatingGroup implementation
- JavaDoc examples
- Lint fixes and code quality

### Phase 4: Homomorphisms
- Homomorphism interface and implementation
- Kernel and image computation
- Morphism testing (injective, surjective, isomorphism)
- First Isomorphism Theorem
- Composition of homomorphisms

### Phase 5: Conjugacy Classes
- Conjugacy class computation
- Class equation verification
- Centralizer of elements
- Orbit-Stabilizer theorem
- Relationship with center

### Phase 6: Group Actions
- General action framework
- Orbit and stabilizer computation
- Action properties (transitive, free)
- Burnside's Lemma
- Integration with conjugacy classes

### Phase 7: Visualizations
- Cayley table viewer (color-coded, interactive)
- Subgroup lattice viewer (Hasse diagram)
- Cayley graph viewer (generator graphs)
- Demo launchers (CLI and GUI)
- Documentation and examples

### Phase 8: Consolidation
- Unified GroupDemo launcher
- Streamlined documentation
- Simplified project structure

---

## References

- Dummit & Foote, *Abstract Algebra* (3rd ed.)
- Judson, *Abstract Algebra: Theory and Applications*
- Rotman, *An Introduction to the Theory of Groups* (4th ed.)
