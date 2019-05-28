import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Comparator;
import java.util.Random;
import java.util.stream.StreamSupport;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrderedTreeTest {

    @Before
    @After
    public void separate() {
        System.out.println("================================");
    }

    @Test
    public void _1put() {
        System.out.println("Put duplicate keys test:");
        separate();
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        tree.put(1, 1);
        tree.put(1, 2);
        tree.put(2, 2);
        tree.put(2, 3);
        tree.put(3, 3);
        assertEquals(5, tree.size());
        printSorted(tree);
    }

    @Test
    public void _2delete() {
        System.out.println("Delete by index test:");
        separate();
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        tree.put(1, 1);
        tree.put(1, 2);
        tree.put(2, 2);
        tree.put(2, 3);
        tree.put(3, 3);
        System.out.println("Before:");
        printSorted(tree);
        separate();
        System.out.println("Deleting element index [3]");
        separate();
        tree.delete(3);
        assertEquals(4, tree.size());
        System.out.println("After:");
        printSorted(tree);
    }

    @Test
    public void _3erase() {
        System.out.println("Erase test:");
        separate();
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        tree.put(1, 1);
        tree.put(2, 2);
        tree.put(3, 3);
        tree.put(4, 4);
        tree.put(5, 5);
        System.out.println("Before:");
        printSorted(tree);
        separate();
        System.out.println("Erasing elements between [1],[3]");
        separate();
        tree.erase(2, 3);
        assertEquals(3, tree.size());
        System.out.println("After:");
        printSorted(tree);
    }

    @Test
    public void _3eraseRandom() {
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        Random random = new Random();
        for (int i = 0; i < 40; i++) {
            tree.put(i, random.nextInt(1000));
        }
        System.out.println("Before:");
        printSorted(tree);
        separate();
        System.out.println("Erasing elements between [20],[29]");
        separate();
        tree.erase(20, 29);
        System.out.println("After:");
        printSorted(tree);
    }


    @Test
    public void _4exclude() {
        System.out.println("Exclude test");
        separate();
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        tree.put(1, 1);
        tree.put(1, 2);
        tree.put(2, 2);
        tree.put(2, 3);
        tree.put(3, 3);
        System.out.println("Before:");
        printSorted(tree);
        OrderedTree<Integer, Integer> subtree = new OrderedTree<>(new RBTreeImpl<>());
        subtree.put(2, 2);
        subtree.put(2, 3);
        subtree.put(3, 3);
        separate();
        System.out.println("Excluding sub tree:");
        separate();
        printSorted(subtree);
        tree.exclude(subtree);
        assertEquals(2, tree.size());
        System.out.println("After:");
        printSorted(tree);
    }

    @Test
    public void _4excludeRandom() {
        System.out.println("Exclude test");
        separate();
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            tree.put(i, random.nextInt(1000));
        }
        System.out.println("Before:");
        printSorted(tree);
        OrderedTree<Integer, Integer> subtree = new OrderedTree<>(new RBTreeImpl<>());
        for (int i = 0; i < 10; i++) {
            subtree.put(i, random.nextInt(1000));
        }
        separate();
        System.out.println("Excluding sub tree:");
        separate();
        printSorted(subtree);
        tree.exclude(subtree);
        System.out.println("After:");
        printSorted(tree);
    }

    @Test
    public void _4multiple() {
        System.out.println("Multiple test:");
        separate();
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        tree.put(1, 1);
        tree.put(2, 2);
        tree.put(3, 3);
        System.out.println("Before:");
        printSorted(tree);
        separate();
        System.out.println("Multiplying [3] times");
        separate();
        tree.multiple(3);
        assertEquals(9, tree.size());
        System.out.println("After:");
        printSorted(tree);
    }

    @Test
    public void _4multipleRandom() {
        System.out.println("Multiple test:");
        separate();
        OrderedTree<Integer, Integer> tree = new OrderedTree<>(new RBTreeImpl<>());
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            tree.put(i, random.nextInt(1000));
        }
        System.out.println("Before:");
        printSorted(tree);
        separate();
        System.out.println("Multiplying [3] times");
        separate();
        tree.multiple(4);
        System.out.println("After:");
        printSorted(tree);
    }

    private void printSorted(OrderedTree<Integer, Integer> tree) {
        StreamSupport.stream(tree.spliterator(), false)
                .sorted(Comparator.comparingInt(key -> key.getKey().getIndex()))
                .forEach(System.out::println);
    }
}