package ru.progwards.java2.lessons.testArea;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleAutoLoader extends ClassLoader {
    public static final String DOT_CLASS = ".class";
    public static final String PATH_OF_TASKS = "d:/progwards/java2/"; // местонахождение нашего проекта
    private final String basePath;
    private static SimpleAutoLoader loader = new SimpleAutoLoader(PATH_OF_TASKS); // внутри создаём экземпляр ClassLoader, которым будем дальше пользоваться

    public SimpleAutoLoader(String basePath) { // конструктор, вызывающий второй конструктор
        this(basePath, ClassLoader.getSystemClassLoader());
    }

    public SimpleAutoLoader(String basePath, ClassLoader parent) { // конструктор инициализирует ClassPath через родительский класс, а также присваивает в basePath адрес папки, где будут храниться классы
        super(parent);
        this.basePath = basePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException { // в качестве параметра передаётся полное имя класса
        try {
            String classpath = name.replace(".", "/"); // подготавливаем адрес, заменяя слэши на точки
            Path className = Paths.get(basePath + classpath + DOT_CLASS); // формируем адрес в формате Path (местонахождение основной папки + название класса + .class)
            if (Files.exists(className)) { // если таковой адрес действительно существует
                byte[] b = Files.readAllBytes(className); // получаем массив байт из указанного класса
                return defineClass(name, b, 0, b.length); // вызываем defineClass, куда мы передаём полное имя класса, получившийся массив байт, от ноля до крайнего значения массива; название класса будет name
            } else { // если будет совершена попытка загрузить иной класс (например, системный или любой иной, который не лежит в данном каталоге)
                return findSystemClass(name); // то тогда делегируем загрузку системному загрузчику класса (с дальнейшим делегированием по необходимости, которое прописано в системе classLoader-ов
            }
        } catch (IOException e) {
            throw new ClassNotFoundException();
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> cl = findClass(name); // ищем класс. Примечательно, что мы в первую очередь мы просматриваем новые классы в нашем каталоге, а уже потом (если такого адреса с классом не найдено) ищем классы при помощи других ClassLoader-ов.
        if (resolve) {
            resolveClass(cl); // метод выбросит исключение, если класс не найден.
        }
        return cl;
    }


    private static void updateTaskList(Map<String, Task> tasks) throws IOException {
        Files.walkFileTree(Paths.get(SimpleAutoLoader.PATH_OF_TASKS), new SimpleFileVisitor<>() { // используем проход по дереву файлов
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                if (path.toString().endsWith(SimpleAutoLoader.DOT_CLASS)) { // проверяем, является ли загруженный класс байт-кодом (т.е. заканчивается на ".class")
                    String className = null; // формируем имя класса на основе найденного пути
                    try {
                        className = makeClassName(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Task task = tasks.get(className); // используем это имя для поиска в списке задач
                    if (task == null) { // если находим - то делать ничего не нужно (в этой версии программы мы не модифицируем классы - какой загружен, такой и остаётся до конца работы программы). А вот если нет -
                        try {
                            Class taskClass = loader.loadClass(className, true); // загружаем при помощи ClassLoader этот класс
                            Task newTask = (Task) taskClass.getDeclaredConstructor().newInstance(); // создаёим экземпляр этого класса (через рефлексию)
                            tasks.put(className, newTask); // добавляем имя и экземпляр в список классов
                            System.out.println("Добавлен новый класс " + className);
                        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static String makeClassName(Path path) throws IOException {
        path = path.toAbsolutePath().toRealPath(); // получаем полный маршрут, без точек и относительностей
        Path relPath = Paths.get(PATH_OF_TASKS).relativize(path); // получаем именно package
        String className = relPath.toString().replaceAll("[\\/\\\\]]", "."); // заменяем \ или / на точку
        if (className.toUpperCase().endsWith(DOT_CLASS)) {
            className = className.substring(0, className.length() - DOT_CLASS.length()); // убираем .class
        }
        return className;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        Map<String, Task> tasks = new LinkedHashMap<>(); // создаём хранилище подгружаемых классов
        while(true) {
            System.out.println("Проверка классов и запуск задач:");
            System.out.printf("%1$tI:%1$tM:%1$tS.%1$tN", new Date());
            updateTaskList(tasks); // запуск проверки новых классов в директории
            SecureRandom random = new SecureRandom();
            byte[] data = new byte[1000];
            random.nextBytes(data);
            for(var task : tasks.entrySet()) { // вынимаем все Task из нашей коллекции
                System.out.println("    " + task.getValue().process(data)); //... и у каждого вызываем метод process
            }
            Thread.sleep(5000);
        }
    }

}
