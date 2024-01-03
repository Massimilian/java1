package com.example.testmock.service;

import com.example.testmock.entity.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
    Данная программа тестирует класс TaskService, внутри которого есть обращение к БД.
    К самой БД у нас, по условию задачи, нет доступа; класс, работающий с БД, мы не можем менять.
    Проще всего сделать тестирование при помощи библиотеки Mockito.
    Но также можно сделать тестирование, не имея доступа к БД и не используя библиотеку Mockito.
    Суть - мы подменяем объект внутри TaskService на наследника класса, работающего с БД.
    Сделать это можно при помощи Reflextion API.
    Результат - проведённое тестирование без использования библиотеки Mockito.
 */
public class JUnitTest {
    Object mocked;
    Class clazz;

    @BeforeEach
    private void setReflection() throws ClassNotFoundException, NoSuchMethodException, NoSuchFieldException, IllegalAccessException, InvocationTargetException, InstantiationException {
        this.clazz = Class.forName("com.example.testmock.service.TaskService");
        this.mocked = clazz.getConstructor().newInstance();
        Field repo = clazz.getDeclaredField("repository");
        repo.setAccessible(true);
        repo.set(mocked, new TaskMockRepository());
    }

    @Test
    public void testGet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method add = clazz.getDeclaredMethod("get", Long.class);
        Task t = (Task) add.invoke(mocked, 1l);
        Assertions.assertTrue(t != null);
    }

    @Test
    public void testUpdate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method update = clazz.getDeclaredMethod("update", Task.class);
        Assertions.assertTrue((Boolean) update.invoke(mocked, new Task()));
    }

    @Test
    public void testDelete() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method delete = clazz.getDeclaredMethod("delete", Long.class);
        Long id = 12l;
        Task t = (Task) delete.invoke(mocked, id);
        Long newId = t.getId();
        Assertions.assertTrue(t != null);
        Assertions.assertEquals(id, newId);
    }

    @Test
    public void testGetById() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method getInfo = clazz.getDeclaredMethod("getById", int.class);
        int id = 11111;
        Task t = (Task) getInfo.invoke(mocked, id);
        Assertions.assertNotNull(t);
        Assertions.assertInstanceOf(Task.class, t);
        Assertions.assertEquals(t.getId(), id);
    }
}
