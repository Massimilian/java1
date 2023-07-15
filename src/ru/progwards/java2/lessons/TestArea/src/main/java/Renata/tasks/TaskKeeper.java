package Renata.tasks;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс по хранению, редактированию, удалению и добавлению задач (Task)
 */
public class TaskKeeper {

    // порядковый неповторяющийся номер задачи
    public static int number = 1;
    // хранилище задач
    ArrayList<Task> tasks = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    /**
     * Метод по созданию задачи (Task).
     *
     * @param isCreator внутренняя пометка о том, что этот метод возвращает Task
     * @return подготовленную задачу (Task)
     */
    public Task create(boolean isCreator) {
        System.out.println("Enter your task's name:");
        String name = scanner.nextLine();
        System.out.println("Enter your task's description:");
        String desc = scanner.nextLine();
        LocalDate ld = null;
        while (ld == null) {
            ld = createDate(localDatePreparation(), true);
            if (ld == null) {
                System.out.println("Dates are incorrect. Try again.");
            }
        }
        return new Task(ld, name, desc);
    }

    /**
     * Метод по подготовке данных для создания LocalDate
     *
     * @return упакованные в массив данные
     */
    public String[] localDatePreparation() {
        System.out.println("Enter your final date (numbers): year?");
        String year = scanner.nextLine();
        System.out.println(" ...month?");
        String month = scanner.nextLine();
        System.out.println("...day?");
        String day = scanner.nextLine();
        return new String[]{year, month, day};
    }

    /**
     * Метод по созданию и добавлению задачи (Task) в списрок задач
     *
     * @return подтверждение, что задача (Task) добавлена
     */
    public boolean create() {
        return this.tasks.add(this.create(true));
    }

    /**
     * Подготовка переменной типа LocalDate с загружаемыми данными от пользователя
     *
     * @param dates    данные от пользователя
     * @param isFinish пометка о том, что это финальная дата
     * @return подготовленная дата
     */
    public LocalDate createDate(String[] dates, boolean isFinish) {
        int realYear;
        int realMonth;
        int realDay;
        LocalDate finish;
        try {
            realYear = Integer.parseInt(dates[0]);
            realMonth = Integer.parseInt(dates[1]);
            realDay = Integer.parseInt(dates[2]);
            finish = LocalDate.of(realYear, realMonth, realDay);
        } catch (NumberFormatException | DateTimeException ex) {
            return null;
        }
        if (isFinish) {
            LocalDate nowMinusOneDay = LocalDate.now().minusDays(1);
            if (finish.isAfter(nowMinusOneDay)) {
                return finish;
            } else {
                return null;
            }
        } else {
            return finish;
        }
    }

    /**
     * Метод по удалению элементов из списка задач
     */
    public void delete() {
        if (this.tasks.size() != 0) {
            this.delete(this.getCorrectId());
        } else {
            System.out.println("There's no tasks for delete");
        }
    }

    /**
     * Метод по получению корректного id
     *
     * @return корректный id
     */
    public int getCorrectId() {
        int id = -1;
        while (id <= 0) {
            id = getIdFromUser();
            if (id <= 0) {
                System.out.println("Incorrect id, try again.");
            }
        }
        return id;
    }

    /**
     * Удаление задачи (Task) по полученному id.
     *
     * @param id номер задачи
     */
    public void delete(int id) {
        boolean isFound = false;
        for (int i = 0; i < this.tasks.size(); i++) {
            if (tasks.get(i).number == id) {
                isFound = true;
                Task task = tasks.get(i);
                System.out.println(task);
                System.out.println("If you really want to delete, enter 'DELETE'?");
                Scanner sc = new Scanner(System.in);
                String del = sc.nextLine();
                if (del.equals("DELETE")) {
                    tasks.remove(i);
                    System.out.println("Task deleted.");
                } else {
                    System.out.println("Task saved.");
                }
                break;
            }
        }
        if (!isFound) {
            System.out.println("There is no any task with this id - " + id);
        }
    }

