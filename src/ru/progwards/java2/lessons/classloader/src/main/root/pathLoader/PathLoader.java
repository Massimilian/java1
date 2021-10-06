package pathLoader;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PathLoader extends ClassLoader {

    final static String PATH_OF_TASKS = "c:/data";
    final static String DOT_CLASS = ".class";
    private static PathLoader loader = new PathLoader(PATH_OF_TASKS);

    private final String basePath;

    public PathLoader(String basePath) {
        this(basePath, ClassLoader.getSystemClassLoader());
    }

    public PathLoader(String basePath, ClassLoader parent) {
        super(parent);
        this.basePath = basePath;
    }

    @Override
    public Class<?> findClass(String className) throws ClassNotFoundException {
        try {
            String classPath = className.replace(".", "/");
            Path classPathName = Paths.get(basePath + classPath + DOT_CLASS);
            if (Files.exists(classPathName)) {
                byte b[] = Files.readAllBytes(classPathName);
                return defineClass(className, b, 0, b.length);
            } else {
                return findSystemClass(className);
            }
        } catch (IOException ex) {
            throw new ClassNotFoundException(className);
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> c = findClass(name);
        if (resolve) {
            resolveClass(c);
        }
        return c;
    }


    private static String makeClassName(Path path, String place) throws IOException {
        path = path.toAbsolutePath().toRealPath();
        Path relPath = Paths.get(place).relativize(path);
        String className = relPath.toString().replaceAll("[\\/\\\\]", ".");
        if (className.toLowerCase().endsWith(DOT_CLASS)) {
            className = className.substring(0, className.length() - DOT_CLASS.length());
        }
        return className;
    }

    private static void updateTaskList(final Map<String, Object> tasks, String place, HashSet<ClassKeeper> keepedObjects) throws IOException {
        Files.walkFileTree(Paths.get(place), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if (path.toString().endsWith(DOT_CLASS)) {
                    String className = makeClassName(path, place);
                    Object task = tasks.get(className);
                    try {
                        if (task == null) {
                            Class taskClass = loader.loadClass(className, true);
                            Object newTask = taskClass.getDeclaredConstructor().newInstance();
                            tasks.put(className, newTask);
                            if (keepedObjects.add(new ClassKeeper(className, place))) {
                                logging(path.toString(), "");
                            }
                            System.out.println(task == null ? "Добавлен" : "Загружен" + " класс " + className);
                        }
                    } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                        String mistake = e.getMessage();
                        logging(path.toString(), mistake);
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            private void logging(String fullClassName, String mistake) {
                try {
                    String result;
                    Path path = Paths.get(PATH_OF_TASKS + "/patchloader.log");
                    if (Files.notExists(path)) {
                        Files.createFile(path);
                    }
                    LocalDateTime ldt = LocalDateTime.now();
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy kk.mm.ss");
                    if (mistake.isEmpty()) {
                        result = String.format("%s%s %s загружен из %s успешно.%s", Files.readString(path), dtf.format(ldt), fullClassName.substring(PATH_OF_TASKS.length() + 1), PATH_OF_TASKS, System.lineSeparator());
                    } else {
                        result = String.format("%s%s %s ошибка загрузки %s%s", Files.readString(path), dtf.format(ldt), fullClassName.substring(PATH_OF_TASKS.length() + 1), mistake, System.lineSeparator());
                    }
                    Files.writeString(path, result);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException e) {
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> tasks = new HashMap<>();
        HashSet<ClassKeeper> keepedObjects = new HashSet<>();
        SortedSet<String> folders = new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        while (true) {
            tasks.clear();
            Files.walkFileTree(Paths.get(PATH_OF_TASKS), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    folders.add(dir.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
            folders.remove(Paths.get(PATH_OF_TASKS).toString());
            Iterator<String> iterator = folders.iterator();
            while (iterator.hasNext()) {
                String temp = iterator.next();
                loader.updateTaskList(tasks, temp, keepedObjects);
            }
            Thread.sleep(1_000);
        }
    }
}