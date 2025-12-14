package d021248.group.demo.archive;

import java.io.IOException;
import java.util.Set;

import d021248.group.Group;
import d021248.group.export.CayleyImageExporter;
import d021248.group.export.CayleyImageExporter.Layout;
import d021248.group.export.CayleyImageExporter.Palette;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/** Demo to export Cayley table images for S_4 (24 elements). */
public final class S4CayleyImageDemo {
    public static void main(String[] args) throws IOException {
        int n = 4;
        Group<Permutation> s4 = new SymmetricGroup(n);
        int cellSize = 14; // 25x25 grid including headers -> (24+1)*14 ~ 350px

        exportAll(s4, cellSize);
        System.out.println("S_" + n + " Cayley images exported.");
    }

    private static void exportAll(Group<Permutation> group, int cellSize) throws IOException {
        // Standard layouts
        CayleyImageExporter.exportPng(group, cellSize, "S4-hue.png", Palette.HUE, Set.of(), Layout.STANDARD);
        CayleyImageExporter.exportPng(group, cellSize, "S4-gradient.png", Palette.GRADIENT, Set.of(), Layout.STANDARD);
        CayleyImageExporter.exportPng(group, cellSize, "S4-cat.png", Palette.CATEGORICAL_12, Set.of(), Layout.STANDARD);
        // Diagonal identity layout
        CayleyImageExporter.exportPng(group, cellSize, "S4-hue-diagonal.png", Palette.HUE, Set.of(),
                Layout.DIAGONAL_IDENTITY);
        // Legend
        CayleyImageExporter.exportLegend(group, 18, 18, "S4-legend.png", Palette.HUE);
    }

    private S4CayleyImageDemo() {
    }
}
