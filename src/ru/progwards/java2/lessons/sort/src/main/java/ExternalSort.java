
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

public class ExternalSort {
    private final TreeMap<Integer, Integer> fileNames = new TreeMap<>();
    private final int sizeOfTempFile = 10000;
    private final String positionOfTempFiles = "TempFiles";
    private int counterOfMaxes = 0;

    static void sort(String inFileName, String outFileName) throws IOException {
        ExternalSort es = new ExternalSort();
        es.nonStaticSort(inFileName, outFileName);
    }

    private void nonStaticSort(String inFileName, String outFileName) throws IOException {
        GenData.generate();
        Files.createDirectory(Paths.get(positionOfTempFiles));
        this.readWrite(inFileName);
        this.secondPass(outFileName);
        this.finalDeletes();
    }

    private void finalDeletes() throws IOException {
        counterOfMaxes--;
        while (counterOfMaxes != -1) {
            Files.deleteIfExists(Paths.get(positionOfTempFiles + "/" + counterOfMaxes-- + ".txt"));
        }
        Files.deleteIfExists(Paths.get(positionOfTempFiles));
    }

    private void secondPass(String outFileName) throws IOException {
        File file = new File(outFileName);
        file.createNewFile();
        ArrayList<Long> numbers = this.firstPrepare();
        while (this.getNumberTo(Paths.get(outFileName), numbers)) {
        }
    }

    private boolean getNumberTo(Path path, ArrayList<Long> numbers) throws IOException {
        int positionOfMin = this.findAndWriteMinValue(path, numbers);
        fileNames.put(positionOfMin, fileNames.get(positionOfMin) + 1);
        long newNumber = this.readFileWStringByNumber(positionOfMin, fileNames.get(positionOfMin));
        numbers.set(positionOfMin, newNumber);
        counterOfMaxes = newNumber == Long.MAX_VALUE ? counterOfMaxes + 1 : counterOfMaxes;
        return this.counterOfMaxes != numbers.size();
    }

    private int findAndWriteMinValue(Path path, ArrayList<Long> numbers) throws IOException {
        long l = numbers.stream().min(Comparator.naturalOrder()).get();
        Files.write(path, (l + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
        return numbers.indexOf(l);
    }

    private ArrayList<Long> firstPrepare() throws IOException {
        ArrayList<Long> result = new ArrayList<>(fileNames.size());
        for (int i = 0; i < fileNames.size(); i++) {
            result.add(this.readFileWStringByNumber(i, 0));
        }
        return result;
    }

    private long readFileWStringByNumber(int file, int num) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(positionOfTempFiles + "/" + file + ".txt"));
        long result = 0;
        if (num >= sizeOfTempFile) {
            result = Long.MAX_VALUE;
        } else {
            for (int i = 0; i <= num; i++) {
                if (i == num) {
                    result = Long.parseLong(r.readLine());
                }
                r.readLine();
            }
        }
        r.close();
        return result;
    }

    private void writer(String temp, int count) throws IOException {
        String name = positionOfTempFiles + "/" + count + ".txt";
        Files.writeString(Paths.get(name), temp);
    }

    private void readWrite(String forRead) throws IOException {
        BufferedReader r = new BufferedReader(new FileReader(forRead));
        String line;
        ArrayList<String> list = new ArrayList<>();
        int count = 0;
        int nameNumber = 0;
        boolean hasMore = true;
        while (hasMore) {
            line = r.readLine();
            if (line != null && count < sizeOfTempFile) {
                list.add(line);
                count++;
                if (count == sizeOfTempFile) {
                    count = 0;
                    this.writer(this.builder(this.sort(list)), nameNumber);
                    fileNames.put(nameNumber++, 0);
                    list.clear();
                }
            } else {
                if (list.size() != 0) {
                    this.writer(this.builder(this.sort(list)), nameNumber);
                    fileNames.put(nameNumber, 0);
                }
                hasMore = false;
            }
        }
    }

    private String builder(ArrayList<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(System.lineSeparator());
        }
        return sb.toString();
    }


    public ArrayList<String> sort(ArrayList<String> array) {
        return sort(array, 0, array.size() - 1); // вызываем сортировку с теми данными с ограничением данных
    }

    private ArrayList<String> sort(ArrayList<String> array, int low, int high) {
        if (array.size() == 0) { // возврат, если массив нулевой
            return null;
        }

        if (low >= high) { // возврат, если массив минимален
            return array;
        }

        int middle = low + (high - low) / 2;
        String center = array.get(middle);

        int i = low;
        int j = high;
        while (i <= j) {
            while (Integer.parseInt(array.get(i)) < Integer.parseInt(center)) {
                i++;
            }

            while (Integer.parseInt(array.get(j)) > Integer.parseInt(center)) {
                j--;
            }

            if (i <= j) {
                String temp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, temp);
                i++;
                j--;
            }
        }
        if (low < j) {
            sort(array, low, j);
        }
        if (high > i) {
            sort(array, i, high);
        }
        return array;
    }


    public static void main(String[] args) throws IOException {
        ExternalSort.sort("data.txt", "sortedData.txt");
    }
}
