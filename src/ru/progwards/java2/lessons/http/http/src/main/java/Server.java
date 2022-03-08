import app.Store;
import app.model.Account;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

/**
 * HTTP server
 */
public class Server {
    static AtmClient atm = new AtmClient();
    public static void main(String[] args) {
        System.out.println("Server started");
        try (ServerSocket ss = new ServerSocket(40001)) {
            while (true) {
                Socket serverSocket = ss.accept();
                new Thread(new RequestHandler(serverSocket, atm)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Class for multithreading work
 */
class RequestHandler implements Runnable {
    private final String neck = System.lineSeparator() + "Content-Type: text/html; charset=utf-8" +
            System.lineSeparator() + "Content-Length: 1234" + System.lineSeparator() + System.lineSeparator();
    private final String goodHeaders = "HTTP/1.1 200 OK" + neck;
    private final String badHeaders = "HTTP/1.1 404 BadRequest" + neck;
    private boolean needGoodRequest = true;
    private Socket server;
    AtmClient atm;

    public RequestHandler(Socket server, AtmClient atm) {
        this.server = server;
        this.atm = atm;
    }


    @Override
    public void run() {
        try (InputStream is = server.getInputStream();
             OutputStream os = server.getOutputStream();) {
            StringBuilder sb = new StringBuilder();
            Scanner scanner = new Scanner(is);
            System.out.println(Store.getStore());

            while (scanner.hasNextLine()) {
                String temp = scanner.nextLine();
//                System.out.println(temp);
                sb.append(temp);
                if (temp. isEmpty()) {
//                    isCont = false;
                    break;
                }
            }
            String request = this.prepare(sb.toString());
//            System.out.println("All request is " + request);
            int pos = request.indexOf("?");
            String method = request.substring(0, pos);
            HashMap<String, String> params = prepareParams(request.substring(pos + 1));
//            System.out.println("Method is " + method);
//            System.out.println("Params are " + params);
            sb = new StringBuilder();
            Account acc = Store.getStore().get(params.get("param1"));
            Account acc2;
            String amount;
            switch (method) {
                case "balance":
                    System.out.println("Balance on work:");
                    sb.append("Balance is ").append(atm.balance(acc)).append(".");
                    break;
                case "deposit":
                    System.out.println("Deposit on work:");
                    amount = params.get("param2");
                    atm.deposit(acc, Double.valueOf(amount));
                    sb.append("The balance is replenished (your balance now is ").append(acc.getAmount()).append(").");
                    break;
                case "withdraw":
                    System.out.println("Withdraw on work:");
                    amount = params.get("param2");
                    atm.withdraw(acc, Double.valueOf(amount));
                    sb.append("The balance is changed (your balance now is ").append(acc.getAmount()).append(").");
                    break;
                case "transfer":
                    System.out.println("Transfer on work");
                    acc2 = Store.getStore().get(params.get("param2"));
                    amount = params.get("param3");
                    atm.transfer(acc, acc2, Double.valueOf(amount));
                    sb.append(String.format("Transfered %s from %s to %s", amount, acc.getHolder(), acc2.getHolder()));
                    break;
                default:
                    needGoodRequest = false;
                    sb.append("Unknown command, sorry...");
            }
            PrintWriter pw = new PrintWriter(os);
            if (this.needGoodRequest) {
                pw.println(goodHeaders);
            } else {
                pw.println(badHeaders);
            }
            pw.println(sb.toString());
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for prepare params for request
     * @param cut is params
     * @return
     */
    private HashMap<String, String> prepareParams(String cut) {
        HashMap<String, String> params = new HashMap<>();
        String[] list = cut.split("&");
        for (int i = 0; i < list.length; i++) {
            params.put(list[i].substring(0, list[i].indexOf("=")), list[i].substring(list[i].indexOf("=") + 1));
        }
        return params;
    }

    /**
     * Method for cut unnecessary parts of string
     * @param forChange is a string for change
     * @return changed and prepared string
     */
    private String prepare(String forChange) {
        forChange = this.findBackspaces(forChange);
        int tail = forChange.lastIndexOf("hostname: localhost");
        String prepared = forChange.substring(5, tail - 9);
        return prepared;
    }

    /**
     * Method for find all backspaces (in case we work from Telnet)
     * @param forChange is a string for change
     * @return prepared string without backspaces ("\b")
     */
    private String findBackspaces(String forChange) {
        int pos = 0;
        while(pos > -1) {
            pos = forChange.indexOf("\b");
            if (pos != -1) {
                forChange = String.format("%s%s", forChange.substring(0, pos - 1), forChange.substring(pos + 1));
            }
        }
        return forChange;
    }
}
