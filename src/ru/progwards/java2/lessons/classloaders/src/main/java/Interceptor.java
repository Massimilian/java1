import java.lang.instrument.Instrumentation;

public class Interceptor {
    public static void premain(String agentArgument, Instrumentation instrumentation) { // аргументы - строка параметров, задающаяся в командной строке при указании Java-агента, и объект instrumentation, который позволяет низкойуровнеый доступ к объектам JVM
        System.out.println("Interceptor: premain стартовал"); // обозначим старт нашего класс-агента
        instrumentation.addTransformer(new SimpleTransformer()); // устанавливаем необходимый трансформер
        System.out.println("Interceptor: установлен SimpleTransformer"); // информируем об установке SimpleTransformer
        System.out.println("Interceptor: завершена работа"); // информируем об окончании работы
    }
}

