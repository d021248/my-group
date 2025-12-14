gpackage d021248.group.repl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import d021248.group.Group;
import d021248.group.api.Element;
import d021248.group.repl.Tokenizer.Token;

/**
 * Interactive REPL (Read-Eval-Print Loop) for group theory computations.
 * <p>
 * Example session:
 * 
 * <pre>
 * group> g = Z(6)
 * Z₆ (Cyclic group of order 6)
 * 
 * group> g.order
 * 6
 * 
 * group> a = 2
 * 2
 * 
 * group> b = 3
 * 3
 * 
 * group> order(a)
 * 3
 * 
 * group> isAbelian(g)
 * true
 * </pre>
 * </p>
 */
public class GroupREPL {

    private final ReplContext context = new ReplContext();
    private final Evaluator evaluator = new Evaluator(context);
    private final Tokenizer tokenizer = new Tokenizer();
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private boolean running = true;

    public static void main(String[] args) {
        new GroupREPL().run();
    }

    public void run() {
        printWelcome();

        while (running) {
            try {
                System.out.print("group> ");
                String line = reader.readLine();

                if (line == null || line.trim().isEmpty()) {
                    continue;
                }

                line = line.trim();

                // Handle special commands
                if (handleSpecialCommand(line)) {
                    continue;
                }

                // Parse and evaluate
                Object result = evaluateExpression(line);

                // Print result
                if (result != null) {
                    printResult(result);
                }

            } catch (IOException e) {
                System.err.println("IO Error: " + e.getMessage());
                break;
            } catch (Tokenizer.ParseException | Evaluator.EvaluationException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                if (context.isVerbose()) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("\nGoodbye!");
    }

    private void printWelcome() {
        System.out.println("╔═══════════════════════════════════════════════════════════════╗");
        System.out.println("║        Group Theory REPL - Computer Algebra System            ║");
        System.out.println("║                     Version 1.0                               ║");
        System.out.println("╚═══════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Welcome to the Group Theory Interactive Shell!");
        System.out.println("Type 'help()' for available commands or 'quit' to exit.");
        System.out.println();
        System.out.println("Quick examples:");
        System.out.println("  g = Z(6)          - Create cyclic group Z₆");
        System.out.println("  h = D(4)          - Create dihedral group D₄");
        System.out.println("  g.order           - Get group order");
        System.out.println("  isAbelian(g)      - Check if group is abelian");
        System.out.println("  subgroups(g)      - List all subgroups");
        System.out.println();
    }

    private boolean handleSpecialCommand(String line) {
        return switch (line.toLowerCase()) {
            case "quit", "exit", "q" -> {
                running = false;
                yield true;
            }

            case "clear" -> {
                context.clear();
                System.out.println("All variables and groups cleared.");
                yield true;
            }

            case "vars", "variables" -> {
                printVariables();
                yield true;
            }

            case "groups" -> {
                printGroups();
                yield true;
            }

            case "verbose on" -> {
                context.setVerbose(true);
                System.out.println("Verbose mode enabled.");
                yield true;
            }

            case "verbose off" -> {
                context.setVerbose(false);
                System.out.println("Verbose mode disabled.");
                yield true;
            }

            default -> false;
        };
    }

    private Object evaluateExpression(String input) {
        // Tokenize
        List<Token> tokens = tokenizer.tokenize(input);

        if (context.isVerbose()) {
            System.out.println("Tokens: " + tokens);
        }

        // Parse
        Parser parser = new Parser(tokens);
        Expression expr = parser.parse();

        if (context.isVerbose()) {
            System.out.println("Parsed: " + expr);
        }

        // Evaluate
        return evaluator.evaluate(expr);
    }

    private void printResult(Object result) {
        if (result instanceof Group<?>) {
            printGroup((Group<?>) result);
        } else if (result instanceof Element) {
            System.out.println(formatElement((Element) result));
        } else if (result instanceof java.util.Set<?>) {
            printSet((java.util.Set<?>) result);
        } else if (result instanceof List<?>) {
            printList((List<?>) result);
        } else if (result instanceof Boolean) {
            System.out.println((Boolean) result ? "true" : "false");
        } else if (result instanceof String) {
            System.out.println(result);
        } else {
            System.out.println(result);
        }
        System.out.println();
    }

    private void printGroup(Group<?> group) {
        String name = getGroupName(group);
        int order = group.elements().size();
        System.out.println(name + " (order " + order + ")");
    }

    private String getGroupName(Group<?> group) {
        String className = group.getClass().getSimpleName();
        return switch (className) {
            case "CyclicGroup" -> "Z₍" + group.elements().size() + "₎";
            case "DihedralGroup" -> "D₍" + (group.elements().size() / 2) + "₎";
            case "SymmetricGroup" -> {
                int n = (int) Math.round(factorial(group.elements().size()));
                yield "S₍" + n + "₎";
            }
            case "AlternatingGroup" -> {
                int n = (int) Math.round(factorial(group.elements().size()) * 2);
                yield "A₍" + n + "₎";
            }
            default -> className;
        };
    }

    private int factorial(int n) {
        int result = 1;
        int fact = 1;
        while (fact < 10 && factorial(fact) != n) {
            fact++;
        }
        return fact;
    }

    private int factorial2(int n) {
        if (n <= 1)
            return 1;
        return n * factorial2(n - 1);
    }

    private String formatElement(Element element) {
        return element.toString();
    }

    private void printSet(java.util.Set<?> set) {
        if (set.isEmpty()) {
            System.out.println("∅ (empty set)");
            return;
        }

        System.out.println("{ " + String.join(", ",
                set.stream()
                        .limit(20)
                        .map(Object::toString)
                        .toArray(String[]::new))
                +
                (set.size() > 20 ? ", ... (" + set.size() + " total)" : "") + " }");
    }

    private void printList(List<?> list) {
        if (list.isEmpty()) {
            System.out.println("[ ]");
            return;
        }

        System.out.println("[ " + String.join(", ",
                list.stream()
                        .limit(20)
                        .map(Object::toString)
                        .toArray(String[]::new))
                +
                (list.size() > 20 ? ", ... (" + list.size() + " total)" : "") + " ]");
    }

    private void printVariables() {
        if (context.getVariables().isEmpty()) {
            System.out.println("No variables defined.");
            return;
        }

        System.out.println("Variables:");
        context.getVariables().forEach((name, value) -> {
            System.out.println("  " + name + " = " + value);
        });
    }

    private void printGroups() {
        if (context.getGroups().isEmpty()) {
            System.out.println("No groups defined.");
            return;
        }

        System.out.println("Groups:");
        context.getGroups().forEach((name, group) -> {
            System.out.println("  " + name + " = " + getGroupName(group) +
                    " (order " + group.elements().size() + ")");
        });
    }
}
