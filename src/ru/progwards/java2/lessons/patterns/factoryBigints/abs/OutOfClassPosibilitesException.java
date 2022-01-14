package ru.progwards.java2.lessons.patterns.factoryBigints.abs;

/**
 * Exception when the number is too big/small.
 */
public class OutOfClassPosibilitesException extends Exception{
    public OutOfClassPosibilitesException(String message) {
        super(message);
    }
}
