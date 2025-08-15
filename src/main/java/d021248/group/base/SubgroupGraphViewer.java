
package d021248.group.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import d021248.group.api.Element;
import d021248.group.api.Group;
import d021248.group.api.Operation;

public class SubgroupGraphViewer<T extends Element> {
    // Removed unused subgroups field
    private final Group<T> group;

    // Recursively collect all subgroups for every subgroup
    private List<Set<T>> collectAllSubgroups(Group<T> group) {
        java.util.Set<String> seen = new java.util.HashSet<>();
        java.util.List<Set<T>> all = new java.util.ArrayList<>();
        java.util.List<Set<T>> queue = new java.util.ArrayList<>(new Subgroup<>(group).getSubgroups());
        while (!queue.isEmpty()) {
            Set<T> sg = queue.remove(0);
            String canonical = sg.stream().map(Object::toString).sorted().reduce("", (a, b) -> a + "," + b);
            if (!seen.contains(canonical)) {
                seen.add(canonical);
                all.add(sg);
                if (sg.size() > 1 && sg.size() < group.elements().size()) {
                    Group<T> subgroup = new Group<T>() {
                        @Override
                        public Set<T> elements() {
                            return sg;
                        }

                        @Override
                        public Operation<T> operation() {
                            return group.operation();
                        }
                    };
                    for (Set<T> subsg : new Subgroup<>(subgroup).getSubgroups()) {
                        String subCanonical = subsg.stream().map(Object::toString).sorted().reduce("",
                                (a, b) -> a + "," + b);
                        if (!seen.contains(subCanonical)) {
                            queue.add(subsg);
                        }
                    }
                }
            }
        }
        return all;
    }

    public SubgroupGraphViewer(Group<T> group) {
        this.group = group;
        // Removed assignment to subgroups; only storing group
    }

    private String subgroupId(Set<T> subgroup, int index) {
        // Guaranteed unique id: order + index
        return "sg_" + subgroup.size() + "_" + index;
    }

    private String formatElementsGrid(Set<T> subgroup) {
        int n = subgroup.size();
        int cols = (int) Math.ceil(Math.sqrt(n));
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        int i = 0;
        for (T el : subgroup) {
            sb.append(el);
            i++;
            if (i % cols == 0 && i < n) {
                sb.append(";\n "); // new line for grid
            } else if (i < n) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public void showGraph() {
        Graph graph = new SingleGraph("Subgroup Inclusion Graph");
        java.util.List<Set<T>> allSubgroups = collectAllSubgroups(this.group);
        java.util.Map<Set<T>, String> subgroupIds = buildNodes(graph, allSubgroups);
        buildEdges(graph, allSubgroups, subgroupIds);
        styleGraph(graph);
        Viewer viewer = graph.display(false); // disable auto layout
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }

    private Map<Set<T>, String> buildNodes(Graph graph, List<Set<T>> allSubgroups) {
        Map<Integer, List<Set<T>>> levels = new TreeMap<>(Collections.reverseOrder());
        for (Set<T> sg : allSubgroups) {
            levels.computeIfAbsent(sg.size(), k -> new ArrayList<>()).add(sg);
        }
        int yStep = 120;
        int graphWidth = 1200;
        int numLevels = levels.size();
        int levelIdx = 0;
        Map<Set<T>, String> subgroupIds = new HashMap<>();
        int globalIndex = 0;
        for (Map.Entry<Integer, List<Set<T>>> entry : levels.entrySet()) {
            List<Set<T>> levelSubgroups = entry.getValue();
            int count = levelSubgroups.size();
            int y = 80 + (numLevels - levelIdx - 1) * yStep;
            double xStep = count > 1 ? (double) graphWidth / (count + 1) : graphWidth / 2.0;
            for (int j = 0; j < count; j++) {
                Set<T> sg = levelSubgroups.get(j);
                String id = subgroupId(sg, globalIndex);
                subgroupIds.put(sg, id);
                org.graphstream.graph.Node node = graph.addNode(id);
                String label = "|" + sg.size() + "| " + formatElementsGrid(sg);
                node.setAttribute("ui.label", label);
                double x = (j + 1) * xStep;
                double yOffset = (j % 2 == 0) ? 0 : 12;
                node.setAttribute("xyz", x, y + yOffset, 0);
                globalIndex++;
            }
            levelIdx++;
        }
        return subgroupIds;
    }

    private void buildEdges(Graph graph, List<Set<T>> allSubgroups,
            Map<Set<T>, String> subgroupIds) {
        for (Set<T> a : allSubgroups) {
            for (Set<T> b : allSubgroups) {
                if (a == b || a.isEmpty() || b.isEmpty())
                    continue;
                if (b.size() >= a.size())
                    continue;
                if (!a.containsAll(b))
                    continue;
                if (hasIntermediate(allSubgroups, a, b))
                    continue;
                String fromId = subgroupIds.get(b);
                String toId = subgroupIds.get(a);
                String edgeId = fromId + "_to_" + toId;
                if (graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, fromId, toId, true);
                }
            }
        }
    }

    private boolean hasIntermediate(List<Set<T>> allSubgroups, Set<T> a, Set<T> b) {
        for (Set<T> c : allSubgroups) {
            if (c == a || c == b)
                continue;
            if (c.size() >= a.size() || c.size() <= b.size())
                continue;
            if (a.containsAll(c) && c.containsAll(b)) {
                return true;
            }
        }
        return false;
    }

    private void styleGraph(Graph graph) {
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: #e3f2fd; stroke-mode: plain; stroke-color: #1976d2; size: 60px, 40px; text-size: 14; text-alignment: center; text-background-mode: rounded-box; text-background-color: #ffffff; text-padding: 6px; text-offset: 0px, 0px; } "
                        +
                        "edge { fill-color: #90caf9; arrow-shape: arrow; arrow-size: 12px, 8px; } ");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
    }

    public static <T extends Element> void show(Group<T> group) {
        new SubgroupGraphViewer<>(group).showGraph();
    }
}
