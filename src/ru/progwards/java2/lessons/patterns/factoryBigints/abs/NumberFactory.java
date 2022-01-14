package ru.progwards.java2.lessons.patterns.factoryBigints.abs;

/**
 * NumberFactory Singleton-type-made.
 */
public class NumberFactory {

    private static NumberFactory INSTANCE = new NumberFactory();

    public static NumberFactory getInstance() {
        return INSTANCE;
    }

    private NumberFactory() {
    }

    public AbsNumber fabric(Typer typer) throws OutOfClassPosibilitesException {
        AbsNumber abs;
        if (typer.getValue() > Integer.MAX_VALUE || typer.getValue() < Integer.MIN_VALUE) {
            throw new OutOfClassPosibilitesException(String.format("Impossible operation with number %d.", typer.getValue()));
        } else if (typer.getValue() > Short.MAX_VALUE || typer.getValue() < Short.MIN_VALUE) {
            abs = new IntInteger((int) typer.getValue());
        } else if (typer.getValue() > Byte.MAX_VALUE || typer.getValue() < Byte.MIN_VALUE) {
            abs = new ShortInteger((short) typer.getValue());
        } else {
            abs = new ByteInteger((byte) typer.getValue());
        }
        return abs;
    }
}
