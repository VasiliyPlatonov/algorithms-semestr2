import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.apache.commons.collections4.CollectionUtils.*;

public class Domain {

    public static final int NUMBER = 100;
    public static final int BOUND = 10000;

    public static void main(String[] args) {
        new Domain().process();
    }

    public void process() {
        Set<CompoundKey<Integer>> a = generate();
        Set<CompoundKey<Integer>> b = generate();
        Set<CompoundKey<Integer>> c = generate();
        Set<CompoundKey<Integer>> d = generate();
        Set<CompoundKey<Integer>> e = generate();
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
        System.out.println("e = " + e);
        Collection<CompoundKey<Integer>> subtraction = subtract(d, e);
        System.out.println("subtraction = " + subtraction);
        Collection<CompoundKey<Integer>> intersection = intersection(c, subtraction);
        System.out.println("intersection = " + intersection);
        Collection<CompoundKey<Integer>> union = union(a, b);
        System.out.println("union = " + union);
        Collection<CompoundKey<Integer>> disjunction = disjunction(subtraction, union);
        System.out.println("disjunction = " + disjunction);
    }

    public SortedSet<CompoundKey<Integer>> generate() {
        Random random = new Random();
        AtomicInteger counter = new AtomicInteger();
        return IntStream.range(0, NUMBER)
                .map(k -> random.nextInt(BOUND))
                .boxed()
                .reduce(new TreeSet<>(), (set, next) -> {
                    CompoundKey<Integer> key = new CompoundKey<Integer>(next) {
                        @Override
                        public boolean equals(Object o) {
                            return getKey().equals(((CompoundKey<Integer>) o).getKey());
                        }

                        @Override
                        public int hashCode() {
                            return getKey();
                        }
                    };
                    key.setIndex(counter.getAndIncrement());
                    set.add(key);
                    return set;
                }, (a, b) -> b);
    }
}
