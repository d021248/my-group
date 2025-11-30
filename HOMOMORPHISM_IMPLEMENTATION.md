# Group Homomorphisms Implementation

## Overview
Implemented complete support for group homomorphisms, structure-preserving maps between groups that satisfy φ(g₁ ∘ g₂) = φ(g₁) ∘ φ(g₂). Includes kernel/image computation, morphism analysis, and the First Isomorphism Theorem.

## What Was Added

### New Package: `d021248.group.homomorphism`

#### 1. `GroupHomomorphism<E1, E2>` (Functional Interface)
Functional interface for homomorphisms with optional source/target methods:
- `E2 apply(E1)` - apply the homomorphism
- `source()` - get source group (optional)
- `target()` - get target group (optional)

#### 2. `Homomorphism<E1, E2>` (Record)
Concrete implementation binding source, target, and mapping:
- `source()` - source group
- `target()` - target group
- `mapping()` - the actual function
- `apply(E1)` - delegates to mapping

#### 3. `HomomorphismAnalyzer` (Utility Class)
Static methods for homomorphism analysis:

| Method | Description | Mathematical Concept |
|--------|-------------|---------------------|
| `isHomomorphism(φ)` | Verify φ(ab) = φ(a)φ(b) | Homomorphism property |
| `kernel(φ)` | Compute ker(φ) = {g \| φ(g) = e} | Normal subgroup |
| `image(φ)` | Compute im(φ) = {φ(g) \| g ∈ G} | Subgroup of target |
| `isInjective(φ)` | Check if φ is 1-1 ⟺ ker(φ) = {e} | Monomorphism |
| `isSurjective(φ)` | Check if φ is onto ⟺ im(φ) = H | Epimorphism |
| `isIsomorphism(φ)` | Check if bijective | Groups are "the same" |
| `firstIsomorphismTheorem(φ)` | Verify G/ker(φ) ≅ im(φ) | Fundamental theorem |
| `compose(φ, ψ)` | Compute (ψ ∘ φ)(g) = ψ(φ(g)) | Composition |

### Key Theorems Implemented

1. **Homomorphism Verification**: Checks φ(g₁ ∘ g₂) = φ(g₁) ∘ φ(g₂) for all pairs
2. **Kernel is Normal**: ker(φ) is always a normal subgroup of the source
3. **Injective ⟺ Trivial Kernel**: φ is injective if and only if |ker(φ)| = 1
4. **First Isomorphism Theorem**: For any φ: G → H, we have G/ker(φ) ≅ im(φ)
5. **Composition**: (ψ ∘ φ) is a homomorphism when φ: G → H and ψ: H → K

## Test Coverage

### `HomomorphismTest.java` (10 tests)
- ✅ Trivial homomorphism (everything to identity)
- ✅ Sign homomorphism S_3 → Z_2
- ✅ First Isomorphism Theorem for sign homomorphism
- ✅ Isomorphism between cyclic groups
- ✅ Projection homomorphism Z_6 → Z_3
- ✅ Composition of homomorphisms
- ✅ Kernel is always normal
- ✅ Dihedral group homomorphism
- ✅ Image is a subgroup
- ✅ Injective implies trivial kernel

### `HomomorphismDemo.java`
Demonstrates:
- Sign homomorphism S_3 → Z_2 with complete analysis
- First Isomorphism Theorem: S_3/A_3 ≅ Z_2
- Projection Z_6 → Z_3 showing kernel structure
- Composition of homomorphisms Z_12 → Z_4 → Z_2

## Examples

### Sign Homomorphism: S_3 → Z_2
```
Mapping: even permutations → 0, odd permutations → 1

Properties:
  Is homomorphism: true
  Is injective: false
  Is surjective: true
  Is isomorphism: false

Kernel: A_3 (alternating group, order 3)
Image: Z_2 (order 2)

First Isomorphism Theorem: S_3/A_3 ≅ Z_2
Both have order: 2 ✓
```

### Projection: Z_6 → Z_3
```
Mapping: mod 3 reduction

Kernel: {0, 3} in Z_6 (order 2)
Image: Z_3 (order 3)

First Isomorphism Theorem: Z_6/<0,3> ≅ Z_3
|Z_6|/|ker| = 6/2 = 3 = |Z_3| ✓
```

### Composition: (ψ ∘ φ)
```
φ: Z_12 → Z_4 (mod 4)
ψ: Z_4 → Z_2 (mod 2)
(ψ ∘ φ): Z_12 → Z_2

Kernel of composition: {0, 2, 4, 6, 8, 10} (order 6)
```

## Integration

- **Module System**: Added `exports d021248.group.homomorphism;` to `module-info.java`
- **Documentation**: Complete package-info.java with examples and mathematical background
- **README**: Updated with homomorphism examples, First Isomorphism Theorem demonstration
- **Test Suite**: 158 total tests passing (added 10 homomorphism tests)

## Mathematical Significance

Group homomorphisms are central to:
1. **Classification**: Isomorphic groups are algebraically identical
2. **Quotient Groups**: First Isomorphism Theorem connects quotients to images
3. **Normal Subgroups**: Kernels are always normal, explaining quotient structure
4. **Group Actions**: Actions are homomorphisms G → Sym(X)
5. **Representation Theory**: Linear representations are homomorphisms G → GL(V)
6. **Automorphisms**: Self-homomorphisms reveal internal symmetries

## Implementation Notes

- **Functional Interface**: `GroupHomomorphism` allows lambda definitions
- **Type Safety**: Generic types ensure source/target compatibility
- **Verification**: `isHomomorphism()` actually checks the homomorphism property
- **Composition**: Type-checked composition ensures target of φ equals source of ψ
- **Normal Kernels**: All kernels automatically satisfy normality (mathematical theorem)

## Performance Notes

- Homomorphism verification: O(|G|²) - checks all pairs
- Kernel computation: O(|G|) - single pass through elements
- Image computation: O(|G|) - single pass through elements
- Composition: O(1) - creates wrapper function

## Next Steps

With homomorphisms complete, the logical next implementations are:

1. **Group Actions** - homomorphisms G → Sym(X), orbits and stabilizers
2. **Automorphisms** - isomorphisms G → G, inner automorphisms
3. **Sylow Subgroups** - uses conjugacy and group actions
4. **Semidirect Products** - generalizes direct products using homomorphisms

---

**Status**: ✅ Complete - All tests passing (158 total), documentation updated, demo functional
