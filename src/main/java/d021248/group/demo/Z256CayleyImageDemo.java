package d021248.group.demo;

import java.util.Set;

import d021248.group.cyclic.CyclicGroup;
import d021248.group.export.CayleyImageExporter;

/** Enhanced demo exporting multiple palette variants for Z_256. */
public final class Z256CayleyImageDemo {
    public static void main(String[] args) throws Exception {
        int modulus = 256;
        int cellSize = args.length > 0 ? Integer.parseInt(args[0]) : 4;
        CyclicGroup z256 = new CyclicGroup(modulus);
        System.out.println("Generating palette variants for Z_256...");
        var hue = CayleyImageExporter.exportPng(z256, cellSize, "Z256-hue.png", CayleyImageExporter.Palette.HUE,
                Set.of());
        var grad = CayleyImageExporter.exportPng(z256, cellSize, "Z256-gradient.png",
                CayleyImageExporter.Palette.GRADIENT, Set.of());
        var cat = CayleyImageExporter.exportPng(z256, cellSize, "Z256-cat.png",
                CayleyImageExporter.Palette.CATEGORICAL_12, Set.of());
        var legend = CayleyImageExporter.exportLegend(z256, 12, 12, "Z256-legend.png", CayleyImageExporter.Palette.HUE);
        System.out.println("Hue:     " + hue.getAbsolutePath());
        System.out.println("Gradient:" + grad.getAbsolutePath());
        System.out.println("Categorical: " + cat.getAbsolutePath());
        System.out.println("Legend:  " + legend.getAbsolutePath());
    }
}
