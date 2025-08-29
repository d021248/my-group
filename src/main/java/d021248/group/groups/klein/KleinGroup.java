package d021248.group.groups.klein;

import java.util.Set;
import java.util.function.BinaryOperator;

import d021248.group.api.SimpleGroup;

/**
 * The Klein four-group V_4 = {e, a, b, ab} under custom multiplication.
 */
public class KleinGroup extends SimpleGroup<KleinElement> {
    public KleinGroup() {
        super(
                Set.of(
                        new KleinElement("e"),
                        new KleinElement("a"),
                        new KleinElement("b"),
                        new KleinElement("ab")),
                (BinaryOperator<KleinElement>) (a, b) -> {
                    String av = a.value();
                    String bv = b.value();
                    if (av.equals("e"))
                        return b;
                    if (bv.equals("e"))
                        return a;
                    if (av.equals(bv))
                        return new KleinElement("e");
                    if ((av.equals("a") && bv.equals("b")) || (av.equals("b") && bv.equals("a")))
                        return new KleinElement("ab");
                    if ((av.equals("a") && bv.equals("ab")) || (av.equals("ab") && bv.equals("a")))
                        return new KleinElement("b");
                    if ((av.equals("b") && bv.equals("ab")) || (av.equals("ab") && bv.equals("b")))
                        return new KleinElement("a");
                    throw new IllegalArgumentException("Invalid KleinElement: " + av + ", " + bv);
                });
    }
}
