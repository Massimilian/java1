package Olga;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



// класс клиента, который будет работать с сервером
public class Client {
    public static void main(String[] args) throws IOException {
        Scanner send = new Scanner(System.in);
        while (true) {
            Socket s = new Socket("localhost", 40001); // создаём сокет, который будет работать на сервере
            PrintWriter pw = new PrintWriter(s.getOutputStream()); // активизируем систему записи информации в сокет
            Scanner in = new Scanner(s.getInputStream());
            System.out.print("You: ");
            String words = send.nextLine();
            pw.println(words);
            pw.flush();
            String info = in.nextLine();
            System.out.println("Server: " + info);
        }
    }
}

