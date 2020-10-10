package ru.progwards.java1.lessons;

public class Test {
    public static void main(String[] args) {
        double precition = 0.00000001; // вводим понятие точности
        double d = 5.1234;
        System.out.println(d%1 == 0.1234); // сравниваем полученную математическим способом найденную дробную часть числа d с интуитивно найденной частью - и получаем, что они неравны (false).
        System.out.println(Math.abs(d%1 - 0.1234) < precition); // делаем то же самое с поправкой на точность - и получаем true
    }
}
