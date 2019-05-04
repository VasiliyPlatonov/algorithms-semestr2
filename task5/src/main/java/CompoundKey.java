import java.util.Objects;

class CompoundKey<T extends Comparable<T>> implements Comparable<CompoundKey<T>> {
    private final T key;
    private int index;

    public CompoundKey(T key) {
        this.key = key;
    }

    @Override
    public int compareTo(CompoundKey<T> compoundKey) {
        int i = key.compareTo(compoundKey.key);
        if (i == 0) {
            return Integer.compare(index, compoundKey.index);
        }
        return i;
    }

    public T getKey() {
        return key;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundKey<?> that = (CompoundKey<?>) o;
        return index == that.index &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, index);
    }

    @Override
    public String toString() {
        return "{" +
                "key=" + key +
                ", index=" + index +
                '}';
    }
}
