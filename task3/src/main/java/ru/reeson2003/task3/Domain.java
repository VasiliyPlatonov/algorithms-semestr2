package ru.reeson2003.task3;

import org.springframework.stereotype.Component;
import ru.reeson2003.task3.table.HashTable;
import ru.reeson2003.task3.table.SimpleHashTable;

import java.util.Random;
import java.util.function.Function;

@Component
public class Domain {

    private static final int RANGE = 100;

    private static final Function<Integer, Function<Integer, Integer>> hashFunction = buckets -> n -> ((buckets - 1) * n + 2) % buckets;

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

    public HashTable<Integer> generate(int bucketsCount) {
        HashTable<Integer> table = new SimpleHashTable<>(bucketsCount, hashFunction.apply(bucketsCount));
        for (int i = 0; i < bucketsCount; i++) {
            for (int j = 0; j < 32; j++) {
                table.add(random.nextInt(RANGE));
            }
        }
        return table;
    }
}
