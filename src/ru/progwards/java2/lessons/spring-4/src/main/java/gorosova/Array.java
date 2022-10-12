package gorosova;

public class Array {
    public static void main(String[] args) {
        Array array = new Array();
        array.action(new long[]{5, 4, 3, 1024, 3000, 4000, 2, 6, 122, 123});
    }

    public void action(long[] array) {
        if (array.length < 3) {
            System.out.println("It is impossible to work with this array");
        } else {
            long[] nums = {Long.MIN_VALUE, Long.MIN_VALUE, Long.MIN_VALUE};
            for (int i = 0; i < array.length; i++) {
                renovateNumbers(array[i], nums);
            }
            long semiMax = nums[0];
            long[] arr = findDivisors(semiMax);
            this.printOddsToBig(arr);
            System.out.println("-------");
            this.printEvensFromBig(arr);
        }
    }

    private void printEvensFromBig(long[] arr) {
        System.out.println("Evens from the biggest:");
        for (int i = arr.length - 1; i >= 0; i--) {
            if (arr[i] % 2 == 0) {
                System.out.println(arr[i]);
            }
        }
    }

    private void printOddsToBig(long[] arr) {
        System.out.println("Odds from the smallest:");
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 != 0) {
                System.out.println(arr[i]);
            }
        }
    }

    private long[] findDivisors(long semiMax) {
        long[] divisors = new long[(int) (semiMax / 2)];
        int count = 0;
        for (int i = 1; i <= semiMax / 2; i++) {
            if (semiMax % i == 0) {
                divisors[count++] = i;
            }
        }
        divisors[count++] = semiMax;
        long[] result = new long[count];
        for (int i = 0; i < count; i++) {
            result[i] = divisors[i];
        }
        return result;
    }

    private void renovateNumbers(long l, long[] nums) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < l) {
                nums[i] = l;
                this.orden(nums);
//                Arrays.sort(nums);
                break;
            }
        }
    }

    private void orden(long[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                reput(nums, i, j);
            }
        }
    }

    private void reput(long[] nums, int i, int j) {
        if (nums[i] > nums[j]) {
            long temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
        }
    }
}
