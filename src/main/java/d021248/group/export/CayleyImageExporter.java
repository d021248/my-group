package d021248.group.export;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;

import javax.imageio.ImageIO;

import d021248.group.FiniteGroup;
import d021248.group.api.Element;

/**
 * Image export utilities for Cayley tables with palettes, legend and subgroup
 * highlighting.
 */
public final class CayleyImageExporter {
    private CayleyImageExporter() {
    }

    public enum Palette {
        HUE, GRADIENT, CATEGORICAL_12
    }

    private static final Color[] CATEGORICAL12 = new Color[] {
            new Color(0x1f77b4), new Color(0xff7f0e), new Color(0x2ca02c), new Color(0xd62728), new Color(0x9467bd),
            new Color(0x8c564b), new Color(0xe377c2), new Color(0x7f7f7f), new Color(0xbcbd22), new Color(0x17becf),
            new Color(0x393b79), new Color(0x637939) };

    public static <E extends Element> BufferedImage create(FiniteGroup<E> group, int cellSize) {
        return create(group, cellSize, Palette.HUE, Set.of());
    }

    public static <E extends Element> BufferedImage create(FiniteGroup<E> group, int cellSize, Palette palette,
            Set<E> subgroup) {
        List<E> elems = new ArrayList<>(group.elements());
        int n = elems.size();
        int size = (n + 1) * cellSize;
        BufferedImage img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < size; y++)
            for (int x = 0; x < size; x++)
                img.setRGB(x, y, 0xFFFFFFFF);
        Map<E, Integer> index = new HashMap<>();
        for (int i = 0; i < n; i++)
            index.put(elems.get(i), i);
        for (int i = 0; i < n; i++) {
            int topX = cellSize + i * cellSize;
            fillRect(img, topX, 0, cellSize, cellSize, colorFor(i, n, palette));
            int leftY = cellSize + i * cellSize;
            fillRect(img, 0, leftY, cellSize, cellSize, colorFor(i, n, palette));
        }
        fillRect(img, 0, 0, cellSize, cellSize, Color.BLACK);
        IntStream.range(0, n).parallel().forEach(r -> {
            E a = elems.get(r);
            for (int c = 0; c < n; c++) {
                E b = elems.get(c);
                E prod = group.operation().calculate(a, b);
                int idx = index.get(prod);
                int x = cellSize + c * cellSize;
                int y = cellSize + r * cellSize;
                fillRect(img, x, y, cellSize, cellSize, colorFor(idx, n, palette));
                if (prod.equals(group.identity()))
                    strokeRect(img, x, y, cellSize, cellSize, Color.BLACK.getRGB());
                if (!subgroup.isEmpty() && subgroup.contains(prod))
                    strokeRect(img, x + 1, y + 1, cellSize - 2, cellSize - 2, Color.DARK_GRAY.getRGB());
            }
        });
        return img;
    }

    public static <E extends Element> File exportPng(FiniteGroup<E> group, int cellSize, String filename)
            throws IOException {
        return exportPng(group, cellSize, filename, Palette.HUE, Set.of());
    }

    public static <E extends Element> File exportPng(FiniteGroup<E> group, int cellSize, String filename,
            Palette palette, Set<E> subgroup) throws IOException {
        BufferedImage img = create(group, cellSize, palette, subgroup);
        File file = new File(filename);
        ImageIO.write(img, "png", file);
        return file;
    }

    public static <E extends Element> File exportLegend(FiniteGroup<E> group, int swatchW, int swatchH, String filename,
            Palette palette) throws IOException {
        List<E> elems = new ArrayList<>(group.elements());
        int n = elems.size();
        int columns = Math.min(64, n);
        int rows = (int) Math.ceil(n / (double) columns);
        int pad = 4;
        int imgW = columns * (swatchW + pad) + pad;
        int imgH = rows * (swatchH + pad) + pad + 16;
        BufferedImage img = new BufferedImage(imgW, imgH, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, imgW, imgH);
        g2.setFont(new Font(Font.MONOSPACED, Font.PLAIN, Math.max(10, swatchH - 4)));
        for (int i = 0; i < n; i++) {
            int row = i / columns;
            int col = i % columns;
            int x = pad + col * (swatchW + pad);
            int y = pad + row * (swatchH + pad);
            g2.setColor(colorFor(i, n, palette));
            g2.fillRect(x, y, swatchW, swatchH);
            g2.setColor(Color.BLACK);
            g2.drawRect(x, y, swatchW, swatchH);
            g2.drawString(Integer.toString(i), x + 2, y + swatchH - 2);
        }
        g2.dispose();
        File file = new File(filename);
        ImageIO.write(img, "png", file);
        return file;
    }

    private static void fillRect(BufferedImage img, int x, int y, int w, int h, Color color) {
        int rgb = color.getRGB();
        for (int yy = y; yy < y + h; yy++)
            for (int xx = x; xx < x + w; xx++)
                img.setRGB(xx, yy, rgb);
    }

    private static void strokeRect(BufferedImage img, int x, int y, int w, int h, int rgb) {
        for (int xx = x; xx < x + w; xx++) {
            img.setRGB(xx, y, rgb);
            img.setRGB(xx, y + h - 1, rgb);
        } // top & bottom
        for (int yy = y; yy < y + h; yy++) {
            img.setRGB(x, yy, rgb);
            img.setRGB(x + w - 1, yy, rgb);
        } // left & right
    }

    private static Color colorFor(int idx, int total, Palette palette) {
        if (idx < 0)
            return Color.WHITE;
        return switch (palette) {
            case HUE -> Color.getHSBColor((float) idx / Math.max(1, total), 0.6f, 0.9f);
            case GRADIENT -> {
                float t = (float) idx / Math.max(1, total);
                yield Color.getHSBColor(0.58f, 0.3f + 0.4f * t, 0.3f + 0.6f * t);
            }
            case CATEGORICAL_12 -> CATEGORICAL12[idx % CATEGORICAL12.length];
        };
    }
}
