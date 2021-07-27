public class ClassLoadOrder {
    String str = "Какая-то строка";
    static {
        System.out.println("ClassLoadOrder статический блок инициализации");
    }
}
