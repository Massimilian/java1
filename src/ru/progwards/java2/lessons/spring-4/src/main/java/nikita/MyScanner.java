package nikita;

import java.io.*;

public class MyScanner {
    public void writeFile(String info, String to) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(to)));
            bw.write(info);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readLine() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }

    public String readLine(String from) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(from)));
        return reader.readLine();
    }

}
