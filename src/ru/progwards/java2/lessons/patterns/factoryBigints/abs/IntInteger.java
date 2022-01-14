package ru.progwards.java2.lessons.patterns.factoryBigints.abs;

public class IntInteger implements AbsNumber {
    private int value;

    public IntInteger(int typer) {
        this.value = typer;
    }

    @Override
    public Number getValue() {
        System.out.printf("Required type is %s (%d).%s", this.getClass().getSimpleName(), value, System.lineSeparator());
        return value;
    }
}
