import com.google.common.collect.Iterables;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.apache.commons.collections4.CollectionUtils.*;

public class MyContainer<T extends Comparable<T>> implements Container<T> {

    protected LinkedList<T> origin = new LinkedList<>();

    @Override
    public Collection<T> toCollection() {
        return origin;
    }

    @Override
    public void add(T value) {
        origin.add(value);
    }

    @Override
    public void unite(Container<T> another) {
        this.origin = new LinkedList<>(union(origin, another.toCollection()));
    }

    @Override
    public void intersect(Container<T> another) {
        this.origin = new LinkedList<>(intersection(origin, another.toCollection()));
    }

    @Override
    public void differ(Container<T> another) {
        this.origin = new LinkedList<>(subtract(origin, another.toCollection()));
    }

    @Override
    public void symDiffer(Container<T> another) {
        this.origin = new LinkedList<>(disjunction(origin, another.toCollection()));
    }

    @Override
    public void erase(int from, int to) {
        IntStream.rangeClosed(from, to)
                .mapToObj(n -> Iterables.get(origin, n))
                .collect(Collectors.toList())
                .forEach(origin::remove);
    }

    @Override
    public void exclude(Container<T> another) {
        if (origin.containsAll(another.toCollection()))
            this.origin = new LinkedList<>(removeAll(origin, another.toCollection()));
    }

    @Override
    public void multiple(int times) {
        Stream<T> stream = origin.stream();
        for (int i = 0; i < times - 1; i++) {
            stream = Stream.concat(stream, origin.stream());
        }
        this.origin = stream.collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public String toString() {
        return origin.toString();
    }

    public int size() {
        return origin.size();
    }
}
