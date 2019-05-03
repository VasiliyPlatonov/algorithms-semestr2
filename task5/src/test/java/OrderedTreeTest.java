import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderedTreeTest {

    @Test
    public void putOne() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>();
        tree.putOne(1, 1);
        tree.putOne(1, 2);
        tree.putOne(2, 2);
        tree.putOne(2, 3);
        tree.putOne(3, 3);
        assertEquals(5, tree.size());
        tree.print();
    }

    @Test
    public void delete() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>();
        tree.putOne(1, 1);
        tree.putOne(1, 2);
        tree.putOne(2, 2);
        tree.putOne(2, 3);
        tree.putOne(3, 3);
        tree.delete(3);
        assertEquals(4, tree.size());
        tree.print();
    }

    @Test
    public void erase() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>();
        tree.putOne(1, 1);
        tree.putOne(1, 2);
        tree.putOne(2, 2);
        tree.putOne(2, 3);
        tree.putOne(3, 3);
        tree.erase(1, 3);
        assertEquals(2, tree.size());
        tree.print();
    }

    @Test
    public void exclude() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>();
        tree.putOne(1, 1);
        tree.putOne(1, 2);
        tree.putOne(2, 2);
        tree.putOne(2, 3);
        tree.putOne(3, 3);
        OrderedTree<Integer, Integer> subtree = new OrderedTree<>();
        subtree.putOne(2, 2);
        subtree.putOne(2, 3);
        subtree.putOne(3, 3);
        tree.exclude(subtree);
        assertEquals(2, tree.size());
        tree.print();
    }

    @Test
    public void multiple() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>();
        tree.putOne(1, 1);
        tree.putOne(2, 2);
        tree.putOne(3, 3);
        tree.multiple(3);
        assertEquals(9, tree.size());
        tree.print();
    }
}