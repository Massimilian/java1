package ru.progwards.java1.lessons.basics;

import org.junit.Assert;

import static org.hamcrest.CoreMatchers.is;

public class ReverseDigits {
    public static int reverseDigitsThrowString(int number) {
        String s = Integer.toString(number);
        StringBuilder result = new StringBuilder();
        for (int i = s.length() - 1; i >= 0; i--) {
            result.append(s.charAt(i));
        }
        return Integer.valueOf(String.valueOf(result));
    }

    public static int reverseDigits(int number) {
        int result = 0;
        if (number > 999 || number < 100) {
            result = reverseDigitsThrowString(number);
        } else {
            result = (number % 100 % 10)*100 + (number % 100 - number % 100 % 10) + (number - number % 100)/100;
        }
        return result;
    }


    public static void main(String[] args) {
        Assert.assertTrue(reverseDigits(234) == 432);
        Assert.assertTrue(reverseDigits(23) == 32);
        Assert.assertTrue(reverseDigits(1234) == 4321);
        Assert.assertEquals(reverseDigits(13579), 97531);
        Assert.assertThat(reverseDigits(1234567890), is (987654321));
    }
}
