package d021248.group.repl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import d021248.group.Group;
import d021248.group.GroupFactory;
import d021248.group.api.Element;
import d021248.group.cyclic.CyclicElement;
import d021248.group.dihedral.DihedralElement;
import d021248.group.subgroup.SpecialSubgroups;
import d021248.group.subgroup.SubgroupGenerator;

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

    private String getHelp(String topic) {
        if (topic == null) {
            return """
                    Available commands:
                      Group creation: Z(n), D(n), S(n), A(n)
                      Operations: *, +, ^
                      Functions: inverse(e), order(e/g), identity(g), elements(g)
                                subgroups(g), center(g), isAbelian(g), isCyclic(g)
                      Properties: g.order, g.identity, e.value
                      Special: help(), clear, vars, groups, quit
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
