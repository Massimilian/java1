package ru.maslov.spring8.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.maslov.spring8.entity.Task;
import ru.maslov.spring8.entity.Userdata;

public interface TaskRepository extends JpaRepository<Task, Integer> {

    @Query("SELECT t FROM Task t")
    Page<Task> getAllTasks(Pageable pageable);

    @Query("SELECT ud FROM Userdata ud WHERE ud.login = :login")
    Userdata getAuthor(String login);
}