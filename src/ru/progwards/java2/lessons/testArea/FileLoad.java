package ru.progwards.java2.lessons.testArea;

public class FileLoad extends ClassLoader{
    final static String PATH_OF_TASKS = "d:/progwards";
    final static String DOT_CLASS = ".class";
//    private static FileLoad fl = new FileLoad(String )


    @Override
    public Class<?> findClass(String className) {
        String classPath = className.replace(".", "/");
        return null;
    }

    public static void main(String[] args) {

    }
}
