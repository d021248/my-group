# Archived Demo Files

This directory contains the original individual demo files that have been consolidated into the unified `GroupDemo.java` launcher.

These files are preserved as reference examples showing specific feature usage:

- `Demo.java` - Basic group creation and strategy usage
- `GroupPropertiesDemo.java` - Properties like abelian, exponent
- `ProductSubgroupDemo.java` - Direct products and subgroup analysis  
- `QuotientGroupDemo.java` - Quotient groups
- `FrattiniDemo.java` - Frattini subgroup computation
- `S4CayleyImageDemo.java` - Cayley graph export for S_4
- `Z256CayleyImageDemo.java` - Cayley graph export for Z_256
- `ActionDemo.java` - Group actions, orbits, stabilizers
- `AutomorphismDemo.java` - Automorphisms
- `ConjugacyDemo.java` - Conjugacy classes
- `HomomorphismDemo.java` - Homomorphisms and First Isomorphism Theorem
- `CayleyTheoremDemo.java` - Cayley's theorem demonstration
- `S4Demo.java` - Quick S_4 visualization

## Using the New Unified Launcher

Instead of running individual demos, use:

```bash
# Interactive menu
java -cp target/classes d021248.group.GroupDemo

# Quick visualization
java -cp target/classes d021248.group.GroupDemo viz S 4

# Or use the shell script
./viz.sh demo
./viz.sh S 4
```

The unified launcher provides:
- Interactive menus for all features
- Organized sections: Visualizations, Concepts, Theorems
- Better user experience with consistent interface
