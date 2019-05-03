import java.util.Objects;

class CompoundKey<T extends Comparable<T>> implements Comparable<CompoundKey<T>> {
    private final T key;
    private int number;

    public CompoundKey(T key) {
        this.key = key;
    }

    @Override
    public int compareTo(CompoundKey<T> compoundKey) {
        int i = key.compareTo(compoundKey.key);
        if (i == 0) {
            return Integer.compare(number, compoundKey.number);
        }
        return i;
    }

    public T getKey() {
        return key;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompoundKey<?> that = (CompoundKey<?>) o;
        return number == that.number &&
                Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, number);
    }

    @Override
    public String toString() {
        return "{" +
                "key=" + key +
                ", number=" + number +
                '}';
    }
}
