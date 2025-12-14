package d021248.group.util;

/**
 * Simple 2D point record for visualization layouts.
 * <p>
 * Used by visualization components to store element positions
 * in Cayley graphs and subgroup lattice diagrams.
 * </p>
 *
 * @param x horizontal coordinate
 * @param y vertical coordinate
 */
public record Point(int x, int y) {
}
