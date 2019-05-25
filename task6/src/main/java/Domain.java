import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Random;

public class Domain {

    private final int number;
    private final int bound;

    public Domain(int number, int bound) {
        this.number = number;
        this.bound = bound;
    }

    public static void main(String[] args) {
        new Domain(100, 1000).process(System.out);
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
        MyContainer<Integer> data = new MyContainer<>();
        for (int i = 0; i < number; i++) {
            for (int j = 0; j < 32; j++) {
                data.add(random.nextInt(bound));
            }
        }
        return data;
    }
}
