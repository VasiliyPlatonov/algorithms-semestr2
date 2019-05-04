import java.util.Collection;

public interface Container<T extends Comparable<T>> {

    Collection<T> toCollection();

    void add(T value);

    void unite(Container<T> another);

    void intersect(Container<T> another);

    void differ(Container<T> another);

    void symDiffer(Container<T> another);

    void erase(int from, int to);

    void exclude(Container<T> another);

    void multiple(int times);
}
