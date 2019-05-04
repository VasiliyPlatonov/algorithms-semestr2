import java.util.function.BiConsumer;

public interface RBTree<Key extends Comparable<Key>, Value> {
    int size();

    Value get(Key key);

    void put(Key key, Value val);

    void delete(Key key);

    void walkThrough(BiConsumer<Key, Value> handler);

    class Pair<K, V> {
        private final K key;
        private final V value;

        public Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
