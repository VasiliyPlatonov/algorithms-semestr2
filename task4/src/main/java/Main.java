import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Domain domain = new Domain();
        ArrayList<RBTree<Integer, Integer>> trees = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            trees.add(domain.generate());
        }

        for (RBTree tree : trees) {
            TreePrinter.print(tree.getRoot());
            System.out.println("\n\n===============================================================" +
                    "===============================================================================\n\n");
        }

        RBTree<Integer, Integer> result = domain.process(trees.get(0),
                trees.get(1),
                trees.get(2),
                trees.get(3),
                trees.get(4));

        TreePrinter.print(result.getRoot());
    }
}
//  https://github.com/billvanyo/tree_printer/blob/master/src/main/java/tech/vanyo/treePrinter/TreePrinter.java