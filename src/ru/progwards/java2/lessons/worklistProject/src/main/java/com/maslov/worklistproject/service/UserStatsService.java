package com.maslov.worklistproject.service;

import com.maslov.worklistproject.entity.UserStats;
import com.maslov.worklistproject.repo.UserStatsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional
@AllArgsConstructor
public class UserStatsService {
    UserStatsRepository repo;

    public UserStats getInfoById(Long id) {
        try {
            return repo.findById(id).get();
        } catch (NoSuchElementException nsee) {
            return null;
        }
    }

    public Set<UserStats> findByFinished(Long finished) {
        return repo.findUserStatsByFinishedEquals(finished);
    }

    public UserStats findUserStatsByUserEmail(String email) {
        return repo.findUserStatsByUserEmail(email);
    }
}
