import java.io.*;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) throws IOException {
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("D:/text.txt")));
        dos.writeBytes("Some information: "); // записываем информацию в файл
        dos.flush(); // финализируем буфер обмена
        dos.close(); // закрываем ресурс

    }
}
