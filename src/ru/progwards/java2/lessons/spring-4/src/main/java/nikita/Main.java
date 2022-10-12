package nikita;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Wspp wspp = new Wspp();
        wspp.work("src/main/resources/test.txt");
    }
}
