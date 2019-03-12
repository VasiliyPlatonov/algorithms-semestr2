package ru.reeson2003.task3.table;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface LinkedSet<T> extends Iterable<T> {

    static <T> LinkedSet<T> of(T... elements) {
        return new LinkedSetImpl<>(elements);
    }

    Element<T> head();

    void add(T element);

    void remove(T element);

    String print();

    interface Element<T> {
        T value();

        Element<T> next();
    }

    class LinkedSetImpl<T> implements LinkedSet<T> {
        private ElementImpl head;

        LinkedSetImpl(T... elements) {
            for (int i = 0; i < elements.length; i++) {
                add(elements[i]);
            }
        }

        @Override
        public Element<T> head() {
            return head;
        }

        @Override
        public void add(T element) {
            Iterator<T> iterator = iterator();
            while (iterator.hasNext()) {
                T next = iterator.next();
                if (next.equals(element))
                    return;
            }
            head = new ElementImpl(element, head);
        }

        @Override
        public void remove(T element) {
            if (head != null) {
                if (element.equals(head.value)) {
                    head = head.next;
                } else {
                    ElementImpl prev = head;
                    ElementImpl current = head.next;
                    while (current != null) {
                        if (element.equals(current.value)) {
                            prev.next = current.next;
                            break;
                        } else {
                            prev = current;
                            current = current.next;
                        }
                    }
                }
            }
        }

        @Override
        public String print() {
            return StreamSupport.stream(spliterator(), false)
                    .map(Objects::toString)
                    .collect(Collectors.joining(",", "[", "]"));
        }

        @Override
        public String toString() {
            return StreamSupport.stream(spliterator(), false)
                    .map(Objects::toString)
                    .collect(Collectors.joining(","));
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {

                private ElementImpl next = head;

                @Override
                public boolean hasNext() {
                    return next != null;
                }

                @Override
                public T next() {
                    if (next != null) {
                        T result = next.value();
                        next = next.next();
                        return result;
                    }
                    return null;
                }
            };
        }

        class ElementImpl implements Element<T> {
            private T value;
            private ElementImpl next;

            public ElementImpl(T value, ElementImpl next) {
                this.value = value;
                this.next = next;
            }

            @Override
            public T value() {
                return value;
            }

            @Override
            public ElementImpl next() {
                return next;
            }

            @Override
            public String toString() {
                return "ElementImpl{" +
                        "value=" + value +
                        '}';
            }
        }
    }
}
