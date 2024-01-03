package com.maslov.worklistproject.repo;

import com.maslov.worklistproject.entity.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.config.Task;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
    @Query("SELECT t FROM Tasks t WHERE t.name = :name")
    public Tasks findTasksByNameIs(@Param("name") String name);


}
