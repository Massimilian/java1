package arina.easyAll;

public class FriendlyNumbers {
    public static int COUNT = 100000;

    public static void main(String[] args) {
        FriendlyNumbers fn = new FriendlyNumbers();
        fn.findAll(10);
    }

    public void findAll(int n) {
        int count = 0;
        for (int i = 0; i < Integer.MAX_VALUE - COUNT; i++) {
            for (int j = i; j < i * 1.3; j++) {
                Friends temp = new Friends(i, j);
                if (temp.reallyFriends()) {
                    System.out.println(++count + ". " + temp);
                    if (count == n) {
                        break;
                    }
                }
            }
            if (count == n) {
                break;
            }
        }
    }
}
