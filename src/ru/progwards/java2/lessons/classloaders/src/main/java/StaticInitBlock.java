public class StaticInitBlock {
    static String str = "Инициализация по умолчанию";

    static {
        System.out.println("Запущен статический блок инициализации");
        System.out.println("    str = \"" + str + "\"");
        str = "Строка str переопределена в статическом блоке";
        System.out.println("Статический блок инициализации завершён");
    }

    public static void main(String[] args) {
        System.out.println("Точка входа StaticInitBlock.main()");
        System.out.println("    str = \"" + str + "\"");
        System.out.println(new ClassLoadOrder().str);
    }
}
