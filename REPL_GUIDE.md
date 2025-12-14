# Group Theory REPL - Quick Reference (v2.0)

## Starting the REPL
```bash
./repl.sh                    # Launch interactive shell
mvn compile && java -cp target/classes d021248.group.repl.GroupREPL  # Alternative
```

## Basic Usage

### Creating Groups
```
g = Z(6)          # Cyclic group Z₆
h = D(4)          # Dihedral group D₄  
s = S(4)          # Symmetric group S₄
a = A(5)          # Alternating group A₅
p = Product(g, h) # Direct product G × H
```

### Group Properties
```
g.order           # Get order of group
g.identity        # Get identity element
g.elements        # List all elements
```

## Group Analysis Functions

### Basic Properties
```
order(g)          # Order of group or element
isAbelian(g)      # Check if group is abelian
isCyclic(g)       # Check if group is cyclic
identity(g)       # Get identity element
elements(g)       # Get all elements
exponent(g)       # Exponent of group (LCM of element orders)
generators(g)     # Find all generators
```

### Subgroups
```
subgroups(g)      # All subgroups
center(g)         # Center Z(G)
commutator(g)     # Commutator subgroup [G,G]
frattini(g)       # Frattini subgroup Φ(G)
maximal(g)        # All maximal subgroups
normalizer(g, h)  # Normalizer of subgroup H in G
centralizer(g, h) # Centralizer of subgroup H in G
centralizer(g, e) # Centralizer of element e in G
isNormal(g, h)    # Check if H is normal in G
```

### Conjugacy
```
conjugacyClasses(g)  # All conjugacy classes
conjugate(g, x)      # Compute xgx⁻¹
classEquation(g)     # Display class equation
```

## Visualizations 🎨

### Interactive GUI Windows
```
show(g)           # Cayley table (operation table with colors)
lattice(g)        # Subgroup lattice (Hasse diagram)
graph(g)          # Cayley graph (generator graph)
viz(g)            # All three visualizations at once
```

**Visualization Features:**
- **Cayley Table**: Color-coded operation table with hover tooltips
- **Subgroup Lattice**: Hierarchical diagram showing containment
- **Cayley Graph**: Graph with generators as colored edges

## Element Operations
```
inverse(e)        # Compute inverse of element
order(e)          # Order of element
power(e, n)       # Compute eⁿ
conjugate(e, x)   # Compute xex⁻¹
```

## Special Commands
```
help()            # Show comprehensive help
vars              # List all variables
groups            # List all defined groups
clear             # Clear all variables and groups
verbose on/off    # Toggle verbose mode
quit / exit / q   # Exit REPL
```

## Example Sessions

### Basic Exploration
```
group> g = Z(6)
Z₍6₎ (order 6)

group> g.order
6

group> isAbelian(g)
true

group> exponent(g)
6

group> generators(g)
{ 1 (mod 6), 5 (mod 6) }
```

### Subgroup Analysis
```
group> h = D(3)
D₍3₎ (order 6)

group> subgroups(h)
Found 6 subgroup(s):
  Subgroup 1:
    Order: 1
    Index: 6
    Elements: { r^0 }
  ...

group> center(h)
Subgroup (order 1, index 6)
Elements: { r^0 }

group> commutator(h)
Subgroup (order 3, index 2)
Elements: { r^0, r^1, r^2 }
```

### Conjugacy Classes
```
group> s = S(3)
S₍3₎ (order 6)

group> conjugacyClasses(s)
Found 3 conjugacy class(es):
  Class 1:
    Size: 1
    Representative: ()
  Class 2:
    Size: 3
    Representative: (0 1)
  Class 3:
    Size: 2
    Representative: (0 1 2)

group> classEquation(s)
|G| = 6 = 1 + 3 + 2
```

### Visualizations
```
group> g = D(4)
D₍4₎ (order 8)

group> show(g)           # Opens Cayley table window
Cayley table displayed in new window

group> lattice(g)        # Opens subgroup lattice
Subgroup lattice displayed in new window

group> graph(g)          # Opens Cayley graph
Cayley graph displayed in new window

group> viz(g)            # Opens all three!
All visualizations displayed
```

### Analyzing Group Properties
```
g = S(4)
order(g)               # Should be 24
isAbelian(g)          # false (S_n is non-abelian for n ≥ 3)
subgroups(g)          # All subgroups of S₄
center(g)             # Center (trivial for S_n, n ≥ 3)
```

## Tips

1. Group names are case-sensitive
2. Use `vars` to see what you've defined
3. Use `groups` to see all groups in scope
4. Type `help()` anytime for guidance
5. Press Ctrl+C or type `quit` to exit
