import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class OrderedTree<Key extends Comparable<Key>, Value> extends RBTree<CompoundKey<Key>, Value> implements Iterable<Value> {

    public void putOne(Key key, Value value) {
        CompoundKey<Key> compoundKey = new CompoundKey<>(key);
        compoundKey.setNumber(size);
        put(compoundKey, value);
    }

    public void delete(int index) {
        Node<CompoundKey<Key>, Value> node = getNode(index);
        delete(node.getKey());
        int number = node.getKey().getNumber();
        preOrderTraversal(root, n -> {
            int num = n.getKey().getNumber();
            if (num > number)
                n.getKey().setNumber(num - 1);
        });
    }

    protected Node<CompoundKey<Key>, Value> getNode(int index) {
        if (index > size)
            return null;
        AtomicReference<Node<CompoundKey<Key>, Value>> result = new AtomicReference<>();
        preOrderTraversal(root, node -> {
            if (node.getKey().getNumber() == index)
                result.set(node);
        });
        return result.get();
    }

    public Value get(int index) {
        return Optional.ofNullable(getNode(index))
                .map(Node::getValue)
                .orElse(null);
    }

    @Override
    public Iterator<Value> iterator() {
        return new Iterator<Value>() {
            private int remains = size;

            @Override
            public boolean hasNext() {
                return remains > 0;
            }

            @Override
            public Value next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                return get(size - remains--);
            }
        };
    }
}
