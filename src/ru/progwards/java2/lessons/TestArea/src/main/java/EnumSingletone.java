public enum EnumSingletone {
    INSTANCE(true, 1, "Строка"); // перечисление по умолчанию

    // некая информация, хранимая в классе
    private boolean bool;
    private int integer;
    private String str;
    private Tester test; // как включить это поле внутрь INSTANCE?

    private EnumSingletone(boolean bool, int integer, String str) {
        this.bool = bool;
        this.integer = integer;
        this.str = str;
    }
}

class Tester {
}