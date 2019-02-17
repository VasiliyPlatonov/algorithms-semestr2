package ru.reeson2003.task3.table;

import org.junit.Test;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;

public class LinkedSetTest {

    @Test
    public void integerSetTest() {
        LinkedSet<Integer> integers = LinkedSet.of(100, 200, 300, 400, 500);
        print(integers);
        integers.add(600);
        print(integers);
        integers.remove(300);
        print(integers);
    }

    public void print(LinkedSet<?> set) {
        String str = StreamSupport.stream(set.spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.joining(","));
        System.out.println(str);
    }
}