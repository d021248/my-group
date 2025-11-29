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

    public enum Layout {
        STANDARD, DIAGONAL_IDENTITY
    }

    private static final Color[] CATEGORICAL12 = new Color[] {
            new Color(0x1f77b4), new Color(0xff7f0e), new Color(0x2ca02c), new Color(0xd62728), new Color(0x9467bd),
            new Color(0x8c564b), new Color(0xe377c2), new Color(0x7f7f7f), new Color(0xbcbd22), new Color(0x17becf),
            new Color(0x393b79), new Color(0x637939) };

    public static <E extends Element> BufferedImage create(FiniteGroup<E> group, int cellSize) {
        return create(group, cellSize, Palette.HUE, Set.of(), Layout.STANDARD);
    }

    public static <E extends Element> BufferedImage create(FiniteGroup<E> group, int cellSize, Palette palette,
            Set<E> subgroup, Layout layout) {
        List<E> elems = new ArrayList<>(group.elements());
        int n = elems.size();
        BufferedImage img = new BufferedImage((n + 1) * cellSize, (n + 1) * cellSize, BufferedImage.TYPE_INT_ARGB);
        fillBackground(img);
        List<E> columnElems = layout == Layout.DIAGONAL_IDENTITY ? reorderByInverse(group, elems) : elems;
        Map<E, Integer> index = buildIndex(columnElems);
        drawHeaders(img, n, cellSize, palette);
        CosetData<E> cosets = computeCosets(group, elems, subgroup);
        TableDims dims = new TableDims(n, cellSize);
        ElementsIndex<E> ei = new ElementsIndex<>(elems, index);
        Structure<E> structure = new Structure<>(cosets, subgroup);
        RenderContext<E> ctx = new RenderContext<>(img, group, ei, structure, dims, palette);
        ctx.columnElements = columnElems; // override columns if layout requires
        fillProducts(ctx);
        return img;
    }

    private static void fillBackground(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                img.setRGB(x, y, 0xFFFFFFFF);
    }

    private static <E extends Element> Map<E, Integer> buildIndex(List<E> elems) {
        Map<E, Integer> map = new HashMap<>();
        for (int i = 0; i < elems.size(); i++)
            map.put(elems.get(i), i);
        return map;
    }

    private static void drawHeaders(BufferedImage img, int n, int cellSize, Palette palette) {
        for (int i = 0; i < n; i++) {
            int topX = cellSize + i * cellSize;
            fillRect(img, topX, 0, cellSize, cellSize, colorFor(i, n, palette));
            int leftY = cellSize + i * cellSize;
            fillRect(img, 0, leftY, cellSize, cellSize, colorFor(i, n, palette));
        }
        fillRect(img, 0, 0, cellSize, cellSize, Color.BLACK);
    }

    private static <E extends Element> void fillProducts(RenderContext<E> ctx) {
        IntStream.range(0, ctx.dims.n).parallel().forEach(r -> {
            for (int c = 0; c < ctx.dims.n; c++)
                renderCell(ctx, r, c);
        });
    }

    private static final class TableDims {
        final int n;
        final int cellSize;

        TableDims(int n, int cellSize) {
            this.n = n;
            this.cellSize = cellSize;
        }
    }

    private static final class ElementsIndex<E extends Element> {
        final List<E> elems;
        final Map<E, Integer> index;

        ElementsIndex(List<E> elems, Map<E, Integer> index) {
            this.elems = elems;
            this.index = index;
        }
    }

    private static final class Structure<E extends Element> {
        final CosetData<E> cosets;
        final Set<E> subgroup;

        Structure(CosetData<E> cosets, Set<E> subgroup) {
            this.cosets = cosets;
            this.subgroup = subgroup;
        }
    }

    private static final class RenderContext<E extends Element> {
        final BufferedImage img;
        final FiniteGroup<E> group;
        final ElementsIndex<E> elementsIndex;
        final Structure<E> structure;
        final TableDims dims;
        final Palette palette;
        List<E> columnElements; // may differ from row elements

        RenderContext(BufferedImage img, FiniteGroup<E> group, ElementsIndex<E> elementsIndex, Structure<E> structure,
                TableDims dims, Palette palette) {
            this.img = img;
            this.group = group;
            this.elementsIndex = elementsIndex;
            this.structure = structure;
            this.dims = dims;
            this.palette = palette;
            this.columnElements = elementsIndex.elems; // default
        }
    }

    private static <E extends Element> void renderCell(RenderContext<E> ctx, int r, int c) {
        E prod = ctx.group.operation().calculate(ctx.elementsIndex.elems.get(r), ctx.columnElements.get(c));
        int idx = ctx.elementsIndex.index.get(prod);
        int x = ctx.dims.cellSize + c * ctx.dims.cellSize;
        int y = ctx.dims.cellSize + r * ctx.dims.cellSize;
        fillRect(ctx.img, x, y, ctx.dims.cellSize, ctx.dims.cellSize, colorFor(idx, ctx.dims.n, ctx.palette));
        if (prod.equals(ctx.group.identity()))
            strokeRect(ctx.img, x, y, ctx.dims.cellSize, ctx.dims.cellSize, Color.BLACK.getRGB());
        if (!ctx.structure.subgroup.isEmpty() && ctx.structure.subgroup.contains(prod))
            strokeRect(ctx.img, x + 1, y + 1, ctx.dims.cellSize - 2, ctx.dims.cellSize - 2, Color.DARK_GRAY.getRGB());
        if (!ctx.structure.cosets.index().isEmpty()) {
            Integer ci = ctx.structure.cosets.index().get(prod);
            if (ci != null)
                strokeRect(ctx.img, x, y, ctx.dims.cellSize, ctx.dims.cellSize,
                        ctx.structure.cosets.colors().get(ci).getRGB());
        }
    }

    private static <E extends Element> CosetData<E> computeCosets(FiniteGroup<E> group, List<E> elems,
            Set<E> subgroup) {
        Map<E, Integer> index = new HashMap<>();
        List<Color> colors = new ArrayList<>();
        if (subgroup.isEmpty())
            return new CosetData<>(index, colors);
        int cosetCounter = 0;
        for (E g : elems) {
            if (index.containsKey(g))
                continue;
            int cosetId = cosetCounter++;
            colors.add(Color.getHSBColor((float) cosetId / Math.max(1, cosetCounter + 5), 0.85f, 0.55f));
            for (E s : subgroup) {
                E gs = group.operation().calculate(g, s);
                index.put(gs, cosetId);
            }
        }
        return new CosetData<>(index, colors);
    }

    private static final record CosetData<E extends Element>(Map<E, Integer> index, List<Color> colors) {
    }

    public static <E extends Element> File exportPng(FiniteGroup<E> group, int cellSize, String filename)
            throws IOException {
        return exportPng(group, cellSize, filename, Palette.HUE, Set.of(), Layout.STANDARD);
    }

    public static <E extends Element> File exportPng(FiniteGroup<E> group, int cellSize, String filename,
            Palette palette, Set<E> subgroup, Layout layout) throws IOException {
        BufferedImage img = create(group, cellSize, palette, subgroup, layout);
        File file = new File(filename);
        ImageIO.write(img, "png", file);
        return file;
    }

    private static <E extends Element> List<E> reorderByInverse(FiniteGroup<E> group, List<E> elems) {
        List<E> cols = new ArrayList<>(elems.size());
        E id = group.identity();
        for (E a : elems)
            cols.add(findInverse(group, a, elems, id));
        return cols;
    }

    private static <E extends Element> E findInverse(FiniteGroup<E> group, E a, List<E> elems, E id) {
        for (E b : elems)
            if (group.operation().calculate(a, b).equals(id))
                return b;
        return a; // fallback should not occur for valid group
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
