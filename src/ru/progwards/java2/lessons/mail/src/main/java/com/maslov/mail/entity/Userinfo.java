package com.maslov.mail.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;
import java.util.Set;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Getter
@Setter
public class Userinfo {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "address")
    private String address;

    @OneToMany // связь таблиц осуществляется методом "один ко многим"
            (mappedBy = "fromuserid", // название поля, по которому осуществляется связь, в соответствующем классе ("Userinfo")
                    fetch = FetchType.LAZY) // тип загрузки (ленивый)
    private Set<Letter> from; // создаём сет присоединённых объектов

    @OneToMany // связь таблиц осуществляется методом "один ко многим"
            (mappedBy = "touserid", // название поля, по которому осуществляется связь, в соответствующем классе ("Userinfo")
                    fetch = FetchType.LAZY) // тип загрузки (ленивый)
    private Set<Letter> to; // создаём сет присоединённых объектов

}
