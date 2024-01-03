package com.example.testmock.service;

import com.example.testmock.entity.Task;
import com.example.testmock.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class MockTest {
    // куда будет внедряться Mock
    @InjectMocks
    TaskService service = new TaskService();

    // Превращаем объект для внедрения в Mock
    @Mock
    TaskRepository mock;

    @Test
    public void testGetMethod() {
        Mockito.when(mock.getInfo("GET")).thenReturn("in get method");
        Assertions.assertEquals(service.testget(), "in get method");
    }

    @Test
    public void testGetById() {
        Mockito.when(mock.getById(0)).thenReturn(new Task());
        Assertions.assertNotNull(service.getById(0));
    }

    @Test
    public void testGet() {
        Mockito.when(mock.get()).thenReturn(new ArrayList<>(List.of(new Task(), new Task())));
        Assertions.assertEquals(service.get().size(), 2);
    }

    @Test
    public void testDelete() {
        Mockito.when(mock.delete(0L)).thenReturn(new Task());
        Assertions.assertFalse(service.delete(0L) == null);
    }

}
