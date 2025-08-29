package d021248.group.groups.quaternion;

import java.util.function.BinaryOperator;

public class QuaternionOperation implements BinaryOperator<QuaternionElement> {
    private static final java.util.Map<String, java.util.Map<String, String>> TABLE;

    static {
        TABLE = new java.util.HashMap<>();

        java.util.Map<String, String> t1 = new java.util.HashMap<>();
        t1.put("1", "1");
        t1.put("i", "i");
        t1.put("j", "j");
        t1.put("k", "k");
        t1.put("-1", "-1");
        t1.put("-i", "-i");
        t1.put("-j", "-j");
        t1.put("-k", "-k");
        TABLE.put("1", t1);

        java.util.Map<String, String> tMinus1 = new java.util.HashMap<>();
        tMinus1.put("1", "-1");
        tMinus1.put("i", "-i");
        tMinus1.put("j", "-j");
        tMinus1.put("k", "-k");
        tMinus1.put("-1", "1");
        tMinus1.put("-i", "i");
        tMinus1.put("-j", "j");
        tMinus1.put("-k", "k");
        TABLE.put("-1", tMinus1);

        java.util.Map<String, String> ti = new java.util.HashMap<>();
        ti.put("1", "i");
        ti.put("i", "-1");
        ti.put("j", "k");
        ti.put("k", "-j");
        ti.put("-1", "-i");
        ti.put("-i", "1");
        ti.put("-j", "-k");
        ti.put("-k", "j");
        TABLE.put("i", ti);

        java.util.Map<String, String> tj = new java.util.HashMap<>();
        tj.put("1", "j");
        tj.put("i", "-k");
        tj.put("j", "-1");
        tj.put("k", "i");
        tj.put("-1", "-j");
        tj.put("-i", "k");
        tj.put("-j", "1");
        tj.put("-k", "-i");
        TABLE.put("j", tj);

        java.util.Map<String, String> tk = new java.util.HashMap<>();
        tk.put("1", "k");
        tk.put("i", "j");
        tk.put("j", "-i");
        tk.put("k", "-1");
        tk.put("-1", "-k");
        tk.put("-i", "-j");
        tk.put("-j", "i");
        tk.put("-k", "1");
        TABLE.put("k", tk);
    }

    @Override
    public QuaternionElement apply(QuaternionElement a, QuaternionElement b) {
        String av = a.value();
        String bv = b.value();
        boolean negA = av.startsWith("-");
        boolean negB = bv.startsWith("-");
        String aPos = negA ? av.substring(1) : av;
        String bPos = negB ? bv.substring(1) : bv;
        java.util.Map<String, String> row = TABLE.get(aPos);
        String result = (row != null) ? row.get(bPos) : null;
        if (result == null)
            throw new IllegalArgumentException("Invalid QuaternionElement: " + av + ", " + bv);
        boolean negResult = negA ^ negB;
        String finalResult = negResult && !result.equals("1") ? negate(result) : result;
        return new QuaternionElement(finalResult);
    }

    private static String negate(String v) {
        return v.startsWith("-") ? v.substring(1) : "-" + v;
    }

}
