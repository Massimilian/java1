package renata.terminal;

public class Main {
    public static void main(String[] args) {
        Terminal terminal = new Terminal();
        Card school = terminal.createNewCard();
        Card social = terminal.createNewCard();
        Card student = terminal.createNewCard();
        terminal.pass(school);
        terminal.pass(school);
        terminal.pass(social);
        terminal.pass(student);
        terminal.pass(student);
        terminal.pass(student);
        System.out.println("@@@@@@@");
        System.out.println(terminal.getStatistics());
    }
}
