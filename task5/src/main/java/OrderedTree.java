import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class OrderedTree<Key extends Comparable<Key>, Value>
        extends RBTree<CompoundKey<Key>, Value>
        implements Iterable<OrderedTree.Pair<CompoundKey<Key>, Value>> {

    public void putOne(Key key, Value value) {
        CompoundKey<Key> compoundKey = new CompoundKey<>(key);
        compoundKey.setNumber(size());
        put(compoundKey, value);
    }

    public void delete(int index) {
        Node node = getNode(index);
        int number = node.key.getNumber();
        preOrderTraversal(root, n -> {
            int num = n.key.getNumber();
            if (num > number)
                n.key.setNumber(num - 1);
        });
        delete(node.key);
    }

    protected Node getNode(int index) {
        if (index > size())
            return null;
        AtomicReference<Node> result = new AtomicReference<>();
        preOrderTraversal(root, node -> {
            if (node.key.getNumber() == index)
                result.set(node);
        });
        return result.get();
    }

    public Pair<CompoundKey<Key>, Value> get(int index) {
        return Optional.ofNullable(getNode(index))
                .map(node -> new Pair<>(node.key, node.val))
                .orElse(null);
    }

    @Override
    public Iterator<Pair<CompoundKey<Key>, Value>> iterator() {
        return new Iterator<Pair<CompoundKey<Key>, Value>>() {
            private int remains = size();

            @Override
            public boolean hasNext() {
                return remains > 0;
            }

            @Override
            public Pair<CompoundKey<Key>, Value> next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return get(size() - remains--);
            }
        };
    }

    protected void preOrderTraversal(Node n, Consumer<Node> handler) {
        handler.accept(n);
        if (n.left != null)
            preOrderTraversal(n.left, handler);
        if (n.right != null)
            preOrderTraversal(n.right, handler);
    }

    public void erase(int from, int to) {
        for (int i = 0; i < to - from; i++) {
            delete(from);
        }
    }

    public void exclude(OrderedTree<Key, Value> another) {
        another.forEach(pair -> {
            AtomicInteger toDelete = new AtomicInteger(-1);
            preOrderTraversal(root, node -> {
                if (pair.key.getKey().equals(node.key.getKey()))
                    toDelete.set(node.key.getNumber());
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
        List<Pair<Key, Value>> pairs = StreamSupport.stream(spliterator(), false)
                .map(pair -> new Pair<>(pair.key.getKey(), pair.value))
                .collect(Collectors.toList());
        for (int i = 0; i < times - 1; i++) {
            pairs.forEach(pair -> putOne(pair.key, pair.value));
        }
    }

    public void print() {
        preOrderTraversal(root, System.out::println);
    }

    public static final class Pair<K, V> {
        public final K key;
        public final V value;

        private Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
