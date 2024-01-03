package com.maslov.worklistproject.repo;

import com.maslov.worklistproject.entity.UserStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface UserStatsRepository extends JpaRepository<UserStats, Long> {

    public Set<UserStats> findUserStatsByFinishedEquals(Long finished);

    @Query("SELECT s FROM UserStats s WHERE s.usersId = (SELECT u.id FROM UserData u WHERE u.email=:email)")
    public UserStats findUserStatsByUserEmail(@Param("email") String email);
}
