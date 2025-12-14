package d021248.group.demo.archive;

import java.util.List;
import java.util.Map;
import java.util.Set;

import d021248.group.GroupFactory;
import d021248.group.conjugacy.ConjugacyAnalyzer;
import d021248.group.conjugacy.ConjugacyClass;
import d021248.group.dihedral.DihedralElement;
import d021248.group.dihedral.DihedralGroup;
import d021248.group.dihedral.Flip;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

/**
 * Demonstrates conjugacy class computations.
 */
public class ConjugacyDemo {

    public static void main(String[] args) {
        demonstrateS3();
        System.out.println();
        demonstrateDihedral();
        System.out.println();
        demonstrateCenterRelation();
    }

    private static void demonstrateS3() {
        System.out.println("=== Conjugacy Classes in S_3 ===");
        SymmetricGroup s3 = GroupFactory.symmetric(3);

        List<ConjugacyClass<Permutation>> classes = ConjugacyAnalyzer.conjugacyClasses(s3);
        System.out.println("Number of conjugacy classes: " + classes.size());

        for (int i = 0; i < classes.size(); i++) {
            ConjugacyClass<Permutation> cl = classes.get(i);
            System.out.println("\nClass " + (i + 1) + " (size " + cl.size() + "):");
            System.out.println("  Representative: " + cl.representative());
            System.out.println("  Elements: " + cl.elements());
        }

        Map<Integer, Long> equation = ConjugacyAnalyzer.classEquation(s3);
        System.out.println("\nClass Equation:");
        equation.forEach((size, count) -> System.out.println("  " + count + " class(es) of size " + size));

        System.out.println("\nVerifying class equation: " +
                ConjugacyAnalyzer.verifyClassEquation(s3));
    }

    private static void demonstrateDihedral() {
        System.out.println("=== Conjugacy Classes in D_4 ===");
        DihedralGroup d4 = GroupFactory.dihedral(4);

        List<ConjugacyClass<DihedralElement>> classes = ConjugacyAnalyzer.conjugacyClasses(d4);
        System.out.println("Number of conjugacy classes: " + classes.size());

        for (int i = 0; i < classes.size(); i++) {
            ConjugacyClass<DihedralElement> cl = classes.get(i);
            System.out.println("\nClass " + (i + 1) + " (size " + cl.size() + "):");
            System.out.println("  Representative: " + cl.representative());
        }

        Map<Integer, Long> equation = ConjugacyAnalyzer.classEquation(d4);
        System.out.println("\nClass Equation:");
        equation.forEach((size, count) -> System.out.println("  " + count + " class(es) of size " + size));
    }

    private static void demonstrateCenterRelation() {
        System.out.println("=== Relationship Between Center and Conjugacy Classes ===");
        DihedralGroup d4 = GroupFactory.dihedral(4);

        Subgroup<DihedralElement> center = SubgroupGenerator.center(d4);
        System.out.println("Center Z(D_4) has order: " + center.order());

        List<ConjugacyClass<DihedralElement>> classes = ConjugacyAnalyzer.conjugacyClasses(d4);
        long singletonClasses = classes.stream().filter(c -> c.size() == 1).count();
        System.out.println("Number of singleton conjugacy classes: " + singletonClasses);
        System.out.println("These match: " + (center.order() == singletonClasses));

        System.out.println("\nOrbit-Stabilizer Verification:");
        DihedralElement g = new DihedralElement(1, Flip.ROTATION, 4);
        Set<DihedralElement> conjugacyClass = ConjugacyAnalyzer.conjugacyClass(d4, g);
        Subgroup<DihedralElement> centralizer = ConjugacyAnalyzer.elementCentralizer(d4, g);

        System.out.println("Element: " + g);
        System.out.println("Conjugacy class size: " + conjugacyClass.size());
        System.out.println("Centralizer order: " + centralizer.order());
        System.out.println("Product: " + (conjugacyClass.size() * centralizer.order()));
        System.out.println("Group order: " + d4.order());
        System.out.println("Orbit-Stabilizer holds: " +
                (conjugacyClass.size() * centralizer.order() == d4.order()));
    }
}
