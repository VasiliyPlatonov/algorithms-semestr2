package ru.reeson2003.task3.table;

import org.junit.Test;

import java.util.Random;
import java.util.function.Function;

public class SimpleHashTableTest {

    private static final int BUCKETS_COUNT = 16;
    private static final Function<Integer, Integer> HASH_FUNCTION = n -> 15 * n + 2;

    @Test
    public void integerHashtableTest() {
        HashTable<Integer> table = new SimpleHashTable<>(BUCKETS_COUNT, HASH_FUNCTION);
        Random random = new Random(System.nanoTime());
        for (int i = 0; i < BUCKETS_COUNT; i++) {
             for (int j = 0; j < 32; j++) {
                 table.add(random.nextInt(100));
             }
        }
        System.out.println(table);
    }
}