import java.util.Random;

public class Domain {

    public static final int NUMBER = 100;
    public static final int BOUND = 10000;

    public static void main(String[] args) {
        new Domain().process();
    }

    public void process() {
        MyContainer<Integer> a = generate();
        MyContainer<Integer> b = generate();
        MyContainer<Integer> c = generate();
        MyContainer<Integer> d = generate();
        MyContainer<Integer> e = generate();
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("c = " + c);
        System.out.println("d = " + d);
        System.out.println("e = " + e);
        c.intersect(d);
        System.out.println("intersect = " + c);
        c.differ(e);
        System.out.println("differ = " + c);
        a.unite(b);
        System.out.println("union = " + a);
        a.symDiffer(c);
        System.out.println("symDiffer = " + a);
    }

    MyContainer<Integer> generate() {
        Random random = new Random();
        MyContainer<Integer> data = new MyContainer<>();
        for (int i = 0; i < NUMBER; i++) {
            for (int j = 0; j < 32; j++) {
                data.add(random.nextInt(BOUND));
            }
        }
        return data;
    }
}
