import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(40000)) { // создали сервер с портом 40000
            while (true) {
                Socket serverSocket = ss.accept(); // открыли работу сервера
                new Thread(new RequestHandler(serverSocket)).start(); // создаём новый поток, в который помещаем RequestHandler, в котором лежит наш сервер
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

class RequestHandler implements Runnable {
    Socket server; // серверный сокет

    public RequestHandler(Socket server) {
        this.server = server;
    }

    @Override
    public void run() {
        try (InputStream is = server.getInputStream();
             OutputStream os = server.getOutputStream();) { // через try-with-resources создаём Input- и OutputStream сокета
            Scanner scanner = new Scanner(is);
            boolean done = false;
            while (scanner.hasNextLine() && !done) {
                String part = scanner.nextLine();
                if (part.equals("EXIT")) {
                    done = true;
                } else {
                    PrintWriter pw = new PrintWriter(os);
                    pw.println("Echo: " + part + System.lineSeparator());
                    pw.flush();
                    server.shutdownOutput();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
