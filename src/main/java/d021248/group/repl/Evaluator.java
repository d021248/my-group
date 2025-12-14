package d021248.group.repl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import d021248.group.Group;
import d021248.group.GroupFactory;
import d021248.group.api.Element;
import d021248.group.conjugacy.ConjugacyAnalyzer;
import d021248.group.cyclic.CyclicElement;
import d021248.group.dihedral.DihedralElement;
import d021248.group.subgroup.SpecialSubgroups;
import d021248.group.subgroup.Subgroup;
import d021248.group.subgroup.SubgroupAnalyzer;
import d021248.group.subgroup.SubgroupGenerator;
import d021248.group.viz.CayleyGraphViewer;
import d021248.group.viz.CayleyTableViewer;
import d021248.group.viz.SubgroupLatticeViewer;

/**
 * Evaluates parsed expressions in the context of group theory.
 */
public class Evaluator {

    private final ReplContext context;

    public Evaluator(ReplContext context) {
        this.context = context;
    }

    public Object evaluate(Expression expr) {
        return switch (expr) {
            case Expression.Literal lit -> lit.value();

            case Expression.Variable var -> {
                if (context.hasVariable(var.name())) {
                    yield context.getVariable(var.name());
                } else if (context.hasGroup(var.name())) {
                    yield context.getGroup(var.name());
                } else {
                    throw new EvaluationException("Undefined variable: " + var.name());
                }
            }

            case Expression.Assignment assign -> {
                Object value = evaluate(assign.value());

                // Store in appropriate context
                if (value instanceof Group<?>) {
                    context.setGroup(assign.variable(), (Group<?>) value);
                } else {
                    context.setVariable(assign.variable(), value);
                }

                yield value;
            }

            case Expression.BinaryOp binOp -> evaluateBinaryOp(binOp);

            case Expression.UnaryOp unOp -> evaluateUnaryOp(unOp);

            case Expression.FunctionCall func -> evaluateFunctionCall(func);

            case Expression.GroupCreation group -> createGroup(group);

            case Expression.PropertyAccess prop -> evaluatePropertyAccess(prop);
        };
    }

    private Object evaluateBinaryOp(Expression.BinaryOp binOp) {
        Object left = evaluate(binOp.left());
        Object right = evaluate(binOp.right());
        String op = binOp.operator();

        // Group element operations
        if (left instanceof Element && right instanceof Element) {
            return evaluateElementOperation((Element) left, op, (Element) right);
        }

        // Numeric operations
        if (left instanceof Integer && right instanceof Integer) {
            return evaluateNumericOp((Integer) left, op, (Integer) right);
        }

        throw new EvaluationException("Cannot apply operator " + op + " to " +
                left.getClass().getSimpleName() + " and " + right.getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    private Object evaluateElementOperation(Element left, String op, Element right) {
        // Find the group containing these elements
        Group<Element> group = null;

        for (Group<?> g : context.getGroups().values()) {
            if (g.elements().contains(left) && g.elements().contains(right)) {
                group = (Group<Element>) g;
                break;
            }
        }

        if (group == null) {
            throw new EvaluationException("Cannot find group containing elements");
        }

        return switch (op) {
            case "*", "+" -> group.operate(left, right);
            case "^" -> power(group, left, (Integer) evaluate(new Expression.Literal(right)));
            default -> throw new EvaluationException("Unknown operator for elements: " + op);
        };
    }

    private int evaluateNumericOp(int left, String op, int right) {
        return switch (op) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            case "%" -> left % right;
            case "^", "**" -> (int) Math.pow(left, right);
            default -> throw new EvaluationException("Unknown numeric operator: " + op);
        };
    }

    private Object evaluateUnaryOp(Expression.UnaryOp unOp) {
        Object operand = evaluate(unOp.operand());
        String op = unOp.operator();

        return switch (op) {
            case "-" -> {
                if (operand instanceof Integer) {
                    yield -(Integer) operand;
                }
                throw new EvaluationException("Cannot negate " + operand.getClass().getSimpleName());
            }
            default -> throw new EvaluationException("Unknown unary operator: " + op);
        };
    }

    @SuppressWarnings("unchecked")
    private Object evaluateFunctionCall(Expression.FunctionCall func) {
        List<Object> args = func.arguments().stream()
                .map(this::evaluate)
                .collect(Collectors.toList());

        return switch (func.name()) {
            case "inverse", "inv" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Element)) {
                    throw new EvaluationException("inverse() requires one Element argument");
                }
                Element elem = (Element) args.get(0);
                Group<Element> group = findGroupContaining(elem);
                yield group.inverse(elem);
            }

