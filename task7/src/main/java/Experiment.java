import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Experiment implements Runnable {

    public static final int BOUND = 1000;
    public static final String RG_32_17_EXE = "RG32_17";
    public static final String IN_FILE = "in.txt";
    public static final String OUT_FILE = "out.txt";
    public static final String EXCEL_FILE = "EXAMPLE.xls";
    private static final OutputStream STUB = new OutputStream() {
        @Override
        public void write(int b) {
            /* no op */
        }
    };

    public static void main(String[] args) {
        new Experiment().run();
    }

    @Override
    public void run() {
        try {
            Files.deleteIfExists(Paths.get(IN_FILE));
            Files.deleteIfExists(Paths.get(OUT_FILE));
            generateData();
            processResults();
            exportToExcel();
        } catch (IOException ignored) {
        }
    }

    private void exportToExcel() {

    }

    private void processResults() throws IOException {
        Process process = Runtime.getRuntime().exec(RG_32_17_EXE);
        Files.copy(Paths.get(Experiment.OUT_FILE), process.getOutputStream());
    }

    private void generateData() {
        List<String> results = IntStream.range(5, 206)
                .filter(n -> n % 5 == 0)
                .flatMap(n -> IntStream.range(0, 3)
                        .map(i -> n))
                .mapToObj(n -> n + " " + process(n))
                .collect(Collectors.toList());
        try (PrintWriter writer = new PrintWriter(Experiment.IN_FILE)) {
            writer.println(results.size());
            results.forEach(writer::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private long process(int setPower) {
        long before = System.nanoTime();
        new Domain(setPower, BOUND).process(STUB);
        return System.nanoTime() - before;
    }
}
