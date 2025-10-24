package d021248.group.util;

import java.util.ArrayList;
import java.util.List;

import d021248.group.Group;
import d021248.group.api.Element;

/** Utility to build a Cayley table for a finite group. */
public final class CayleyTable {
    private CayleyTable() {
    }

    public static <E extends Element> Table<E> build(Group<E> group) {
        List<E> elems = new ArrayList<>(group.elements());
        int n = elems.size();
        List<List<E>> table = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            List<E> row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add(group.operate(elems.get(i), elems.get(j)));
            }
            table.add(row);
        }
        return new Table<>(elems, table);
    }

    public static <E extends Element> String toMarkdown(Table<E> table) {
        StringBuilder sb = new StringBuilder();
        sb.append("| * ∘ * ");
        for (E e : table.elements())
            sb.append("| ").append(e).append(' ');
        sb.append("|\n");
        sb.append("|");
        sb.append("---|");
        table.elements().forEach(e -> sb.append("---|"));
        sb.append("\n");
        for (int i = 0; i < table.elements().size(); i++) {
            sb.append("| ").append(table.elements().get(i)).append(' ');
            for (int j = 0; j < table.elements().size(); j++) {
                sb.append("| ").append(table.values().get(i).get(j)).append(' ');
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    /**
     * Immutable table representation: row/column order matches {@link #elements()}.
     */
    public static final record Table<E extends Element>(List<E> elements, List<List<E>> values) {
        public Table {
            elements = List.copyOf(elements);
            values = values.stream().map(List::copyOf).toList();
        }

        /** Number of elements (order of group). */
        public int size() {
            return elements.size();
        }
    }
}