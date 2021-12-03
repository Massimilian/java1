import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter first number:");
        int one = scanner.nextInt();
        System.out.println("Enter second number:");
        int two = scanner.nextInt();
        ArrayList<Integer> list = new ArrayList<>();
        list.add(one);
        list.add(two);
        System.out.println(list);
    }
}
