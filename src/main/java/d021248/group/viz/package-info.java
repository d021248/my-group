/**
 * Interactive Swing-based visualizations for group theory concepts.
 * <p>
 * This package provides GUI components for visualizing groups, including:
 * </p>
 * <ul>
 * <li>{@link d021248.group.viz.CayleyTableViewer} - Interactive Cayley
 * operation tables</li>
 * <li>{@link d021248.group.viz.SubgroupLatticeViewer} - Hasse diagrams of
 * subgroup lattices</li>
 * <li>{@link d021248.group.viz.CayleyGraphViewer} - Graph visualization with
 * generators</li>
 * </ul>
 * 
 * <p>
 * Example usage:
 * </p>
 * 
 * <pre>{@code
 * SymmetricGroup s4 = GroupFactory.symmetric(4);
 * CayleyTableViewer.show(s4, "S4 Cayley Table");
 * SubgroupLatticeViewer.show(s4, "S4 Subgroup Lattice");
 * CayleyGraphViewer.show(s4, "S4 Cayley Graph");
 * }</pre>
 */
package d021248.group.viz;
