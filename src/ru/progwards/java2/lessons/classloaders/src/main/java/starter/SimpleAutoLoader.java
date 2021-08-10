package starter;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.SecureRandom;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleAutoLoader extends ClassLoader {
    final static String PATH_OF_TASKS = "c:/data"; // создаём базовый адрес, каталог для плагинов
    final static String DOT_CLASS = ".class"; // пометка расширения для типа класса - скомпилированного файла Java
    private static SimpleAutoLoader loader = new SimpleAutoLoader(PATH_OF_TASKS); // внутренний экземпляр ClassLoader с уже прописанным basePath.

    private final String basePath;

    public SimpleAutoLoader(String basePath) { // конструктор, вызывающий второй конструктор
        this(basePath, ClassLoader.getSystemClassLoader());
    }

    public SimpleAutoLoader(String basePath, ClassLoader parent) { // в этот конструктор передаём basepath и, если не указан ClassLoader, передаём системный ClassLoader
        super(parent);
        this.basePath = basePath;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            String classPath = className.replace(".", "/"); // в адресе заменяем все точки на слэши
            Path classPathName = Paths.get(basePath + classPath + DOT_CLASS); // формируем полный адрес класса
            if (Files.exists(classPathName)) { // если таковой адрес действительно существует
                byte b[] = Files.readAllBytes(classPathName); // побайтово считываем
                return defineClass(className, b, 0, b.length);// вызываем defineClass, куда мы передаём полное имя класса, получившийся массив байт, от ноля до крайнего значения массива; название класса будет name
            } else {// если будет совершена попытка загрузить иной класс (например, системный или любой иной, который не лежит в данном каталоге)
                return findSystemClass(className); // то тогда делегируем загрузку системному загрузчику класса (с дальнейшим делегированием по необходимости, которое прописано в системе classLoader-ов
            }
        } catch (IOException ex) {
            throw new ClassNotFoundException(className);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findClass(name); // ищем класс. Примечательно, что мы в первую очередь мы просматриваем новые классы в нашем каталоге, а уже потом (если такого адреса с классом не найдено) ищем классы при помощи других ClassLoader-ов.
        if (resolve) {
            resolveClass(c);  // метод выбросит исключение, если класс не найден.
        }
        return c;
    }

    private static String makeClassName(Path path) throws IOException {
        path = path.toAbsolutePath().toRealPath(); // получаем полный маршрут, без точек("..") и относительностей
        Path relPath = Paths.get(PATH_OF_TASKS).relativize(path); // создаёт "релятивный" путь (т.е. путь без PATH_OF_TASKS)
        String className = relPath.toString().replaceAll("[\\/\\\\]", "."); // заменяем \ или / на точку
        if (className.toLowerCase().endsWith(DOT_CLASS)) { // если всё заканчивается на .class
            className = className.substring(0, className.length() - DOT_CLASS.length()); // убираем .class, если это необходимо (в идеале, это необходимо всегда)
        }
        return className;
    }

    private static void updateTaskList(Map<String, Task> tasks) throws IOException {
        Files.walkFileTree(Paths.get(PATH_OF_TASKS), new SimpleFileVisitor<Path>() { // используем проход по дереву файлов
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if (path.toString().endsWith(DOT_CLASS)) { // проверяем, является ли загруженный класс байт-кодом (т.е. заканчивается на ".class")
                    String className = makeClassName(path); // формируем имя класса на основе найденного пути
                    Task task = tasks.get(className); // используем это имя для поиска в списке задач
                    if (task == null) {// если находим - то делать ничего не нужно (в этой версии программы мы не модифицируем классы - какой загружен, такой и остаётся до конца работы программы). А вот если нет -
                        try {
                            Class taskClass = loader.loadClass(className, true); // загружаем при помощи ClassLoader этот класс
                            Task newTask = (Task) taskClass.getDeclaredConstructor().newInstance(); // при помощи рефлексии создаём экземпляр класса
                            tasks.put(className, newTask); // кладём его в список классов tasks
                            System.out.println("Добавлен класс " + className);
                        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
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

    public static void main(String[] args) throws Exception {
        Map<String, Task> tasks = new LinkedHashMap<>(); // здесь мы будем хранить список названий классов и сами экземпляры классов
        loader.updateTaskList(tasks);
        while (true) {
            System.out.println("Проверка классов и запуск задач: ");
            System.out.printf("%1$tI:%1$tM:%1$tS.%1$tN", new Date());
            System.out.println();

            updateTaskList(tasks); // обновляем список классов, которые мы подгрузили

            SecureRandom random = new SecureRandom();
            byte[] data = new byte[1000];
            random.nextBytes(data); // создаём объём byte для отработки классов

            for (var task : tasks.entrySet())
                System.out.println("     " + task.getValue().process(data)); // запускаем все классы подряд

            Thread.sleep(5_000);
        }
    }
}
