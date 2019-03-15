package screen.exception;

import java.util.ArrayList;

public class ExceptionList {
    public static ArrayList<String> exceptions = new ArrayList<>();

    public static void showAll() {
        exceptions.forEach(System.out::println);
        clear();
    }

    private static void clear() {
        exceptions.clear();
    }
}
