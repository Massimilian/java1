package com.example.parentandchild.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Entity
@Table(name = "status", schema = "public", catalog = "ParentsAndChilds")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "type")
    private String type;

    @OneToMany // связь таблиц осуществляется методом "один ко многим"
            (mappedBy = "status", // название поля, по которому осуществляется связь, в соответствующем классе ("Tasks")
                    fetch = FetchType.EAGER) // тип загрузки
    private Set<Parent> tasks; // создаём сет присоединённых объектов




}
