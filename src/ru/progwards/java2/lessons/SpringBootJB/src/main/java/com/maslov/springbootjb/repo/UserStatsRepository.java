package com.maslov.springbootjb.repo;

import com.maslov.springbootjb.entity.UserStats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStatsRepository extends CrudRepository<UserStats, Long> {

    // Получение всех элементов типа UserStats по значению поля finished
    @Query("SELECT u FROM UserStats u WHERE u.finished=:finished")
    List<UserStats> findUserStatsByFinishedIs(@Param("finished") long finished);

    // Получение элемента типа UserStats по полю email объекта UserData, у которых совпадают UserData.id и UserStats.usersid
    @Query("SELECT u FROM UserStats u WHERE u.usersid=(SELECT d.id FROM UserData d WHERE d.email=:email)")
    UserStats findUserStatsByUserEmail(@Param("email") String email); // возвращает только один объект UserStats (не коллекцию)

}
