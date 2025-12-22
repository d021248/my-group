package d021248.group.repl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenizes input strings for the REPL parser.
 */
public class Tokenizer {
    
    public enum TokenType {
        NUMBER, IDENTIFIER, OPERATOR, LPAREN, RPAREN, LBRACKET, RBRACKET, 
        COMMA, DOT, EQUALS, SEMICOLON, STRING, EOF
    }
    
    public record Token(TokenType type, String value, int position) {
        @Override
        public String toString() {
            return type + "(" + value + ")";
        }
    }
    
    private static final Pattern TOKEN_PATTERN = Pattern.compile(
        "\\s*(" +
        "\\d+|" +                           // numbers
        "[a-zA-Z_][a-zA-Z0-9_]*|" +        // identifiers
        "==|!=|<=|>=|<|>|" +               // comparison operators
        "\\*\\*|\\*|\\+|-|/|%|\\^|" +     // arithmetic operators
        "\\(|\\)|\\[|\\]|" +               // parentheses and brackets
        ",|\\.|=|;|" +                     // punctuation
        "\"[^\"]*\"|" +                    // strings
        "'[^']*'" +                        // strings with single quotes
        ")\\s*"
    );
    
    public List<Token> tokenize(String input) {
        List<Token> tokens = new ArrayList<>();
        Matcher matcher = TOKEN_PATTERN.matcher(input);
        int position = 0;
        
        while (position < input.length()) {
            if (!matcher.find(position)) {
                throw new ParseException("Invalid token at position " + position + ": " + input.substring(position));
            }
            
            if (matcher.start() != position) {
                throw new ParseException("Invalid token at position " + position);
            }
            
            String value = matcher.group(1);
            TokenType type = determineType(value);
            tokens.add(new Token(type, value, position));
            position = matcher.end();
        }
        
        tokens.add(new Token(TokenType.EOF, "", position));
        return tokens;
    }
    
    private TokenType determineType(String value) {
        if (value.matches("\\d+")) {
            return TokenType.NUMBER;
        } else if (value.matches("[a-zA-Z_][a-zA-Z0-9_]*")) {
            return TokenType.IDENTIFIER;
        } else if (value.equals("(")) {
            return TokenType.LPAREN;
        } else if (value.equals(")")) {
            return TokenType.RPAREN;
        } else if (value.equals("[")) {
            return TokenType.LBRACKET;
        } else if (value.equals("]")) {
            return TokenType.RBRACKET;
        } else if (value.equals(",")) {
            return TokenType.COMMA;
        } else if (value.equals(".")) {
            return TokenType.DOT;
        } else if (value.equals("=")) {
            return TokenType.EQUALS;
        } else if (value.equals(";")) {
            return TokenType.SEMICOLON;
        } else if (value.startsWith("\"") || value.startsWith("'")) {
            return TokenType.STRING;
        } else {
            return TokenType.OPERATOR;
        }
    }
    
    public static class ParseException extends RuntimeException {
        public ParseException(String message) {
            super(message);
        }
    }
}
