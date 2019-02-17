package ru.reeson2003.task3.table;

import reactor.util.function.Tuples;
import ru.reeson2003.task3.table.LinkedSet.Element;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleHashTable<T> implements HashTable<T> {

    private final int bucketsCount;

    private final Function<T, Integer> hashFunction;

    private LinkedSet<T>[] data;

    @SuppressWarnings("unchecked")
    public SimpleHashTable(int bucketsCount, Function<T, Integer> hashFunction) {
        this.bucketsCount = bucketsCount;
        this.hashFunction = hashFunction.andThen(n -> n % bucketsCount);
        data = new LinkedSet[bucketsCount];
        for (int i = 0; i < data.length; i++) {
            data[i] = LinkedSet.of();
        }
    }

    @Override
    public void add(T value) {
        Integer bucket = hashFunction.apply(value);
        data[bucket].add(value);
    }

    @Override
    public void remove(T value) {
        Integer bucket = hashFunction.apply(value);
        data[bucket].remove(value);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iter();
    }

    @Override
    public String toString() {
        return IntStream.range(0, data.length)
                .mapToObj(i -> Tuples.of(i, data[i]))
                .map(t -> "Bucket #" + t.getT1() + "\n" + t.getT2().toString() + "\n")
                .collect(Collectors.joining());
    }

    private class Iter implements Iterator<T> {
        private int bucketNumber;
        private Element<T> next;

        public Iter() {
            bucketNumber = 0;
            next = getNextFromBucket(0);
        }

        private Element<T> getNext() {
            if (next == null) {
                return next;
            }
            Element<T> current = next;
            if (current.next() != null) {
                next = current.next();
                return current;
            } else {
                next = getNextFromBucket(bucketNumber + 1);
                return current;
            }
        }

        private Element<T> getNextFromBucket(int bucketNumber) {
            for (int i = bucketNumber; i < bucketsCount; i++) {
                Element<T> head = data[bucketNumber].head();
                if (head != null) {
                    this.bucketNumber = i;
                    return head;
                }
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            return next.value();
        }
    }
}
