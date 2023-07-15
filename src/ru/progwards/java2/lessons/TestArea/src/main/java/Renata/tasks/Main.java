package Renata.tasks;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * Метод по работе с TaskKeeper
 */
public class Main {
    public static void main(String[] args) {
        boolean toCont = true;
        Scanner scanner = new Scanner(System.in);
        TaskKeeper tk = new TaskKeeper();
        while (toCont) {
            System.out.println("Enter your number:");
            System.out.println("1 - create");
            System.out.println("2 - delete");
            System.out.println("3 - edit");
            System.out.println("4 - get all tasks");
            System.out.println("5 - get one task");
            System.out.println("6 - other actions");
            System.out.println("7 - exit");
            String answer = scanner.nextLine();
            switch (answer) {
                case "1":
                    tk.create();
                    break;
                case "2":
                    tk.delete();
                    break;
                case "3":
                    tk.edit();
                    break;
                case "4":
                    System.out.println(tk.getAllTasks());
                    break;
                case "5":
                    Task task = tk.getTask();
                    if (task != null) {
                        System.out.println(task);
                    } else {
                        System.out.println("Cannot find this task.");
                    }
                    break;
                case "6":
                    boolean toContEntry = true;
                    while (toContEntry) {
                        System.out.println("Enter your number:");
                        System.out.println("1 - get all finished tasks.");
                        System.out.println("2 - get all not finished tasks");
                        System.out.println("3 - get all finished tasks by the date of finish");
                        System.out.println("4 - get all not finished tasks by the date of finish");
                        System.out.println("5 - get all finished tasks by start date");
                        System.out.println("6 - get all not finished tasks by start date");
                        System.out.println("7 - return back");
                        String entryAnswer = scanner.nextLine();
                        switch (entryAnswer) {
                            case "1":
                                System.out.println(tk.getAllFinishedTasks());
                                break;
                            case "2":
                                System.out.println(tk.getAllNotFinishedTasks());
                                break;
                            case "3":
                                LocalDate ld = tk.createDate(tk.localDatePreparation(), false);
                                if (ld == null) {
                                    System.out.println("Incorrect!");
                                    toContEntry = false;
                                } else {
                                    System.out.println(tk.getAllFinishedTasksByDate(ld));
                                }
                                break;
                            case "4":
                                ld = tk.createDate(tk.localDatePreparation(), false);
                                if (ld == null) {
                                    System.out.println("Incorrect!");
                                    toContEntry = false;
                                } else {
                                    System.out.println(tk.getAllNotFinishedTasksByDate(ld));
                                }
                                break;
                            case "5":
                                ld = tk.createDate(tk.localDatePreparation(), false);
                                if (ld == null) {
                                    System.out.println("Incorrect!");
                                    toContEntry = false;
                                } else {
                                    System.out.println(tk.getAllFinishedTasksByStartDate(ld));
                                }
                                break;
                            case "6":
                                ld = tk.createDate(tk.localDatePreparation(), false);
                                if (ld == null) {
                                    System.out.println("Incorrect!");
                                    toContEntry = false;
                                } else {
                                    System.out.println(tk.getAllNotFinishedTasksByStartDate(ld));
                                }
                                break;
                            case "7":
                                toContEntry = false;
                                break;
                            default:
                                System.out.println("Something's going wrong, try again...");
                        }
                    }
                    break;
                case "7":
                    System.out.println("Thank you for using my app!");
                    toCont = false;
                    break;
                default:
                    System.out.println("Something's going wrong, try again...");
            }
        }
    }
}
