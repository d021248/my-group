# Group Theory Visualizations

This package provides three interactive Swing-based visualizations for exploring finite groups:

## 1. Cayley Table Viewer (`CayleyTableViewer`)

Interactive operation table with:
- **Color-coded results**: Each element has a unique color
- **Hover tooltips**: Shows `a ∗ b = result` when hovering over cells
- **Identity highlighting**: Identity row and column outlined
- **Interactive exploration**: Click and explore group operations

### Usage:
```java
CyclicGroup z6 = GroupFactory.cyclic(6);
CayleyTableViewer.show(z6, "Z_6 Cayley Table");
```

## 2. Subgroup Lattice Viewer (`SubgroupLatticeViewer`)

Hasse diagram showing subgroup hierarchy with:
- **Layered layout**: Subgroups arranged by order
- **Color coding**:
  - 🔵 Blue: Full group
  - 🟡 Gold: Center Z(G)
  - 🔴 Red: Commutator [G,G]
  - 🟢 Green: Maximal subgroups
  - 🟦 Light blue: Other normal subgroups
  - ⚪ White: Regular subgroups
- **Hover details**: Order, index, and special properties
- **Containment edges**: Lines show which subgroups contain others

### Usage:
```java
SymmetricGroup s4 = GroupFactory.symmetric(4);
SubgroupLatticeViewer.show(s4, "S_4 Subgroup Lattice");
```

## 3. Cayley Graph Viewer (`CayleyGraphViewer`)

Graph representation with generators:
- **Circular layout**: Elements arranged in a circle
- **Generator edges**: Colored arrows for each generator
- **Identity at top**: Gold node for identity element
- **Click to highlight**: Select element to see its generator paths
- **Legend**: Shows generators and their colors

### Usage:
```java
DihedralGroup d5 = GroupFactory.dihedral(5);
CayleyGraphViewer.show(d5, "D_5 Cayley Graph");
```

## Quick Launch Options

### 1. Unified Demo Launcher (Recommended)
```bash
java -cp target/classes d021248.group.GroupDemo
# Interactive menu with all features

./viz.sh demo
# Same as above using shell script
```

### 2. Quick Visualization
```bash
./viz.sh S 4      # S_4 visualizations
./viz.sh D 5      # D_5 visualizations
./viz.sh Z 12     # Z_12 visualizations

# Or directly:
java -cp target/classes d021248.group.GroupDemo viz S 4
```

### 3. VizDemo (All Three Visualizations)
```bash
java -cp target/classes d021248.group.viz.examples.VizDemo S 4
```
Launches Cayley table, subgroup lattice, and Cayley graph for the specified group.

### 4. GUI Launcher
```bash
java -cp target/classes d021248.group.viz.examples.VizLauncher
# Or:
./viz.sh launcher
```
Interactive GUI with dropdowns to select group type and parameter.

### 5. Curated Examples
```bash
java -cp target/classes d021248.group.viz.examples.VisualizationExamples cyclic
java -cp target/classes d021248.group.viz.examples.VisualizationExamples s4
java -cp target/classes d021248.group.viz.examples.VisualizationExamples compare
java -cp target/classes d021248.group.viz.examples.VisualizationExamples all
```
Pre-configured examples showing specific educational scenarios:
- `cyclic` - Z_8 (simple cyclic structure)
- `dihedral` - D_4 (contains Klein four-group)
- `s4` - S_4 (30 subgroups!)
- `compare` - Z_6 vs D_3 (both order 6, different structures)
- `graph` - D_5 Cayley graph focus
- `lattice` - Z_12 lattice showing divisors
- `all` - Run all examples

### 6. Quick Test
```bash
java -cp target/classes d021248.group.viz.examples.QuickVizTest
```
Simple test launching D_4 visualizations.

### 4. GUI Launcher
```bash
java -cp target/classes d021248.group.viz.VizLauncher
# Or:
./viz.sh launcher
CayleyGraphViewer.show(d5, "D_5 Cayley Graph");
```

## Running the Demo

### All visualizations for one group:
```bash
# Compile
mvn clean compile

# Run with specific group
java -cp target/classes d021248.group.viz.VizDemo S 4    # S_4
java -cp target/classes d021248.group.viz.VizDemo D 5    # D_5
java -cp target/classes d021248.group.viz.VizDemo Z 12   # Z_12
java -cp target/classes d021248.group.viz.VizDemo A 4    # A_4
```

### Quick test with D_4:
```bash
java -cp target/classes d021248.group.viz.QuickVizTest
```

## Group Type Options

- `Z` or `Cyclic`: Cyclic groups Z_n
- `D` or `Dihedral`: Dihedral groups D_n
- `S` or `Symmetric`: Symmetric groups S_n
- `A` or `Alternating`: Alternating groups A_n

## Features

### Interactive Elements
- **Hover**: See element details in status bar
- **Click** (Cayley Graph): Highlight paths from selected element
- **Tooltips**: Live operation calculations
- **Status bar**: Shows group properties and current selection

### Visual Indicators
- **Color palettes**: Unique colors for each element
- **Special highlighting**: Identity, generators, normal subgroups
- **Directed edges**: Show generator relationships
- **Layout algorithms**: Optimal placement for readability

## Recommended Groups for Visualization

### Small groups (best for all views):
- `Z_6`, `Z_8`, `Z_12`: Nice subgroup structure
- `D_3`, `D_4`, `D_5`: Dihedral groups (symmetries of polygon)
- `S_3`, `S_4`: Symmetric groups (not too large)

### Medium groups (Cayley table + graph):
- `Z_16`, `Z_20`: Cyclic groups
- `D_6`, `D_8`: Dihedral groups

### Lattice-focused:
- `S_4`: Rich subgroup structure (30 subgroups!)
- `D_4`: Klein four-group appears as subgroup
- `Z_12`: Shows divisor lattice clearly

## Notes

- Window size adapts to group size
- Scrollbars appear for large groups
- Multiple windows can be open simultaneously
- Close windows individually or use your window manager
