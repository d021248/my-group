/**
 * REPL (Read-Eval-Print Loop) implementation for interactive group theory
 * computations.
 * <p>
 * This package provides a computer algebra system interface for exploring group
 * theory
 * through an interactive command-line interface. Users can create groups,
 * perform
 * operations on elements, compute subgroups, and analyze group properties.
 * </p>
 * 
 * <h2>Main Components:</h2>
 * <ul>
 * <li>{@link d021248.group.repl.GroupREPL} - Main REPL interface</li>
 * <li>{@link d021248.group.repl.Parser} - Expression parser</li>
 * <li>{@link d021248.group.repl.Evaluator} - Expression evaluator</li>
 * <li>{@link d021248.group.repl.ReplContext} - Session state manager</li>
 * </ul>
 * 
 * <h2>Example Usage:</h2>
 * 
 * <pre>
 * group&gt; g = Z(6)
 * Z₍6₎ (order 6)
 * 
 * group&gt; isAbelian(g)
 * true
 * 
 * group&gt; subgroups(g)
 * [ ... ]
 * </pre>
 */
package d021248.group.repl;
