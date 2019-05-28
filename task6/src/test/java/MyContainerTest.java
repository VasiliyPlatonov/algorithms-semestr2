import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.UnaryOperator;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MyContainerTest {

    private static final UnaryOperator<Collection<Integer>> collectionFactory = LinkedList::new;

    @Before
    @After
    public void separate() {
        System.out.println("================================");
    }

    @Test
    public void _1add() {
        System.out.println("Add test:");
        separate();
        MyContainer<Integer> tree = new MyContainer<>(collectionFactory);
        tree.add(1);
        tree.add(2);
        tree.add(3);
        assertEquals(3, tree.size());
        printSorted(tree);
    }

    @Test
    public void _2erase() {
        System.out.println("Erase test:");
        separate();
        MyContainer<Integer> tree = new MyContainer<>(collectionFactory);
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        System.out.println("Before:");
        printSorted(tree);
        separate();
        System.out.println("Erasing elements between [1],[3]");
        separate();
        tree.erase(1, 3);
        assertEquals(2, tree.size());
        System.out.println("After:");
        printSorted(tree);
    }

    @Test
    public void _3exclude() {
        System.out.println("Exclude test");
        separate();
        MyContainer<Integer> tree = new MyContainer<>(collectionFactory);
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        System.out.println("Before:");
        printSorted(tree);
        MyContainer<Integer> subtree = new MyContainer<>(collectionFactory);
        subtree.add(2);
        subtree.add(3);
        subtree.add(4);
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
    public void _4multiple() {
        System.out.println("Multiple test:");
        separate();
        MyContainer<Integer> tree = new MyContainer<>(collectionFactory);
        tree.add(1);
        tree.add(2);
        tree.add(3);
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

    private void printSorted(MyContainer<Integer> tree) {
        tree.origin.forEach(System.out::println);
    }
}