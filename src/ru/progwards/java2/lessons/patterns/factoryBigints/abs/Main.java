package ru.progwards.java2.lessons.patterns.factoryBigints.abs;

/**
 * Special class for check all number types.
 */
public class Main {
    public static void main(String[] args) throws OutOfClassPosibilitesException {
        Typer typerInt = new Typer(100000000L);
        Typer typerShort = new Typer(10000L);
        Typer typerByte = new Typer(-120L);
        Number n = NumberFactory.getInstance().fabric(typerInt).getValue();
        assert n.getClass().getSimpleName().equals("Integer");
        n = NumberFactory.getInstance().fabric(typerShort).getValue();
        assert n.getClass().getSimpleName().equals("Short");
        n = NumberFactory.getInstance().fabric(typerByte).getValue();
        assert n.getClass().getSimpleName().equals("Byte");
    }
}
