# Group Theory REPL - Quick Reference

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
```

### Group Properties
```
g.order           # Get order of group
g.identity        # Get identity element
g.elements        # List all elements
```

### Functions
```
order(g)          # Order of group or element
isAbelian(g)      # Check if group is abelian
isCyclic(g)       # Check if group is cyclic
identity(g)       # Get identity element
elements(g)       # Get all elements
subgroups(g)      # Generate all subgroups
center(g)         # Compute center of group
```

### Element Operations
```
# For cyclic groups
g = Z(6)
a = 2             # Element representing 2 (mod 6)
b = 3             # Element representing 3 (mod 6)

# Element functions
inverse(a)        # Compute inverse
order(a)          # Get order of element
power(a, 3)       # Compute a³ or a * a * a
```

### Special Commands
```
help()            # Show help message
vars              # List all variables
groups            # List all defined groups
clear             # Clear all variables and groups
verbose on/off    # Toggle verbose mode
quit / exit / q   # Exit REPL
```

## Example Session

```
group> g = Z(6)
Z₍6₎ (order 6)

group> g.order
6

group> isAbelian(g)
true

group> isCyclic(g)
true

group> h = D(4)
D₍4₎ (order 8)

group> isAbelian(h)
false

group> subgroups(h)
[ ... (list of subgroups) ... ]

group> center(h)
(center subgroup)

group> groups
Groups:
  g = Z₍6₎ (order 6)
  h = D₍4₎ (order 8)
```

## Advanced Features

### Direct Products
```
g1 = Z(2)
g2 = Z(3)
p = Product(g1, g2)    # Z₂ × Z₃
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
