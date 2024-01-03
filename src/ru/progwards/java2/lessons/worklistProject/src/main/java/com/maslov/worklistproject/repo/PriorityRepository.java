package com.maslov.worklistproject.repo;

import com.maslov.worklistproject.entity.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PriorityRepository extends JpaRepository<Priority, Long> {
    @Query("SELECT p FROM Priority p")
    public List<Priority> findAll();
}
