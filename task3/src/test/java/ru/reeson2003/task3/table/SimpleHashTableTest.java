package ru.reeson2003.task3.table;

import org.junit.Test;

import java.util.Random;
import java.util.function.Function;

public class SimpleHashTableTest {

    private static final int BUCKETS_COUNT = 100;
    private static final Function<Integer, Integer> HASH_FUNCTION = n -> (97 * n + 2) % BUCKETS_COUNT;

    @Test
    public void integerHashtableTest() {
        HashTable<Integer> table = new SimpleHashTable<>(BUCKETS_COUNT, HASH_FUNCTION);
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < BUCKETS_COUNT; i++) {
             for (int j = 0; j < 32; j++) {
                 table.add(random.nextInt(300));
             }
        }
        System.out.println(table.print());
    }

    @Test
    public void uniteTest() {
        HashTable<Integer> first = new SimpleHashTable<>(10, Object::hashCode);
        first.add(1);
        first.add(2);
        first.add(3);
        HashTable<Integer> second = new SimpleHashTable<>(10, Object::hashCode);
        second.add(2);
        second.add(3);
        second.add(4);
        first.unite(second);
        System.out.println(first.print());
    }

    @Test
    public void intersectTest() {
        HashTable<Integer> first = new SimpleHashTable<>(10, Object::hashCode);
        first.add(1);
        first.add(2);
        first.add(3);
        HashTable<Integer> second = new SimpleHashTable<>(10, Object::hashCode);
        second.add(2);
        second.add(3);
        second.add(4);
        first.intersect(second);
        System.out.println(first.print());
    }

    @Test
    public void differTest() {
        HashTable<Integer> first = new SimpleHashTable<>(10, Object::hashCode);
        first.add(1);
        first.add(2);
        first.add(3);
        HashTable<Integer> second = new SimpleHashTable<>(10, Object::hashCode);
        second.add(2);
        second.add(3);
        second.add(4);
        first.differ(second);
        System.out.println(first.print());
    }

    @Test
    public void symDifferTest() {
        HashTable<Integer> first = new SimpleHashTable<>(10, Object::hashCode);
        first.add(1);
        first.add(2);
        first.add(3);
        HashTable<Integer> second = new SimpleHashTable<>(10, Object::hashCode);
        second.add(2);
        second.add(3);
        second.add(4);
        first.symDiffer(second);
        System.out.println(first.print());
    }
}