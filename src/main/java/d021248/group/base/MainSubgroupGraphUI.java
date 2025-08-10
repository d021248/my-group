package d021248.group.base;

import d021248.group.api.Group;
import d021248.group.cyclic.CyclicElement;
import d021248.group.cyclic.CyclicGroup;
import d021248.group.symmetric.Permutation;
import d021248.group.symmetric.SymmetricGroup;

public class MainSubgroupGraphUI {
    public static void main(String[] args) {

        Group<CyclicElement> cyclicGroup = new CyclicGroup(24);
        // SubgroupGraphViewer.show(cyclicGroup);

        Group<Permutation> symmetricGroup = new SymmetricGroup(4);
        SubgroupGraphViewer.show(symmetricGroup);
        SubgroupGraphViewer.show(symmetricGroup);
    }
}
