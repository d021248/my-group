package d021248.group.repl;

/**
 * Represents a parsed expression in the REPL.
 */
public sealed interface Expression {

    /** Literal value (element, number, string) */
    record Literal(Object value) implements Expression {
    }

    /** Variable reference */
    record Variable(String name) implements Expression {
    }

    /** Binary operation (a * b, a + b, etc.) */
    record BinaryOp(Expression left, String operator, Expression right) implements Expression {
    }

    /** Unary operation (inverse(a), order(a), etc.) */
    record UnaryOp(String operator, Expression operand) implements Expression {
    }

    /** Function call: name(args...) */
    record FunctionCall(String name, java.util.List<Expression> arguments) implements Expression {
    }

    /** Assignment: var = expr */
    record Assignment(String variable, Expression value) implements Expression {
    }

    /** Group creation: Z(6), S(4), D(5), etc. */
    record GroupCreation(String type, java.util.List<Expression> parameters) implements Expression {
    }

    /** Property access: group.order, element.value, etc. */
    record PropertyAccess(Expression object, String property) implements Expression {
    }
}
