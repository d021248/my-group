package d021248.group.groups.klein;

import d021248.group.api.Operation;

public class KleinOperation implements Operation<KleinElement> {
    @Override
    public KleinElement apply(KleinElement a, KleinElement b) {
        String av = a.getValue();
        String bv = b.getValue();
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
    }
}
