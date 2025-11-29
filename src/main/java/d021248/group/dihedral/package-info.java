/**
 * Dihedral groups (D_n) - symmetries of regular n-gons.
 * <p>
 * The dihedral group D_n represents the symmetries of a regular n-sided
 * polygon,
 * consisting of n rotations and n reflections.
 * </p>
 * 
 * <p>
 * Order: |D_n| = 2n
 * </p>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * DihedralGroup d6 = new DihedralGroup(6); // Symmetries of hexagon
 * DihedralElement r = new DihedralElement(1, Flip.ROTATION, 6); // 60° rotation
 * DihedralElement s = new DihedralElement(0, Flip.REFLECTION, 6); // reflection
 * DihedralElement sr = d6.operate(s, r); // reflection followed by rotation
 * }</pre>
 * 
 * @see d021248.group.dihedral.DihedralGroup
 * @see d021248.group.dihedral.DihedralElement
 * @see d021248.group.dihedral.Flip
 */
package d021248.group.dihedral;
