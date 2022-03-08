import app.Store;
import app.model.Account;
import app.service.AccountService;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Client of HTTP server
 */
public class AtmClient implements AccountService {
    /**
     * Method for form, send request to the server and receive the response
     * @param args
     */
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 40001)){
            if(socket.isConnected()) { // проверяем соединение
                InputStream is = socket.getInputStream(); // получаем байтовый поток ввода от сокета
                OutputStream os = socket.getOutputStream(); // получаем байтовый поток вывода для сервера
                String req = formRequest();
                String request = "GET /" + req + " HTTP/1.1" + System.lineSeparator() + "hostname: localhost"
                        + System.lineSeparator(); // пишем request, который далее мы будем отправлять на сервер
                PrintWriter pw = new PrintWriter(os); // Засовываем в поток вывода PrintWriter
                pw.println(request); // ... в котором и будет лежать наш request (т.е. написанный ранее GET-запрос)
                pw.flush(); // обязательно закрываем PrintWriter
                BufferedReader br = new BufferedReader(new InputStreamReader(is)); // считываем response
                String str = "";
                while ((str = br.readLine()) != null) {
                    System.out.println(str);
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    /**
     * Method to form the request from user
     * @return formed request
     */
    private static String formRequest() {
        Scanner scanner = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();
        boolean again = true;
        int answer = -1;
        while (again) {
            System.out.println("Enter your request: 1. Balance; 2. Deposit; 3. Withdraw; 4. Transfer.");
            answer = scanner.nextInt();
            again = answer < 1 || answer > 4;
        }
        switch (answer) {
            case 1:
                sb.append("balance?param1=");
                sb.append(getAccName(false));
                break;
            case 2:
                sb.append("deposit?param1=");
                moneyGetAndSet(sb);
                break;
            case 3:
                sb.append("withdraw?param1=");
                moneyGetAndSet(sb);
                break;
            case 4:
                sb.append("transfer?param1=");
                sb.append(getAccName(false));
                sb.append("&param2=");
                sb.append(getAccName(true));
                sb.append("&param3=");
                sb.append(getSum());
                break;
            default:
        }
        return sb.toString();
    }

    /**
     * Method for form the first (user ID) and second (sum) parameters of request
     * @param sb for form
     */
    private static void moneyGetAndSet(StringBuilder sb) {
        sb.append(getAccName(false));
        sb.append("&param2=");
        sb.append(getSum());
    }

    /**
     * Method for get the sum from user
     * @return number of sum
     */
    private static String getSum() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the sum:");
        return scanner.nextLine();
    }

    /**
     * Method to form the account ID (first or second)
     * @param secondPar (is the paraneter second)
     * @return formed request
     */
    private static String getAccName(boolean secondPar) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Enter the %saccount id:%s", secondPar?"second ": "", System.lineSeparator());
        return scanner.nextLine();
    }

    @Override
    public double balance(Account account) {
        return Store.getStore().get(account.getId()).getAmount();
    }

    @Override
    public void deposit(Account account, double amount) {
        account.setAmount(account.getAmount() - amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        amount *= -1;
        System.out.println(amount);
        this.deposit(account, amount);
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        synchronized (from) {
            this.deposit(from, amount);
            this.withdraw(to, amount);
        }
    }
}
