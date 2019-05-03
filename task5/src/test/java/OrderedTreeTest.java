import org.junit.Test;

import static org.junit.Assert.*;

public class OrderedTreeTest {

    @Test
    public void putOne() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>();
        tree.putOne(1, 1);
        tree.putOne(1, 2);
        tree.putOne(2, 2);
        tree.putOne(2, 3);
        tree.putOne(3, 3);
        assertEquals(5, tree.size);
    }

    @Test
    public void delete() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>();
        tree.putOne(1, 1);
        tree.putOne(1, 2);
        tree.putOne(2, 2);
        tree.putOne(2, 3);
        tree.putOne(3, 3);
        tree.preOrderTraversal(tree.root, System.out::println);
        System.out.println();
        tree.delete(3);
        tree.preOrderTraversal(tree.root, System.out::println);

    }
}