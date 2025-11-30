/**
 * Image export utilities for Cayley tables.
 * <p>
 * This package provides tools for generating visual representations of group
 * operation tables as PNG images with various color schemes and layouts.
 * </p>
 * 
 * <p>
 * Example:
 * </p>
 * 
 * <pre>{@code
 * CyclicGroup z16 = new CyclicGroup(16);
 * BufferedImage img = CayleyImageExporter.create(z16, 20); // 20px cells
 * ImageIO.write(img, "png", new File("z16.png"));
 * }</pre>
 * 
 * @see d021248.group.export.CayleyImageExporter
 */
package d021248.group.export;
