import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
    public static final String EXCEL_COPY_FILE = "COPY.xls";
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

    private void exportToExcel() throws IOException {
        HSSFWorkbook book = new HSSFWorkbook(new FileInputStream(EXCEL_FILE));
        HSSFSheet sheet = book.getSheet("Лист1");
        exportInData(sheet);
        exportOutData(sheet);
        book.write(new File(EXCEL_COPY_FILE));
        book.close();
    }

    private void exportOutData(HSSFSheet sheet) throws IOException {
        List<String> list = getStrings(OUT_FILE);
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.getRow(i + 3);
            String[] numbers = list.get(i).split(" ");
            for (int j = 0; j < numbers.length; j++) {
                row.getCell(52 + j).setCellValue(numbers[j]);
            }
        }
    }

    private void exportInData(HSSFSheet sheet) throws IOException {
        List<String> list = getStrings(IN_FILE);
        HSSFRow row = sheet.getRow(1);
        int total = Integer.parseInt(list.get(0));
        row.getCell(0).setCellValue(total);

        for (int i = 0; i < total; i++) {
            HSSFRow nextRow = sheet.getRow(i + 3);
            String[] entry = list.get(i + 1).split(" ");
            nextRow.getCell(0).setCellValue(entry[0]);
            nextRow.getCell(1).setCellValue(entry[1]);
        }
    }

    private List<String> getStrings(String fileName) throws IOException {
        return Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
    }

    private void processResults() {
        try {
            Process process = Runtime.getRuntime().exec(RG_32_17_EXE);
            Files.copy(Paths.get(Experiment.OUT_FILE), process.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
