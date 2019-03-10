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
    public boolean contains(T value) {
        Integer bucket = hashFunction.apply(value);
        for (T next : data[bucket]) {
            if (next.equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String print() {
        return IntStream.range(0, data.length)
                .mapToObj(n -> n + ": " + data[n].print())
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void unite(HashTable<T> another) {
        for (T t : another) {
            if (!contains(t)) {
                add(t);
            }
        }
    }

    @Override
    public void intersect(HashTable<T> another) {
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (!another.contains(next)) {
                remove(next);
            }
        }
    }

    @Override
    public void differ(HashTable<T> another) {
        Iterator<T> iterator = another.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (contains(next)) {
                remove(next);
            }
        }
    }

    @Override
    public void symDiffer(HashTable<T> another) {
        Iterator<T> iterator = another.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (contains(next)) {
                remove(next);
            } else {
                add(next);
            }
        }
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

        private void setNext() {
            Element<T> current = next;
            Element<T> nextInList = current.next();
            if (nextInList != null) {
                this.next = nextInList;
            } else {
                this.next = getNextFromBucket(bucketNumber + 1);
            }
        }

        private Element<T> getNextFromBucket(int bucketNumber) {
            for (int i = bucketNumber; i < bucketsCount; i++) {
                Element<T> head = data[i].head();
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
            T result = next.value();
            setNext();
            return result;
        }
    }
}
