package com.maslov.worklistproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "user_data", schema = "public", catalog = "worklist")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserData {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "pass")
    private String pass;
    @Basic
    @Column(name = "email")
    private String email;

    @ManyToMany // связь таблиц осуществляется методом "много ко многим"
            (mappedBy = "users", // классы данного типа "складываются" в переменную 'users' соответствующего класса ('Role')
                    fetch = FetchType.EAGER) // загрузка данных производится на этапе компиляции
    private Set<Role> roles; // создаём сет присоединённых объектов
}
