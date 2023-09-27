package com.example.parentandchild.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;


@Entity
@Table(name = "child", schema = "public", catalog = "ParentsAndChilds")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Child {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "status_id", insertable = false, updatable = false)
    private Integer status_id;


    /**
     * todo
     * Не понятен механизм присоединения @ManyToMany через стороннюю таблицу.
     * Дано – таблицы Parent и Child, в которых идёт соединение по таблице relation при помощи Parent.id и Child.id.
     * В Parent мы создаём Set из экземпляров класса Child.
     * Но вывод элемента parents при запросе из Postman не происходит.
     * Почему происходит такое?
     */

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonBackReference
    @ManyToMany // связь таблиц осуществляется методом "много ко многим"
            (mappedBy = "childs", // классы данного типа "складываются" в переменную 'childs' соответствующего класса ('Parent')
                    fetch = FetchType.EAGER) // загрузка данных производится на этапе компиляции
    private Set<Parent> parents; // создаём сет присоединённых объектов


    /**
     * todo
     * При попытке добавления нового Child возникают проблемы (команда /add).
     * Если в @JsonProperty значение параметра access указано как JsonProperty.Access.WRITE_ONLY, то выбрасывается исключение,
     * говорящее о невозможности распарсить JSON (org.springframework.http.converter.HttpMessageNotReadableException: JSON parse error).
     * Если мы меняем значение на READ_ONLY - то значения status_id и status
     * добавляются как null, при том, что в самом запросе мы явно указываем status_id.
     * Каким образом можно сделать, чтобы добавлялся status_id в соответствии с запросом, а в Status присваивалось значение,
     * соответствующее status_id?
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToOne // связь таблиц осуществляется методом "много к одному"
    @JoinColumn // прописываем, по каким колонкам связываемся
            (name = "status_id", // какая колонка...
                    referencedColumnName = "id") // ... с какой связывается
    private Status status; // привязанный объект
}
