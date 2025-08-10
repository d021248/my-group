
package d021248.group.base;

import java.util.List;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import d021248.group.api.Element;
import d021248.group.api.Group;

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
                        public d021248.group.api.Operation<T> operation() {
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
        // Recursively collect all subgroups for every subgroup using the original group
        java.util.List<Set<T>> allSubgroups = collectAllSubgroups(this.group);
        // Layout nodes by order: highest at top, lowest at bottom
        java.util.Map<Integer, java.util.List<Set<T>>> levels = new java.util.TreeMap<>(
                java.util.Collections.reverseOrder());
        for (Set<T> sg : allSubgroups) {
            levels.computeIfAbsent(sg.size(), k -> new java.util.ArrayList<>()).add(sg);
        }
        int yStep = 100;
        int xStep = 120;
        int numLevels = levels.size();
        int graphWidth = 800;
        // int graphHeight = Math.max(400, numLevels * yStep + 160); // unused
        int levelIdx = 0;
        java.util.Map<Set<T>, String> subgroupIds = new java.util.HashMap<>();
        int globalIndex = 0;
        for (java.util.Map.Entry<Integer, java.util.List<Set<T>>> entry : levels.entrySet()) {
            java.util.List<Set<T>> levelSubgroups = entry.getValue();
            int count = levelSubgroups.size();
            int y = 80 + (numLevels - levelIdx - 1) * yStep; // highest order at top
            int totalWidth = (count - 1) * xStep;
            int startX = (graphWidth - totalWidth) / 2;
            for (int j = 0; j < count; j++) {
                Set<T> sg = levelSubgroups.get(j);
                String id = subgroupId(sg, globalIndex);
                subgroupIds.put(sg, id);
                org.graphstream.graph.Node node = graph.addNode(id);
                String label = "|" + sg.size() + "| " + formatElementsGrid(sg);
                node.setAttribute("ui.label", label);
                node.setAttribute("xyz", startX + j * xStep, y, 0);
                globalIndex++;
            }
            levelIdx++;
        }

        // Add edges for direct inclusion
        for (Set<T> a : allSubgroups) {
            for (Set<T> b : allSubgroups) {
                if (a == b || a.isEmpty() || b.isEmpty())
                    continue;
                if (b.size() >= a.size())
                    continue;
                if (!a.containsAll(b))
                    continue;
                boolean hasIntermediate = false;
                for (Set<T> c : allSubgroups) {
                    if (c == a || c == b)
                        continue;
                    if (c.size() >= a.size() || c.size() <= b.size())
                        continue;
                    if (a.containsAll(c) && c.containsAll(b)) {
                        hasIntermediate = true;
                        break;
                    }
                }
                if (!hasIntermediate) {
                    String fromId = subgroupIds.get(b);
                    String toId = subgroupIds.get(a);
                    String edgeId = fromId + "_to_" + toId;
                    if (graph.getEdge(edgeId) == null) {
                        graph.addEdge(edgeId, fromId, toId, true);
                    }
                }
            }
        }

        // Style for better visualization
        graph.setAttribute("ui.stylesheet",
                "node { fill-color: #e3f2fd; stroke-mode: plain; stroke-color: #1976d2; size: 60px, 40px; text-size: 14; text-alignment: center; text-background-mode: rounded-box; text-background-color: #ffffff; text-padding: 6px; text-offset: 0px, 0px; } "
                        +
                        "edge { fill-color: #90caf9; arrow-shape: arrow; arrow-size: 12px, 8px; } ");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");

        Viewer viewer = graph.display(false); // disable auto layout
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }

    public static <T extends Element> void show(Group<T> group) {
        new SubgroupGraphViewer<>(group).showGraph();
    }
}
