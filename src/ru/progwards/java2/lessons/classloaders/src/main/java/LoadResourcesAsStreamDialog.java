import javax.swing.*;
import java.io.InputStream;

public class LoadResourcesAsStreamDialog {
    public static void main(String[] args) throws Exception {
        InputStream stream = ClassLoader.getSystemResourceAsStream("progwards_bw.png");

        Icon icon = new ImageIcon(stream.readAllBytes());

        JOptionPane.showMessageDialog(
                null,
                "InputStream ресурса:\n" + stream,
                "Пример загрузки ресурса",
                JOptionPane.INFORMATION_MESSAGE,
                icon
        );
    }
}
