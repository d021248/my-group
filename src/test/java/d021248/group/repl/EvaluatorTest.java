package d021248.group.repl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import d021248.group.Group;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;

class EvaluatorTest {
    
    private ReplContext context;
    private Evaluator evaluator;
    private Tokenizer tokenizer;
    
    @BeforeEach
    void setUp() {
        context = new ReplContext();
        evaluator = new Evaluator(context);
        tokenizer = new Tokenizer();
    }
    
    private Object eval(String input) {
        var tokens = tokenizer.tokenize(input);
        var parser = new Parser(tokens);
        var expr = parser.parse();
        return evaluator.evaluate(expr);
    }
    
    @Test
    void testGroupCreation() {
        Object result = eval("Z(6)");
        assertInstanceOf(CyclicGroup.class, result);
        assertEquals(6, ((Group<?>) result).elements().size());
    }
    
    @Test
    void testAssignment() {
        eval("g = Z(6)");
        assertTrue(context.hasGroup("g"));
        assertEquals(6, context.getGroup("g").elements().size());
    }
    
    @Test
    void testGroupOrder() {
        eval("g = Z(6)");
        Object result = eval("g.order");
        assertEquals(6, result);
    }
    
    @Test
    void testOrderFunction() {
        eval("g = Z(6)");
        Object result = eval("order(g)");
        assertEquals(6, result);
    }
    
    @Test
    void testIsAbelian() {
        eval("g = Z(6)");
        Object result = eval("isAbelian(g)");
        assertEquals(true, result);
    }
    
    @Test
    void testIsCyclic() {
        eval("g = Z(6)");
        Object result = eval("isCyclic(g)");
        assertEquals(true, result);
    }
    
    @Test
    void testDihedralNonAbelian() {
        eval("h = D(4)");
        Object result = eval("isAbelian(h)");
        assertEquals(false, result);
    }
    
    @Test
    void testIdentity() {
        eval("g = Z(6)");
        Object result = eval("identity(g)");
        assertInstanceOf(CyclicElement.class, result);
        assertEquals(new CyclicElement(0, 6), result);
    }
    
    @Test
    void testElements() {
        eval("g = Z(3)");
        Object result = eval("elements(g)");
        assertInstanceOf(java.util.Set.class, result);
        assertEquals(3, ((java.util.Set<?>) result).size());
    }
    
    @Test
    void testNumericOperations() {
        assertEquals(7, eval("3 + 4"));
        assertEquals(12, eval("3 * 4"));
        assertEquals(8, eval("2 ^ 3"));
        assertEquals(1, eval("5 % 2"));
    }
    
    @Test
    void testMultipleGroups() {
        eval("g1 = Z(4)");
        eval("g2 = D(3)");
        
        assertTrue(context.hasGroup("g1"));
        assertTrue(context.hasGroup("g2"));
        assertEquals(4, context.getGroup("g1").elements().size());
        assertEquals(6, context.getGroup("g2").elements().size());
    }
    
    @Test
    void testCenter() {
        eval("g = Z(6)");
        Object result = eval("center(g)");
        assertNotNull(result);
    }
    
    @Test
    void testSubgroups() {
        eval("g = Z(6)");
        Object result = eval("subgroups(g)");
        assertInstanceOf(java.util.List.class, result);
        assertTrue(((java.util.List<?>) result).size() > 0);
    }
}
