import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Random;
import java.util.function.UnaryOperator;

public class Domain {

    private final int number;
    private final int bound;
    private final UnaryOperator<Collection<Integer>> collectionFactory;

    public Domain(int number, int bound, UnaryOperator<Collection<Integer>> collectionFactory) {
        this.number = number;
        this.bound = bound;
        this.collectionFactory = collectionFactory;
    }

    public static void main(String[] args) {
        new Domain(32, 1000, LinkedList::new).process(System.out);
    }

    public void process(OutputStream out) {
        MyContainer<Integer> a = generate();
        MyContainer<Integer> b = generate();
        MyContainer<Integer> c = generate();
        MyContainer<Integer> d = generate();
        MyContainer<Integer> e = generate();
        PrintStream os = new PrintStream(out);
        os.println("a = " + a);
        os.println("b = " + b);
        os.println("c = " + c);
        os.println("d = " + d);
        os.println("e = " + e);
        c.intersect(d);
        os.println("intersect = " + c);
        c.differ(e);
        os.println("differ = " + c);
        a.unite(b);
        os.println("union = " + a);
        a.symDiffer(c);
        os.println("symDiffer = " + a);
    }

    MyContainer<Integer> generate() {
        Random random = new Random();
        MyContainer<Integer> data = new MyContainer<>(collectionFactory);
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < 32; j++) {
                data.add(random.nextInt(bound));
            }
        }
        return data;
    }
}
