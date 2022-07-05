import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        sum(5, 5);

        text("Программирование на Java - это легко!");
        text("Java - это ООП язык.");
        text("Java - это кроссплатформленность!");

        for (int j = 0; j<10; j++) {
            System.out.println("Я хочу создавать крутые игры, и для этого изучаю Java");
        }
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println("С Java я буду зарабатывать " + n + " тысяч рублей в час.");
    }
    public static void sum(int a, int b) {
        int c = a + b;
        System.out.println(c);
    }
    public static void text(String text) {
        for (int i=0; i<2; i++) {
            System.out.println(text);
        }
        System.out.println("");
    }
}