# Group Theory REPL - Feature Summary (v2.0)

## 🆕 New Features Added

### Enhanced Group Analysis Commands

#### Subgroup Operations
- **`commutator(g)`** - Compute commutator subgroup [G,G]
- **`frattini(g)`** - Compute Frattini subgroup Φ(G)
- **`maximal(g)`** - Find all maximal subgroups
- **`normalizer(g, h)`** - Compute normalizer of subgroup H in G
- **`centralizer(g, h)`** - Compute centralizer of subgroup H in G
- **`centralizer(g, e)`** - Compute centralizer of element e in G
- **`isNormal(g, h)`** - Check if subgroup H is normal in G

#### Conjugacy Analysis
- **`conjugacyClasses(g)`** - Compute all conjugacy classes
- **`conjugate(e, x)`** - Compute conjugate xex⁻¹
- **`classEquation(g)`** - Display class equation |G| = |Z(G)| + Σ|cl(gᵢ)|

#### Group Properties
- **`generators(g)`** - Find all generators of the group
- **`exponent(g)`** - Compute exponent (LCM of element orders)

### 🎨 Interactive Visualizations

#### Cayley Table Viewer
```
group> show(g)
```
Opens interactive color-coded operation table with:
- Unique colors for each element
- Hover tooltips showing operations
- Identity highlighting

#### Subgroup Lattice Viewer
```
group> lattice(g)
```
Opens Hasse diagram showing:
- Hierarchical subgroup structure
- Color coding for special subgroups (center, commutator, maximal)
- Order and index information

#### Cayley Graph Viewer
```
group> graph(g)
```
Opens generator graph with:
- Circular element layout
- Colored edges for each generator
- Interactive element selection

#### All Visualizations at Once
```
group> viz(g)
```
Opens all three visualization windows simultaneously

### Enhanced Output Formatting

#### Improved Subgroup Display
```
group> subgroups(D(3))
Found 6 subgroup(s):

  Subgroup 1:
    Order: 1
    Index: 6
    Elements: { r^0 }

  Subgroup 2:
    Order: 3
    Index: 2
    Elements: { r^0, r^1, r^2 }
  ...
```

#### Conjugacy Class Display
```
group> conjugacyClasses(S(3))
Found 3 conjugacy class(es):

  Class 1:
    Size: 1
    Representative: [1, 2, 3]
    Elements: { [1, 2, 3] }

  Class 2:
    Size: 3
    Representative: [3, 2, 1]
    Elements: { [3, 2, 1], [2, 1, 3], [1, 3, 2] }
  ...
```

#### Class Equation
```
group> classEquation(D(4))
|G| = 8 = 2×1 + 3×2
```

## 📊 Complete Command Reference

### Group Creation
| Command | Description | Example |
|---------|-------------|---------|
| `Z(n)` | Cyclic group of order n | `g = Z(6)` |
| `D(n)` | Dihedral group D_n | `h = D(4)` |
| `S(n)` | Symmetric group S_n | `s = S(3)` |
| `A(n)` | Alternating group A_n | `a = A(4)` |
| `Product(g1, g2)` | Direct product G₁ × G₂ | `p = Product(Z(2), Z(3))` |

### Group Analysis
| Command | Description | Returns |
|---------|-------------|---------|
| `isAbelian(g)` | Check if abelian | boolean |
| `isCyclic(g)` | Check if cyclic | boolean |
| `order(g)` | Group order | integer |
| `exponent(g)` | Group exponent | integer |
| `generators(g)` | All generators | set of elements |
| `identity(g)` | Identity element | element |
| `elements(g)` | All elements | set |

### Subgroups
| Command | Description | Returns |
|---------|-------------|---------|
| `subgroups(g)` | All subgroups | list of subgroups |
| `center(g)` | Center Z(G) | subgroup |
| `commutator(g)` | [G,G] | subgroup |
| `frattini(g)` | Φ(G) | subgroup |
| `maximal(g)` | Maximal subgroups | list of subgroups |
| `normalizer(g, h)` | N_G(H) | subgroup |
| `centralizer(g, h)` | C_G(H) | subgroup |
| `isNormal(g, h)` | H ⊴ G? | boolean |

### Conjugacy
| Command | Description | Returns |
|---------|-------------|---------|
| `conjugacyClasses(g)` | All classes | list of conjugacy classes |
| `conjugate(e, x)` | xex⁻¹ | element |
| `classEquation(g)` | Class equation | string |

### Element Operations
| Command | Description | Returns |
|---------|-------------|---------|
| `inverse(e)` | Element inverse | element |
| `order(e)` | Element order | integer |
| `power(e, n)` | e^n | element |

### Visualizations
| Command | Description |
|---------|-------------|
| `show(g)` | Cayley table window |
| `lattice(g)` | Subgroup lattice window |
| `graph(g)` | Cayley graph window |
| `viz(g)` | All three windows |

### Properties
| Property | Description | Example |
|----------|-------------|---------|
| `g.order` | Group order | `g.order` |
| `g.identity` | Identity | `g.identity` |
| `g.elements` | All elements | `g.elements` |

### Special Commands
| Command | Description |
|---------|-------------|
| `help()` | Show help |
| `vars` | List variables |
| `groups` | List groups |
| `clear` | Clear all |
| `verbose on/off` | Toggle verbose |
| `quit` / `exit` | Exit REPL |

## 🎯 Example Workflows

### Analyzing Group Structure
```
group> g = D(4)
D₍4₎ (order 8)

group> subgroups(g)
Found 10 subgroup(s): ...

group> center(g)
Subgroup (order 2, index 4)

group> commutator(g)
Subgroup (order 4, index 2)

group> maximal(g)
Found 3 subgroup(s): ...

group> viz(g)
All visualizations displayed
```

### Conjugacy Analysis
```
group> s = S(4)
S₍4₎ (order 24)

group> conjugacyClasses(s)
Found 5 conjugacy class(es): ...

group> classEquation(s)
|G| = 24 = 1 + 6 + 8 + 6 + 3

group> lattice(s)
Subgroup lattice displayed in new window
```

### Generator Exploration
```
group> g = Z(12)
Z₍12₎ (order 12)

group> generators(g)
{ 1 (mod 12), 5 (mod 12), 7 (mod 12), 11 (mod 12) }

group> exponent(g)
12

group> isCyclic(g)
true
```

## 🚀 Performance Notes

- Subgroup enumeration limited to groups of order ≤ 32
- Conjugacy class computation is O(n³) for group order n
- Visualizations work best for groups of order ≤ 24
- Cayley table viewer supports up to ~50 elements
- Subgroup lattice viewer supports up to ~100 subgroups

## 💡 Tips

1. Use `help()` to see all available commands
2. Start with small groups (order ≤ 12) for visualization
3. Use `verbose on` to see token and parsing details
4. Combine multiple analyses: `center(g)`, `commutator(g)`, then `lattice(g)`
5. For large groups, use specific queries rather than `subgroups(g)`
6. Visualizations open in separate windows - interact with them while REPL continues

## 🐛 Known Limitations

- Direct products of large groups may be slow
- Frattini subgroup computation requires maximal subgroups (limited to order ≤ 32)
- Element string representations vary by group type
- Visualization window positions are not saved
