package com.maslov.springbootjb.repo;

import com.maslov.springbootjb.entity.Tasks;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

    // Метод по поиску Tasks по полю userDataId, упорядочивающий по полю name
    @Query("SELECT t FROM Tasks t WHERE t.userDataId = :id")
    List<Tasks> findByUserDataIdOrderByNameAsc(@Param("id") int id);

    // Метод по поиску Tasks по полю dateandtime, принимающее два значения и ищущее все значения в промежутке
    @Query("SELECT t FROM Tasks t WHERE t.dateandtime >= :from AND t.dateandtime <= :to")
    List<Tasks> findByDateandtimeBetween(@Param("from") Timestamp from, @Param("to") Timestamp to);

    // Метод по поиску Tasks по имени (name)
    @Query("SELECT t FROM Tasks t WHERE lower(t.name) = lower(:name)")
    List<Tasks> findByNameIgnoreCase(@Param("name") String name);

    // Метод по поиску Tasks по полю mail пользователя
    @Query("SELECT t FROM Tasks t WHERE t.userDataId = (SELECT u.id FROM UserData u WHERE u.email = :email)")
    List<Tasks> findByUserEmail(@Param("email") String mail);

    // метод постраничного вывода Tasks, в которых совпадут либо окажутся нолевыми некоторые параметры (если нолевыми окажутся все параметры - буду выведены все Tasks)
    @Query("SELECT t FROM Tasks t WHERE" + // выбрать все Tasks в которых...
            "(:name IS NULL OR LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " + // либо name нолевое, либо имя совпадает со входящим параметром, не считая регистра и пробелов до и после; кроме того, ...
            "(:done IS NULL OR t.done=:done) AND " + // либо параметр done нолевой, либо совпадает с передаваемым параметром, кроме того...
            "(:categoryId IS NULL OR t.categoryId=:categoryId) AND " + // либо параметр categoryId нолевой, либо равен аналогичному в Tasks, кроме того...
            "(:priorityId IS NULL OR t.priorityId=:priorityId) AND " + // либо параметр priorityId нолевой, либо равен аналогичному в Tasks, кроме того...
            "((cast(:dateFrom as timestamp) IS NULL OR t.dateandtime>=:dateFrom) AND" + // параметр userDataId должен быть больше либо равен параметру dateFrom
            " cast(:dateTo as timestamp) IS NULL OR t.dateandtime<=:dateTo)") // параметр userDataId должен быть больше либо равен параметру dateFrom
    Page<Tasks> findByParams(@Param("name") String name,
                             @Param("done") Boolean done,
                             @Param("categoryId") Integer categoryId,
                             @Param("priorityId") Integer priorityId,
                             @Param("dateFrom") Date dateFrom,
                             @Param("dateTo") Date dateTo,
                             Pageable pageable); // дополнительные настройки для постраничного вывода
}
