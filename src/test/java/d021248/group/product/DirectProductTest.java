package d021248.group.product;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import d021248.group.FiniteGroup;
import d021248.group.GroupFactory;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.util.GroupVerifier;

class DirectProductTest {

    @Test
    @DisplayName("DirectProduct of Z_2 × Z_3 has order 6")
    void orderIsProduct() {
        CyclicGroup z2 = GroupFactory.cyclic(2);
        CyclicGroup z3 = GroupFactory.cyclic(3);
        DirectProduct<CyclicElement, CyclicElement> product = new DirectProduct<>(z2, z3);
        assertEquals(6, product.order());
    }

    @Test
    @DisplayName("Identity is (e1, e2)")
    void identityIsProductOfIdentities() {
        CyclicGroup z2 = GroupFactory.cyclic(2);
        CyclicGroup z3 = GroupFactory.cyclic(3);
        DirectProduct<CyclicElement, CyclicElement> product = new DirectProduct<>(z2, z3);
        ProductElement<CyclicElement, CyclicElement> id = product.identity();
        assertEquals(z2.identity(), id.first());
        assertEquals(z3.identity(), id.second());
    }

    @Test
    @DisplayName("Operation is component-wise")
    void operationIsComponentWise() {
        CyclicGroup z3 = GroupFactory.cyclic(3);
        CyclicGroup z4 = GroupFactory.cyclic(4);
        DirectProduct<CyclicElement, CyclicElement> product = new DirectProduct<>(z3, z4);
        ProductElement<CyclicElement, CyclicElement> a = new ProductElement<>(new CyclicElement(1, 3),
                new CyclicElement(2, 4));
        ProductElement<CyclicElement, CyclicElement> b = new ProductElement<>(new CyclicElement(2, 3),
                new CyclicElement(3, 4));
        ProductElement<CyclicElement, CyclicElement> result = product.operate(a, b);
        assertEquals(new CyclicElement(0, 3), result.first()); // 1+2 mod 3
        assertEquals(new CyclicElement(1, 4), result.second()); // 2+3 mod 4
    }

    @Test
    @DisplayName("DirectProduct satisfies group axioms")
    void satisfiesGroupAxioms() {
        CyclicGroup z2 = GroupFactory.cyclic(2);
        CyclicGroup z3 = GroupFactory.cyclic(3);
        FiniteGroup<ProductElement<CyclicElement, CyclicElement>> product = new DirectProduct<>(z2, z3);
        var result = GroupVerifier.verify(product);
        assertTrue(result.ok(), result.summary());
    }

    @Test
    @DisplayName("Inverse is component-wise")
    void inverseIsComponentWise() {
        CyclicGroup z4 = GroupFactory.cyclic(4);
        CyclicGroup z5 = GroupFactory.cyclic(5);
        DirectProduct<CyclicElement, CyclicElement> product = new DirectProduct<>(z4, z5);
        ProductElement<CyclicElement, CyclicElement> a = new ProductElement<>(new CyclicElement(3, 4),
                new CyclicElement(2, 5));
        ProductElement<CyclicElement, CyclicElement> inv = product.inverse(a);
        assertEquals(new CyclicElement(1, 4), inv.first()); // inverse of 3 mod 4
        assertEquals(new CyclicElement(3, 5), inv.second()); // inverse of 2 mod 5
    }

    @Test
    @DisplayName("Z_2 × Z_2 Klein four-group")
    void kleinFourGroup() {
        CyclicGroup z2a = GroupFactory.cyclic(2);
        CyclicGroup z2b = GroupFactory.cyclic(2);
        DirectProduct<CyclicElement, CyclicElement> v4 = new DirectProduct<>(z2a, z2b);
        assertEquals(4, v4.order());
        // All non-identity elements have order 2
        for (var e : v4.elements()) {
            if (!e.equals(v4.identity())) {
                var doubled = v4.operate(e, e);
                assertEquals(v4.identity(), doubled);
            }
        }
    }

    @Test
    @DisplayName("Access to component groups")
    void componentAccess() {
        CyclicGroup z5 = GroupFactory.cyclic(5);
        CyclicGroup z7 = GroupFactory.cyclic(7);
        DirectProduct<CyclicElement, CyclicElement> product = new DirectProduct<>(z5, z7);
        assertNotNull(product.firstGroup());
        assertNotNull(product.secondGroup());
        assertEquals(5, product.firstGroup().order());
        assertEquals(7, product.secondGroup().order());
    }
}