    /**
     * Метод получение id от пользователя
     *
     * @return готовый id
     */
    private int getIdFromUser() {
        System.out.println("Enter correct id:");
        int id = -1;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException nfe) {
            System.out.println("Please, enter a number!");
            return -1;
        }
        return id;
    }

    /**
     * Метод по редактированию задачи (Task)
     */
    public void edit() {
        int id = this.getCorrectId();
        Task task = this.getTask(id);
        if (task != null) {
            System.out.println("Do you want to edit this task (enter YES)?");
            System.out.println(task);
            String answer = scanner.nextLine();
            if (answer.equals("YES")) {
                Task newTask = create(true);
                newTask.number = task.number;
                this.tasks.remove(task);
                this.tasks.add(newTask);
            }
        } else {
            System.out.println("There is no task with this number");
        }
    }

    /**
     * Метод по получению раскрытого списка задач (Task)
     *
     * @return развёрнутый список задач
     */
    public String getAllTasks() {
        StringBuilder sb = new StringBuilder();
        if (this.tasks.size() != 0) {
            for (int i = 0; i < this.tasks.size(); i++) {
                sb.append(tasks.get(i));
            }
        } else {
            sb.append("There's empty task list.");
        }
        return sb.toString();
    }

    /**
     * Метод по получению законченных задач (Task) по состоянию на текущую дату
     *
     * @return список задач (Task)
     */
    public String getAllFinishedTasks() {
        return getAllFinishedTasksByDate(LocalDate.now());
    }

    /**
     * Метод по получению незаконченных задач (Task) по состоянию на текущую дату
     *
     * @return список задач (Task)
     */
    public String getAllNotFinishedTasks() {
        return getAllNotFinishedTasksByDate(LocalDate.now());
    }

    /**
     * Метод по получению законченных задач (Task) по состоянию на вводимую дату
     *
     * @return список задач (Task)
     */
    public String getAllFinishedTasksByDate(LocalDate ld) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).finish.isBefore(ld)) {
                sb.append(tasks.get(i));
            }
        }
        return sb.toString();
    }

    /**
     * Метод по получению незаконченных задач (Task) по состоянию на вводимую дату
     *
     * @return список задач (Task)
     */
    public String getAllNotFinishedTasksByDate(LocalDate ld) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).finish.isBefore(ld)) {
                sb.append(tasks.get(i));
            }
        }
        if (sb.isEmpty()) {
            sb.append("There's no tasks");
        }
        return sb.toString();
    }

    /**
     * Метод по получению законченных задач (Task) с фиксированной стартовой датой
     *
     * @return список задач (Task)
     */
    public String getAllFinishedTasksByStartDate(LocalDate start) {
        StringBuilder sb = new StringBuilder();
        LocalDate now = LocalDate.now();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).start.equals(start) && !tasks.get(i).finish.isAfter(now)) {
                sb.append(tasks.get(i));
            }
        }
        if (sb.isEmpty()) {
            sb.append("There's no tasks");
        }
        return sb.toString();
    }

    /**
     * Метод по получению незаконченных задач (Task) с фиксированной стартовой датой
     *
     * @return список задач (Task)
     */
    public String getAllNotFinishedTasksByStartDate(LocalDate start) {
        StringBuilder sb = new StringBuilder();
        LocalDate now = LocalDate.now();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).start.equals(start) && tasks.get(i).finish.isAfter(now)) {
                sb.append(tasks.get(i));
            }
        }
        if (sb.isEmpty()) {
            sb.append("There's no tasks.");
        }
        return sb.toString();
    }

    /**
     * Получение задачи (Task) без фиксированного id
     *
     * @return задача (Task)
     */
    public Task getTask() {
        return this.getTask(this.getCorrectId());
    }

    /**
     * Получение задачи (Task) с фиксированным id
     *
     * @return задача (Task)
     */
    public Task getTask(int id) {
        Task task = null;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).number == id) {
                task = tasks.get(i);
                break;
            }
        }
        return task;
    }
}