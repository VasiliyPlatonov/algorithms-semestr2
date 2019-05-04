import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderedTree<Key extends Comparable<Key>, Value>
        implements Iterable<RBTree.Pair<CompoundKey<Key>, Value>> {
    private RBTree<CompoundKey<Key>, Value> origin;

    public OrderedTree(RBTree<CompoundKey<Key>, Value> origin) {
        this.origin = origin;
    }


    public void put(Key key, Value value) {
        CompoundKey<Key> compoundKey = new CompoundKey<>(key);
        compoundKey.setIndex(origin.size());
        origin.put(compoundKey, value);
    }

    public void delete(int index) {
        RBTree.Pair<CompoundKey<Key>, Value> pair = getNode(index);
        int number = pair.getKey().getIndex();
        origin.walkThrough((key, value) -> {
            int num = key.getIndex();
            if (num > number)
                key.setIndex(num - 1);
        });
        origin.delete(pair.getKey());
    }

    protected RBTree.Pair<CompoundKey<Key>, Value> getNode(int index) {
        if (index > origin.size())
            return null;
        AtomicReference<RBTree.Pair<CompoundKey<Key>, Value>> result = new AtomicReference<>();
        origin.walkThrough((key, value) -> {
            if (key.getIndex() == index)
                result.set(new RBTree.Pair<>(key, value));
        });
        return result.get();
    }

    public RBTree.Pair<CompoundKey<Key>, Value> get(int index) {
        return Optional.ofNullable(getNode(index))
                .orElse(null);
    }

    public int size() {
        return origin.size();
    }

    @Override
    public Iterator<RBTree.Pair<CompoundKey<Key>, Value>> iterator() {
        return new Iterator<RBTree.Pair<CompoundKey<Key>, Value>>() {
            private int remains = origin.size();

            @Override
            public boolean hasNext() {
                return remains > 0;
            }

            @Override
            public RBTree.Pair<CompoundKey<Key>, Value> next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return get(origin.size() - remains--);
            }
        };
    }

    public void erase(int from, int to) {
        for (int i = 0; i <= to - from; i++) {
            delete(from);
        }
    }

    public void exclude(OrderedTree<Key, Value> another) {
        another.forEach(pair -> {
            AtomicInteger toDelete = new AtomicInteger(-1);
            origin.walkThrough((key, value) -> {
                if (pair.getKey().getKey().equals(key.getKey()))
                    toDelete.set(key.getIndex());
            });
            int index = toDelete.get();
            if (index >= 0) {
                delete(index);
            }
        });
    }

    public void multiple(int times) {
        if (times <= 1)
            return;
        List<RBTree.Pair<Key, Value>> pairs = StreamSupport.stream(spliterator(), false)
                .map(pair -> new RBTree.Pair<>(pair.getKey().getKey(), pair.getValue()))
                .collect(Collectors.toList());
        for (int i = 0; i < times - 1; i++) {
            pairs.forEach(pair -> put(pair.getKey(), pair.getValue()));
        }
    }

    public void printDFS() {
        origin.walkThrough((key, value) -> {
            System.out.println("key=" + key + ", value=" + value);
        });
    }

}
