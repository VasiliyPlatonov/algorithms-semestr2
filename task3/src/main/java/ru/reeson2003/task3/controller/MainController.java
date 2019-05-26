package ru.reeson2003.task3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.reeson2003.task3.Domain;
import ru.reeson2003.task3.table.HashTable;

@RestController
public class MainController {

    @Autowired
    private Domain domain;

    @GetMapping("/{buckets}")
    public String process(@PathVariable("buckets") int buckets) {
        HashTable<Integer> a = domain.generate(buckets);
        HashTable<Integer> b = domain.generate(buckets);
        HashTable<Integer> c = domain.generate(buckets);
        HashTable<Integer> d = domain.generate(buckets);
        HashTable<Integer> e = domain.generate(buckets);
        StringBuilder sb = new StringBuilder();
        sb.append("================SET [A]=================\n")
                .append(a.print())
                .append("\n")
                .append("================SET [B]=================\n")
                .append(b.print())
                .append("\n")
                .append("================SET [C]=================\n")
                .append(c.print())
                .append("\n")
                .append("================SET [D]=================\n")
                .append(d.print())
                .append("\n")
                .append("================SET [E]=================\n")
                .append(e.print())
                .append("\n");
        HashTable<Integer> result = domain.process(a, b, c, d, e);
        sb.append("================RESULT=================\n")
                .append(result.print());
        System.out.println(sb.toString());
        return sb.toString().replaceAll("\n", "<br>");
    }
}
