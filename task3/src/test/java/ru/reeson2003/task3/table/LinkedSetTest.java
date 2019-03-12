package ru.reeson2003.task3.table;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class LinkedSetTest {

    @Test
    public void integerSetTest() {
        LinkedSet<Integer> integers = LinkedSet.of(100, 200, 200, 300, 200,400, 500, 200);
        assertThat(integers.print(), is("[500,400,300,200,100]"));
        integers.add(600);
        assertThat(integers.print(), is("[600,500,400,300,200,100]"));
        integers.remove(300);
        assertThat(integers.print(), is("[600,500,400,200,100]"));
    }
}