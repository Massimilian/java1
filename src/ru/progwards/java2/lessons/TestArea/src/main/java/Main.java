import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(80)) { // в качестве аргумента указываем порт
            Socket serverSocket = ss.accept(); // получаем серверный сокет
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}

class RequestHandler implements Runnable {

    Socket server; // сюда мы передаём серверный сокет

    public RequestHandler(Socket server) {
        this.server = server;
    }

    @Override
    public void run() {
        try (InputStream is = server.getInputStream();
            OutputStream os = server.getOutputStream();
        ) {
            Scanner sc = new Scanner(is);
            boolean isDone = false;

            while(!isDone && sc.hasNextLine()) {

            }
        }catch (IOException io) {
            io.printStackTrace();
        }
    }
}