package ru.reeson2003.task3;

import org.springframework.stereotype.Component;
import ru.reeson2003.task3.table.HashTable;
import ru.reeson2003.task3.table.SimpleHashTable;

import java.util.Random;
import java.util.function.Function;

@Component
public class Domain {

    private static final int RANGE = 100;
    private static final int BUCKETS_COUNT = 32;
    private static final Function<Integer, Integer> HASH_FUNCTION = n -> (31 * n + 3) % BUCKETS_COUNT;

    private final Random random = new Random(System.nanoTime());

    public HashTable<Integer> process(HashTable<Integer> a,
                                      HashTable<Integer> b,
                                      HashTable<Integer> c,
                                      HashTable<Integer> d,
                                      HashTable<Integer> e) {
        c.intersect(d);
        c.differ(e);
        a.unite(b);
        a.symDiffer(c);
        return a;
    }

    public HashTable<Integer> generate() {
        HashTable<Integer> table = new SimpleHashTable<>(BUCKETS_COUNT, HASH_FUNCTION);
        for (int i = 0; i < BUCKETS_COUNT; i++) {
            for (int j = 0; j < 32; j++) {
                table.add(random.nextInt(RANGE));
            }
        }
        return table;
    }
}
