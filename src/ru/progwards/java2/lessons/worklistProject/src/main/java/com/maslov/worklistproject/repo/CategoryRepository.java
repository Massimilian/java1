package com.maslov.worklistproject.repo;

import com.maslov.worklistproject.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE LOWER(c.catname) = LOWER(:catname)")
    public Category findCategoryByCatnameIs(@Param("catname") String catname);

    @Query("SELECT c FROM Category c WHERE c.inProcess >= :less AND c.inProcess <= :great")
    public Category[] findCategoryByInProcessGreaterThanEqualAndInProcessLessThanEqual(@Param("less") int less, @Param("great") int great);

    @Query("SELECT c FROM Category c WHERE c.finished = :count")
    public Category[] findCategoryByFinishedIs(@Param("count") int count);

    public List<Category> findByIdIs(long id);

    public List<Category> findCategoryByIdGreaterThanEqualAndIdLessThanEqual(long min, long max);
}
