package ru.progwards.java2.lessons.gc.heap;

public class InvalidPointerException extends Exception{
    @Override
    public String getMessage() {
        return "Invalid Pointer Exception.";
    }
}
