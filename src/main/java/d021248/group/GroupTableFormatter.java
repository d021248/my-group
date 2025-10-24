package d021248.group;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import d021248.group.api.Element;

/**
 * Utility to visualize the (Cayley) operation table of a finite group.
 * <p>
 * Supports plain text, Markdown and LaTeX formats. For very large groups,
 * consider generating only selected rows/columns to avoid huge output.
 * </p>
 */
public final class GroupTableFormatter {
    private GroupTableFormatter() {
    }

    /** Configuration object for table generation. */
    public static final class Config<E extends Element> {
        private final FiniteGroup<E> group;
        private final Comparator<E> ordering; // optional custom ordering
        private final boolean showIdentityHighlight;

        private Config(FiniteGroup<E> group, Comparator<E> ordering, boolean showIdentityHighlight) {
            this.group = group;
            this.ordering = ordering;
            this.showIdentityHighlight = showIdentityHighlight;
        }

        public FiniteGroup<E> group() {
            return group;
        }

        public Comparator<E> ordering() {
            return ordering;
        }

        public boolean highlightIdentity() {
            return showIdentityHighlight;
        }
    }

    /** Builder for {@link Config}. */
    public static final class Builder<E extends Element> {
        private final FiniteGroup<E> group;
        private Comparator<E> ordering;
        private boolean highlightIdentity = true;

        public Builder(FiniteGroup<E> group) {
            this.group = group;
        }

        public Builder<E> ordering(Comparator<E> ordering) {
            this.ordering = ordering;
            return this;
        }

        public Builder<E> highlightIdentity(boolean flag) {
            this.highlightIdentity = flag;
            return this;
        }

        public Config<E> build() {
            return new Config<>(group, ordering, highlightIdentity);
        }
    }

    /** Convenience factory for builder. */
    public static <E extends Element> Builder<E> forGroup(FiniteGroup<E> group) {
        return new Builder<>(group);
    }

    /** Generate a plain text table (tab-separated). */
    public static <E extends Element> String toPlainText(Config<E> cfg) {
        List<E> elems = ordered(cfg);
        StringBuilder sb = new StringBuilder();
        // header
        sb.append("\t");
        for (E e : elems)
            sb.append(label(cfg, e)).append('\t');
        trimTrailingTab(sb);
        sb.append('\n');
        for (E row : elems) {
            sb.append(label(cfg, row)).append('\t');
            for (E col : elems) {
                E val = cfg.group().operate(row, col);
                sb.append(label(cfg, val)).append('\t');
            }
            trimTrailingTab(sb);
            sb.append('\n');
        }
        return sb.toString();
    }

    /** Generate a Markdown table. */
    public static <E extends Element> String toMarkdown(Config<E> cfg) {
        List<E> elems = ordered(cfg);
        StringBuilder sb = new StringBuilder();
        sb.append('|').append(' ').append(' ');
        for (E e : elems)
            sb.append('|').append(' ').append(label(cfg, e)).append(' ');
        sb.append('|').append('\n');
        // separator row
        sb.append('|').append(" :-- ");
        for (int i = 0; i < elems.size(); i++)
            sb.append('|').append(" :-- ");
        sb.append('|').append('\n');
        for (E row : elems) {
            sb.append('|').append(' ').append(label(cfg, row)).append(' ');
            for (E col : elems) {
                E val = cfg.group().operate(row, col);
                sb.append('|').append(' ').append(label(cfg, val)).append(' ');
            }
            sb.append('|').append('\n');
        }
        return sb.toString();
    }

    /**
     * Generate a LaTeX tabular environment (requires math mode escaping if needed).
     */
    public static <E extends Element> String toLatex(Config<E> cfg) {
        List<E> elems = ordered(cfg);
        StringBuilder sb = new StringBuilder();
        sb.append("\\begin{tabular}{c");
        for (int i = 0; i < elems.size(); i++)
            sb.append('c');
        sb.append("}\n");
        // header row
        sb.append(" & ");
        for (int i = 0; i < elems.size(); i++) {
            sb.append(escapeLatex(label(cfg, elems.get(i))));
            if (i < elems.size() - 1)
                sb.append(" & ");
        }
        sb.append(" \\ \\hline\n");
        for (E row : elems) {
            sb.append(escapeLatex(label(cfg, row))).append(" & ");
            for (int i = 0; i < elems.size(); i++) {
                E col = elems.get(i);
                E val = cfg.group().operate(row, col);
                sb.append(escapeLatex(label(cfg, val)));
                if (i < elems.size() - 1)
                    sb.append(" & ");
            }
            sb.append(" \\ \n");
        }
        sb.append("\\end{tabular}");
        return sb.toString();
    }

    private static <E extends Element> List<E> ordered(Config<E> cfg) {
        Set<E> set = cfg.group().elements();
        Comparator<E> ordering = cfg.ordering();
        List<E> list = new ArrayList<>(set);
        if (ordering != null)
            list.sort(ordering);
        return list;
    }

    private static void trimTrailingTab(StringBuilder sb) {
        int len = sb.length();
        if (len > 0 && sb.charAt(len - 1) == '\t')
            sb.deleteCharAt(len - 1);
    }

    private static <E extends Element> String label(Config<E> cfg, E e) {
        String base = e.toString();
        if (cfg.highlightIdentity() && e.equals(cfg.group().identity())) {
            return "[" + base + "]"; // simple highlighting style; Markdown renders [] plainly
        }
        return base;
    }

    private static String escapeLatex(String s) {
        return s.chars().mapToObj(c -> switch (c) {
            case '&' -> "\\&";
            case '%' -> "\\%";
            case '$' -> "\\$";
            case '#' -> "\\#";
            case '_' -> "\\_";
            case '{' -> "\\{";
            case '}' -> "\\}";
            case '~' -> "\\textasciitilde{}";
            case '^' -> "\\textasciicircum{}";
            case '\\' -> "\\textbackslash{}";
            default -> String.valueOf((char) c);
        }).collect(Collectors.joining());
    }
}
