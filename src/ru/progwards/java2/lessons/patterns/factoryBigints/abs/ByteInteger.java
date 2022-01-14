package ru.progwards.java2.lessons.patterns.factoryBigints.abs;

public class ByteInteger implements AbsNumber {
    private byte value;

    public ByteInteger(byte typer) {
        this.value = typer;
    }

    @Override
    public Number getValue() {
        System.out.printf("Required type is %s (%d).%s", this.getClass().getSimpleName(), value, System.lineSeparator());
        return value;
    }
}