            case "order" -> {
                if (args.size() != 1) {
                    throw new EvaluationException("order() requires one argument");
                }
                if (args.get(0) instanceof Element) {
                    Element elem = (Element) args.get(0);
                    Group<Element> group = findGroupContaining(elem);
                    yield group.order(elem);
                } else if (args.get(0) instanceof Group<?>) {
                    yield ((Group<?>) args.get(0)).elements().size();
                }
                throw new EvaluationException("order() requires Element or Group");
            }

            case "identity", "id" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("identity() requires one Group argument");
                }
                yield ((Group<?>) args.get(0)).identity();
            }

            case "elements" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("elements() requires one Group argument");
                }
                yield ((Group<?>) args.get(0)).elements();
            }

            case "subgroups" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("subgroups() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield SubgroupGenerator.allSubgroups(group);
            }

            case "center" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("center() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield SpecialSubgroups.center(group);
            }

            case "isAbelian", "abelian" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("isAbelian() requires one Group argument");
                }
                Group<?> group = (Group<?>) args.get(0);
                yield isAbelian(group);
            }

            case "isCyclic", "cyclic" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("isCyclic() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                // Check if group has a single generator
                yield checkIfCyclic(group);
            }

            case "power", "pow" -> {
                if (args.size() != 2 || !(args.get(0) instanceof Element) || !(args.get(1) instanceof Integer)) {
                    throw new EvaluationException("power() requires (Element, Integer) arguments");
                }
                Element elem = (Element) args.get(0);
                int exp = (Integer) args.get(1);
                Group<Element> group = findGroupContaining(elem);
                yield power(group, elem, exp);
            }

            case "commutator" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("commutator() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield SpecialSubgroups.commutatorSubgroup(group);
            }

            case "frattini" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("frattini() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield SpecialSubgroups.frattiniSubgroup(group);
            }

            case "maximal" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("maximal() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield SpecialSubgroups.maximalSubgroups(group);
            }

            case "conjugacyClasses", "conjugacy" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("conjugacyClasses() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield ConjugacyAnalyzer.conjugacyClasses(group);
            }

            case "conjugate" -> {
                if (args.size() != 2 || !(args.get(0) instanceof Element) || !(args.get(1) instanceof Element)) {
                    throw new EvaluationException("conjugate(g, x) requires two Element arguments - computes xgx^-1");
                }
                Element g = (Element) args.get(0);
                Element x = (Element) args.get(1);
                Group<Element> group = findGroupContaining(g);
                yield ConjugacyAnalyzer.conjugate(group, g, x);
            }

            case "normalizer" -> {
                if (args.size() != 2 || !(args.get(0) instanceof Group<?>) || !(args.get(1) instanceof Subgroup<?>)) {
                    throw new EvaluationException("normalizer(G, H) requires Group and Subgroup arguments");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                Subgroup<Element> subgroup = (Subgroup<Element>) args.get(1);
                yield SubgroupAnalyzer.normalizer(group, subgroup);
            }

            case "centralizer" -> {
                if (args.size() == 2 && args.get(0) instanceof Group<?> && args.get(1) instanceof Subgroup<?>) {
                    Group<Element> group = (Group<Element>) args.get(0);
                    Subgroup<Element> subgroup = (Subgroup<Element>) args.get(1);
                    yield SubgroupAnalyzer.centralizer(group, subgroup);
                } else if (args.size() == 2 && args.get(0) instanceof Group<?> && args.get(1) instanceof Element) {
                    Group<Element> group = (Group<Element>) args.get(0);
                    Element element = (Element) args.get(1);
                    yield ConjugacyAnalyzer.elementCentralizer(group, element);
                } else {
                    throw new EvaluationException(
                            "centralizer(G, H) or centralizer(G, e) requires Group and (Subgroup or Element)");
                }
            }

            case "isNormal" -> {
                if (args.size() != 2 || !(args.get(0) instanceof Group<?>) || !(args.get(1) instanceof Subgroup<?>)) {
                    throw new EvaluationException("isNormal(G, H) requires Group and Subgroup arguments");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                Subgroup<Element> subgroup = (Subgroup<Element>) args.get(1);
                yield SubgroupAnalyzer.isNormal(group, subgroup);
            }

            case "generators" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("generators() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield findGenerators(group);
            }

            case "exponent" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("exponent() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                yield computeExponent(group);
            }

            case "classEquation" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("classEquation() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                Map<Integer, Long> equation = ConjugacyAnalyzer.classEquation(group);
                yield formatClassEquation(group, equation);
            }

            case "show", "cayleyTable" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("show() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                CayleyTableViewer.show(group, "Cayley Table");
                yield "Cayley table displayed in new window";
            }

            case "lattice", "subgroupLattice" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("lattice() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                SubgroupLatticeViewer.show(group, "Subgroup Lattice");
                yield "Subgroup lattice displayed in new window";
            }

            case "graph", "cayleyGraph" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("graph() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                CayleyGraphViewer.show(group, "Cayley Graph");
                yield "Cayley graph displayed in new window";
            }

            case "viz", "visualize" -> {
                if (args.size() != 1 || !(args.get(0) instanceof Group<?>)) {
                    throw new EvaluationException("viz() requires one Group argument");
                }
                Group<Element> group = (Group<Element>) args.get(0);
                CayleyTableViewer.show(group, "Cayley Table");
                SubgroupLatticeViewer.show(group, "Subgroup Lattice");
                CayleyGraphViewer.show(group, "Cayley Graph");
                yield "All visualizations displayed";
            }

            case "help" -> {
                yield getHelp(args.isEmpty() ? null : args.get(0).toString());
            }

            default -> throw new EvaluationException("Unknown function: " + func.name());
        };
    }

    private Object createGroup(Expression.GroupCreation groupExpr) {
        List<Object> params = groupExpr.parameters().stream()
                .map(this::evaluate)
                .collect(Collectors.toList());

        return switch (groupExpr.type()) {
            case "Z", "C" -> {
                if (params.size() != 1 || !(params.get(0) instanceof Integer)) {
                    throw new EvaluationException("Z(n) requires one integer argument");
                }
                yield GroupFactory.cyclic((Integer) params.get(0));
            }

            case "D" -> {
                if (params.size() != 1 || !(params.get(0) instanceof Integer)) {
                    throw new EvaluationException("D(n) requires one integer argument");
                }
                yield GroupFactory.dihedral((Integer) params.get(0));
            }

            case "S" -> {
                if (params.size() != 1 || !(params.get(0) instanceof Integer)) {
                    throw new EvaluationException("S(n) requires one integer argument");
                }
                yield GroupFactory.symmetric((Integer) params.get(0));
            }

            case "A" -> {
                if (params.size() != 1 || !(params.get(0) instanceof Integer)) {
                    throw new EvaluationException("A(n) requires one integer argument");
                }
                yield GroupFactory.alternating((Integer) params.get(0));
            }

            case "Product" -> {
                if (params.size() != 2 || !(params.get(0) instanceof Group<?>) ||
                        !(params.get(1) instanceof Group<?>)) {
                    throw new EvaluationException("Product(G, H) requires two group arguments");
                }
                @SuppressWarnings("unchecked")
                Group<Element> g1 = (Group<Element>) params.get(0);
                @SuppressWarnings("unchecked")
                Group<Element> g2 = (Group<Element>) params.get(1);
                yield GroupFactory.directProduct(g1, g2);
            }

            default -> throw new EvaluationException("Unknown group type: " + groupExpr.type());
        };
    }

    @SuppressWarnings("unchecked")
    private Object evaluatePropertyAccess(Expression.PropertyAccess prop) {
        Object obj = evaluate(prop.object());
        String property = prop.property();

        if (obj instanceof Group<?>) {
            Group<Element> group = (Group<Element>) obj;
            return switch (property) {
                case "order" -> group.elements().size();
                case "identity" -> group.identity();
                case "elements" -> group.elements();
                default -> throw new EvaluationException("Unknown group property: " + property);
            };
        } else if (obj instanceof CyclicElement) {
            CyclicElement elem = (CyclicElement) obj;
            return switch (property) {
                case "value" -> elem.value();
                case "modulus" -> elem.modulus();
                default -> throw new EvaluationException("Unknown cyclic element property: " + property);
            };
        } else if (obj instanceof DihedralElement) {
            DihedralElement elem = (DihedralElement) obj;
            return switch (property) {
                case "rotation" -> elem.rotation();
                case "flip" -> elem.flip();
                default -> throw new EvaluationException("Unknown dihedral element property: " + property);
            };
        }

        throw new EvaluationException("Cannot access property " + property +
                " on " + obj.getClass().getSimpleName());
    }

    @SuppressWarnings("unchecked")
    private <E extends Element> Group<E> findGroupContaining(E element) {
        for (Group<?> g : context.getGroups().values()) {
            if (g.elements().contains(element)) {
                return (Group<E>) g;
            }
        }
        throw new EvaluationException("Cannot find group containing element: " + element);
    }

    private <E extends Element> E power(Group<E> group, E element, int exp) {
        if (exp == 0) {
            return group.identity();
        }

        if (exp < 0) {
            element = group.inverse(element);
            exp = -exp;
        }

        E result = element;
        for (int i = 1; i < exp; i++) {
            result = group.operate(result, element);
        }

        return result;
    }

    private boolean isAbelian(Group<?> group) {
        Set<?> elements = group.elements();
        for (Object a : elements) {
            for (Object b : elements) {
                @SuppressWarnings("unchecked")
                Group<Element> g = (Group<Element>) group;
                Element ea = (Element) a;
                Element eb = (Element) b;
                if (!g.operate(ea, eb).equals(g.operate(eb, ea))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfCyclic(Group<Element> group) {
        // A group is cyclic if it can be generated by a single element
        for (Element g : group.elements()) {
            Set<Element> generated = new java.util.HashSet<>();
            Element current = group.identity();

            // Generate powers of g
            do {
                generated.add(current);
                current = group.operate(current, g);
            } while (!generated.contains(current) && generated.size() <= group.elements().size());

            if (generated.size() == group.elements().size()) {
                return true;
            }
        }
        return false;
    }

    private Set<Element> findGenerators(Group<Element> group) {
        Set<Element> generators = new java.util.HashSet<>();

        // Find minimal generating set by trying single elements first
        for (Element g : group.elements()) {
            Set<Element> generated = new java.util.HashSet<>();
            Element current = group.identity();

            do {
                generated.add(current);
                current = group.operate(current, g);
            } while (!generated.contains(current) && generated.size() <= group.elements().size());

            if (generated.size() == group.elements().size()) {
                generators.add(g);
            }
        }

        return generators;
    }

    private int computeExponent(Group<Element> group) {
        // Exponent is LCM of all element orders
        int exponent = 1;
        for (Element e : group.elements()) {
            int order = group.order(e);
            exponent = lcm(exponent, order);
        }
        return exponent;
    }

    private int lcm(int a, int b) {
        return (a * b) / gcd(a, b);
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private String formatClassEquation(Group<Element> group, Map<Integer, Long> equation) {
        StringBuilder sb = new StringBuilder();
        sb.append("|G| = ").append(group.order()).append(" = ");

        boolean first = true;
        for (Map.Entry<Integer, Long> entry : equation.entrySet()) {
            if (!first)
                sb.append(" + ");
            int classSize = entry.getKey();
            long count = entry.getValue();
            if (count == 1) {
                sb.append(classSize);
            } else {
                sb.append(count).append("×").append(classSize);
            }
            first = false;
        }

        return sb.toString();
    }

    private String getHelp(String topic) {
        if (topic == null) {
            return """
                    Available commands:

                      GROUP CREATION:
                        Z(n), D(n), S(n), A(n)          - Cyclic, Dihedral, Symmetric, Alternating groups
                        Product(g1, g2)                  - Direct product of two groups

                      GROUP ANALYSIS:
                        isAbelian(g), isCyclic(g)        - Check group properties
                        order(g), exponent(g)            - Order and exponent of group
                        generators(g)                    - Find all generators
                        elements(g), identity(g)         - Access group elements

                      SUBGROUPS:
                        subgroups(g)                     - All subgroups
                        center(g)                        - Center Z(G)
                        commutator(g)                    - Commutator subgroup [G,G]
                        frattini(g)                      - Frattini subgroup Φ(G)
                        maximal(g)                       - Maximal subgroups
                        normalizer(g, h)                 - Normalizer of subgroup
                        centralizer(g, h/e)              - Centralizer of subgroup or element
                        isNormal(g, h)                   - Check if H is normal in G

                      CONJUGACY:
                        conjugacyClasses(g)              - All conjugacy classes
                        conjugate(g, x)                  - Compute xgx⁻¹
                        classEquation(g)                 - Class equation

                      ELEMENT OPERATIONS:
                        inverse(e), order(e)             - Element inverse and order
                        power(e, n)                      - Compute e^n

                      VISUALIZATIONS:
                        show(g)                          - Cayley table
                        lattice(g)                       - Subgroup lattice
                        graph(g)                         - Cayley graph
                        viz(g)                           - All visualizations

                      PROPERTIES:
                        g.order, g.identity, g.elements  - Group properties
                        e.value, e.modulus               - Element properties

                      SPECIAL:
                        help()                           - This help message
                        vars, groups                     - List variables/groups
                        clear                            - Clear all variables
                        verbose on/off                   - Toggle verbose mode
                        quit, exit                       - Exit REPL
                    """;
        }

        return "No specific help available for: " + topic;
    }

    public static class EvaluationException extends RuntimeException {
        public EvaluationException(String message) {
            super(message);
        }
    }
}
