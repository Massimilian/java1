package com.maslov.springbootjb.repo;

import com.maslov.springbootjb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE LOWER(c.catname)=LOWER(:catName)")
    Category findCategoryByCatnameIgnoreCase(@Param("catName") String catName);


    // метод выбирает объекты category у которых параметр inProcess = передаваемому в аргументах параметру
    // связь передаваемого параметра осуществляется при помощи @Param - со стороны параметра и ":" - со стороны запроса
    @Query("SELECT c FROM Category c WHERE c.inProcess=:inProcess")
    List<Category> findCategoryByInProcessIs(@Param("inProcess") int inProcess);


}
