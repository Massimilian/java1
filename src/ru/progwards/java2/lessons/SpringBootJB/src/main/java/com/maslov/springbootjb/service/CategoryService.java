package com.maslov.springbootjb.service;

import com.maslov.springbootjb.entity.Category;
import com.maslov.springbootjb.repo.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // аннотация для Spring контейнера для того, чтобы он понимал, какую роль играет данный класс
@Transactional // все методы данного класса должны выполниться без ошибок. При возникновении ошибки транзакция откатится назад.
public class CategoryService {
    // в данном случае отработает встроенный механизм Depedency Injection, при помощи которого этот интерфейс и будет реализован в виде конкретного класса реализации
    // ВНИМАНИЕ! Аннотация @Autowired больше не нужна - Spring и так всё поймёт
    private final CategoryRepository repository;

    // так как у нас класс помечен аннотацией @Service, Spring попытается вставить в конструкторе сделанный "на лету" класс-реализацию на основе интерфейса из репозитория; причём все методы данной реализации уже будут автоматически заполнены.
    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public Category findById(Integer id) {
        return repository.findById(id).get(); // возвращает не сам объект, а его Optional (т.е. некую обёртку над объектом), и далее сам объект вынимается при помощи метода get
    }

    public Category findByName(String name) {
        return repository.findCategoryByCatnameIgnoreCase(name);
    }

    public Category add(Category category) {
        return repository.save(category); // метод save обновляет объект, либо добавляет его, если такого объекта не было
    }

    public Category update(Category category) {
        return repository.save(category); // метод save ОБНОВЛЯЕТ объект category, так как id уже заполнено
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

    public List<Category> findCategoryByInProcessIs(int inProcess) {
        return repository.findCategoryByInProcessIs(inProcess);
    }
}
