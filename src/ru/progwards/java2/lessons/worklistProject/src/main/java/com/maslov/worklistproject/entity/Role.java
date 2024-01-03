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
@Table(name = "role", schema = "public", catalog = "worklist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;

    @ManyToMany // связь таблиц осуществляется методом "много ко многим"
    @JoinTable(name="users_roles", // таблица для связывания - 'users_roles'
            joinColumns = @JoinColumn(name = "role_id"), // данный класс присоединяется при помощи колонки 'role_id'
            inverseJoinColumns = @JoinColumn(name = "user_data_id")) // второй объект присоединяется при помощи колонки 'user_data_id'
    private Set<UserData> users; // создаём сет присоединённых объектов

}
