package d021248.group.repl;

import java.util.ArrayList;
import java.util.List;

import d021248.group.repl.Tokenizer.Token;
import d021248.group.repl.Tokenizer.TokenType;

/**
 * Recursive descent parser for REPL expressions.
 * <p>
 * Grammar:
 * 
 * <pre>
 * statement    := assignment | expression
 * assignment   := IDENTIFIER = expression
 * expression   := term ((+|-) term)*
 * term         := factor ((*|/) factor)*
 * factor       := power (^ power)*
 * power        := unary | primary
 * unary        := IDENTIFIER(expression) | primary
 * primary      := NUMBER | IDENTIFIER | group_creation | (expression) | property_access
 * group_creation := IDENTIFIER(arg_list)
 * property_access := primary.IDENTIFIER
 * </pre>
 * </p>
 */
public class Parser {

    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Expression parse() {
        Expression expr = statement();
        if (!isAtEnd()) {
            throw new Tokenizer.ParseException("Unexpected tokens after expression");
        }
        return expr;
    }

    private Expression statement() {
        // Check for assignment: identifier = expression
        if (check(TokenType.IDENTIFIER) && peekAhead(1).type() == TokenType.EQUALS) {
            String varName = advance().value();
            consume(TokenType.EQUALS, "Expected '='");
            Expression value = expression();
            return new Expression.Assignment(varName, value);
        }

        return expression();
    }

    private Expression expression() {
        return additive();
    }

    private Expression additive() {
        Expression left = multiplicative();

        while (match("+", "-")) {
            String operator = previous().value();
            Expression right = multiplicative();
            left = new Expression.BinaryOp(left, operator, right);
        }

        return left;
    }

    private Expression multiplicative() {
        Expression left = power();

        while (match("*", "/", "%")) {
            String operator = previous().value();
            Expression right = power();
            left = new Expression.BinaryOp(left, operator, right);
        }

        return left;
    }

    private Expression power() {
        Expression left = unary();

        if (match("^", "**")) {
            String operator = previous().value();
            Expression right = power(); // Right associative
            return new Expression.BinaryOp(left, operator, right);
        }

        return left;
    }

    private Expression unary() {
        // Check for function calls/group creation like inverse(a), order(a), Z(6)
        if (check(TokenType.IDENTIFIER) && peekAhead(1).type() == TokenType.LPAREN) {
            String name = advance().value();

            // Determine if it's a group type first
            if (isGroupType(name)) {
                consume(TokenType.LPAREN, "Expected '(' after group type");
                List<Expression> args = argumentList();
                consume(TokenType.RPAREN, "Expected ')' after arguments");
                return new Expression.GroupCreation(name, args);
            } else {
                // It's a function call
                return functionCall(name);
            }
        }

        return postfix();
    }

    private Expression postfix() {
        Expression expr = primary();

        // Handle property access: expr.property
        while (match(TokenType.DOT)) {
            if (!check(TokenType.IDENTIFIER)) {
                throw new Tokenizer.ParseException("Expected property name after '.'");
            }
            String property = advance().value();
            expr = new Expression.PropertyAccess(expr, property);
        }

        return expr;
    }

    private Expression primary() {
        // Number
        if (check(TokenType.NUMBER)) {
            int value = Integer.parseInt(advance().value());
            return new Expression.Literal(value);
        }

        // String
        if (check(TokenType.STRING)) {
            String value = advance().value();
            // Remove quotes
            value = value.substring(1, value.length() - 1);
            return new Expression.Literal(value);
        }

        // Group creation or function call: Z(6), S(4), inverse(a)
        if (check(TokenType.IDENTIFIER)) {
            String name = advance().value();

            if (match("(")) {
                // Could be group creation or function call
                List<Expression> args = argumentList();
                consume(TokenType.RPAREN, "Expected ')' after arguments");

                // Determine if it's a known group type
                if (isGroupType(name)) {
                    return new Expression.GroupCreation(name, args);
                } else {
                    return new Expression.FunctionCall(name, args);
                }
            }

            // Just a variable reference
            return new Expression.Variable(name);
        }

        // Parenthesized expression
        if (match("(")) {
            Expression expr = expression();
            consume(TokenType.RPAREN, "Expected ')' after expression");
            return expr;
        }

        throw new Tokenizer.ParseException("Expected expression at position " + current);
    }

    private Expression functionCall(String name) {
        consume(TokenType.LPAREN, "Expected '(' after function name");
        List<Expression> args = argumentList();
        consume(TokenType.RPAREN, "Expected ')' after arguments");
        return new Expression.FunctionCall(name, args);
    }

    private List<Expression> argumentList() {
        List<Expression> args = new ArrayList<>();

        if (!check(TokenType.RPAREN)) {
            do {
                args.add(expression());
            } while (match(","));
        }

        return args;
    }

    private boolean isGroupType(String name) {
        return name.equals("Z") || name.equals("C") || // Cyclic
                name.equals("D") || // Dihedral
                name.equals("S") || // Symmetric
                name.equals("A") || // Alternating
                name.equals("Product") || // Direct product
                name.equals("Quotient"); // Quotient group
    }

    private boolean match(String... operators) {
        for (String op : operators) {
            if (check(TokenType.OPERATOR) && peek().value().equals(op)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private boolean match(TokenType type) {
        if (check(type)) {
            advance();
            return true;
        }
        return false;
    }

    private boolean check(TokenType type) {
        if (isAtEnd())
            return false;
        return peek().type() == type;
    }

    private Token advance() {
        if (!isAtEnd())
            current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token peekAhead(int offset) {
        int pos = current + offset;
        if (pos >= tokens.size()) {
            return tokens.get(tokens.size() - 1); // EOF
        }
        return tokens.get(pos);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private void consume(TokenType type, String message) {
        if (check(type)) {
            advance();
            return;
        }
        throw new Tokenizer.ParseException(message + " at position " + current);
    }
}
