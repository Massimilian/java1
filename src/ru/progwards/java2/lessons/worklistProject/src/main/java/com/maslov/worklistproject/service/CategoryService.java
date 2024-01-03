package com.maslov.worklistproject.service;

import com.maslov.worklistproject.entity.Category;
import com.maslov.worklistproject.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository repo;

    public Category findById(long id) {
        return repo.findById(id).get();
    }

    public Category findByName(String name) {
        return repo.findCategoryByCatnameIs(name);
    }


    public Category[] findByInProcessNums(String[] fromTo) {
        return repo.findCategoryByInProcessGreaterThanEqualAndInProcessLessThanEqual(Integer.parseInt(fromTo[0]), Integer.parseInt(fromTo[1]));
    }

    public Category[] findByFinishedCount(int count) {
        return repo.findCategoryByFinishedIs(count);
    }

    public Category add(Category category) {
        return repo.save(category); // метод save обновляет объект, либо добавляет его, если такого объекта не было
    }

    public Category update(Category category) {
        return repo.save(category); // метод save ОБНОВЛЯЕТ объект category, так как id уже заполнено
    }

    public void deleteById(long id) {
        repo.deleteById(id);
    }

    public List<Category> findCategoryByIdIs(Long id) {
        return repo.findByIdIs(id);
    }

    public List<Category> getByIdFromMinToMax(Long[] minmax) {
        return repo.findCategoryByIdGreaterThanEqualAndIdLessThanEqual(minmax[0], minmax[1]);
    }
}

