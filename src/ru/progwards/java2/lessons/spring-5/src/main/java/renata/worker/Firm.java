package renata.worker;

import java.io.*;
import java.util.ArrayList;

public class Firm {
    public final String fileName = "workers.txt";

    public static void main(String[] args) throws Exception {
        Firm firm = new Firm();
        firm.newWorker(new HourWorker(1, "Vasily", 200));
        firm.newWorker(new HourWorker(2, "Olga", 250));
        firm.newWorker(new MonthWorker(3, "Maia", 75000));
        firm.newWorker(new MonthWorker(4, "Elena", 15000));
        firm.newWorker(new MonthWorker(5, "Svetlana", 75000));
        firm.newWorker(new HourWorker(6, "Dinara", 200));
        firm.newWorker(new HourWorker(7, "Oleg", 200));
        System.out.println();
        firm.arrange();
        for (int i = 0; i < 5; i++) {
            System.out.println(firm.getWorkers().get(i).getName());
        }
        System.out.println("-------");
        for (int i = 0; i < firm.getWorkers().size(); i++) {
            System.out.println("Id: " + firm.getWorkers().get(firm.getWorkers().size() - 1 - i).getId());
            if (i == 2) {
                break;
            }
        }
        firm.write();
        ArrayList<Worker> w = firm.read();
        System.out.println();
    }

    private ArrayList<Worker> workers = new ArrayList<>();

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public void newWorker(Worker worker) {
        workers.add(worker);
    }

    public void arrange() {
        for (int i = 0; i < workers.size() - 1; i++) {
            for (int j = i + 1; j < workers.size(); j++) {
                if (workerComparator(workers.get(i), workers.get(j)) < 0) {
                    Worker temp = workers.get(i);
                    workers.set(i, workers.get(j));
                    workers.set(j, temp);
                }
            }
        }
    }

    public int workerComparator(Worker one, Worker two) {
        int result = 0;
        if (one.count() > two.count()) {
            result = 1;
        } else if (one.count() < two.count()) {
            result = -1;
        } else {
            result = two.getName().compareTo(one.getName());
        }
        return result;
    }

    public void write() throws IOException {
        String info = "@";
        for (int i = 0; i < workers.size(); i++) {
            info += workers.get(i).toString();
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
        bw.write(info);
        bw.flush();
    }

    public ArrayList<Worker> read() throws Exception {
        ArrayList<Worker> workers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
        String info = br.readLine();
        String[] infos = info.split("@");
        if ((infos.length % 4 - 1) != 0) {
            throw new Exception("Broken data.");
        }
        for (int i = 1; i < infos.length; i = i + 4) {
            String id = infos[i];
            String name = infos[i + 1];
            String getForPay = infos[i + 2];
            String type = infos[i + 3];
            if (this.isName(id) || this.isName(getForPay) || !this.checkType(type)) {
                throw new Exception("Impossible data.");
            } else {
                Worker temp;
                if (type.equals("MonthWorker")) {
                    temp = new MonthWorker(Integer.parseInt(id), name, Double.parseDouble(getForPay));
                } else {
                    temp = new HourWorker(Integer.parseInt(id), name, Double.parseDouble(getForPay));
                }
                workers.add(temp);
            }
        }
        return workers;
    }

    private boolean checkType(String type) {
        return type.equals("MonthWorker") || type.equals("HourWorker");
    }

    private boolean isName(String name) {
        boolean good = false;
        for (int i = 0; i < name.length(); i++) {
            if (!(Character.isDigit(name.charAt(i)) || name.charAt(i) == '.')) {
                good = true;
                break;
            }
        }
        return good;
    }
}
