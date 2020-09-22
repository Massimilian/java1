package ru.progwards.java2.lessons.gc_konst;

public class InvalidPointerException extends RuntimeException {
    public String commandName = "";

    public InvalidPointerException(String commandName, int commandLine) {
        super("Ошибка в строке # " + commandLine);
        this.commandName = commandName;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + (commandName == null ? "" : " \"" + commandName + "\"");
    }
}
