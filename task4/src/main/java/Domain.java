import java.util.Random;

public class Domain {
    private static final int RANGE = 100;
    private static final int SIZE = 12;
    private final Random random = new Random(System.nanoTime());

    public RBTree<Integer, Integer> process(RBTree<Integer, Integer> a,
                                            RBTree<Integer, Integer> b,
                                            RBTree<Integer, Integer> c,
                                            RBTree<Integer, Integer> d,
                                            RBTree<Integer, Integer> e) {
        c.intersect(d.getRoot());
        c.differ(e.getRoot());
        a.unite(b.getRoot());
        a.symDiffer(c.getRoot());
        return a;
    }

    public RBTree<Integer, Integer> generate() {
        RBTree<Integer, Integer> tree = new RBTree<>();
        int val;
        for (int i = 0; i <= SIZE; i++) {
            val = random.nextInt(RANGE);
            tree.put(val, val);
        }
        return tree;
    }
}
