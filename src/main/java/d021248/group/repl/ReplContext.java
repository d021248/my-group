package d021248.group.repl;

import java.util.HashMap;
import java.util.Map;

import d021248.group.Group;

/**
 * Manages the state of the REPL session including variables, groups, and
 * settings.
 */
public class ReplContext {
    private final Map<String, Object> variables = new HashMap<>();
    private final Map<String, Group<?>> groups = new HashMap<>();
    private boolean verbose = false;
    private boolean showSteps = false;

    public void setVariable(String name, Object value) {
        variables.put(name, value);
    }

    public Object getVariable(String name) {
        return variables.get(name);
    }

    public boolean hasVariable(String name) {
        return variables.containsKey(name);
    }

    public void setGroup(String name, Group<?> group) {
        groups.put(name, group);
    }

    public Group<?> getGroup(String name) {
        return groups.get(name);
    }

    public boolean hasGroup(String name) {
        return groups.containsKey(name);
    }

    public Map<String, Object> getVariables() {
        return new HashMap<>(variables);
    }

    public Map<String, Group<?>> getGroups() {
        return new HashMap<>(groups);
    }

    public void clear() {
        variables.clear();
        groups.clear();
    }

    public void clearVariables() {
        variables.clear();
    }

    public void clearGroups() {
        groups.clear();
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public boolean isShowSteps() {
        return showSteps;
    }

    public void setShowSteps(boolean showSteps) {
        this.showSteps = showSteps;
    }
}
