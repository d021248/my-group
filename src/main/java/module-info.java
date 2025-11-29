/**
 * Java Platform Module System descriptor for the group theory library.
 * <p>
 * This module provides implementations of finite groups including:
 * </p>
 * <ul>
 * <li>Cyclic groups (Z_n)</li>
 * <li>Dihedral groups (D_n)</li>
 * <li>Symmetric groups (S_n)</li>
 * <li>Alternating groups (A_n)</li>
 * <li>Direct products (G × H)</li>
 * <li>Quotient groups (G/H)</li>
 * <li>Subgroup generation and analysis</li>
 * </ul>
 */
module d021248.group {
    // Core API exports
    exports d021248.group;
    exports d021248.group.api;

    // Group implementations
    exports d021248.group.cyclic;
    exports d021248.group.dihedral;
    exports d021248.group.symmetric;

    // Advanced features
    exports d021248.group.product;
    exports d021248.group.subgroup;
    exports d021248.group.quotient;

    // Utilities and export
    exports d021248.group.util;
    exports d021248.group.export;

    // Internal packages (not exported)
    // d021248.group.base
    // d021248.group.strategy
    // d021248.group.demo

    // Dependencies
    requires java.desktop; // For cayley table image generation (BufferedImage, Graphics2D)
}
