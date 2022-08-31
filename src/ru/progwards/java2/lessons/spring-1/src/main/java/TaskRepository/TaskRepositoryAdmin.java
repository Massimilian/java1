package TaskRepository;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class TaskRepositoryAdmin {
    TaskRepository tr;
    Scanner scanner = new Scanner(System.in);

    public TaskRepositoryAdmin(TaskRepository tr) {
        this.tr = tr;
    }

    public boolean work() {
        boolean toCont = true;
        boolean correct;
        do {
            System.out.println("Enter the name of operation: ADD - put new task; UPDATE - renew task by id-number;" +
                    " DELETE - delete task; GET - get all tasks; GETID - get task by id; EXIT - exit.");
            String act = scanner.nextLine().toUpperCase();
            switch (act) {
                case "ADD":
                    tr.save(new Task(true));
                    correct = true;
                    break;
                case "UPDATE":
                    tr.update(new Task(numberCreator()));
                    correct = true;
                    break;
                case "DELETE":
                    tr.delete(numberCreator());
                    correct = true;
                    break;
                case "GET":
                    List<Task> list = tr.get();
                    if (list.isEmpty()) {
                        System.out.println("Tasks not found!");
                    } else {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println(list.get(i).toString());
                        }
                    }
                    correct = true;
                    break;
                case "GETID":
                    Task task = tr.get(numberCreator());
                    if (task == null) {
                        System.out.println("Tasks not found!");
                    } else {
                        System.out.println(task);
                    }
                    correct = true;
                    break;
                case "EXIT":
                    toCont = false;
                    correct = true;
                    break;
                default:
                    System.out.println("Incorrect operation!");
                    correct = false;
            }
        } while (!correct);
        return toCont;
    }

    private String numberCreator() {
        boolean isntCorrectId = true;
        String number = "";
        while (isntCorrectId) {
            System.out.println("Enter id-number of task:");
            number = scanner.nextLine();
            if (Pattern.matches("[0-9]", number)) {
                isntCorrectId = false;
            }
        }
        return number;
    }
}
