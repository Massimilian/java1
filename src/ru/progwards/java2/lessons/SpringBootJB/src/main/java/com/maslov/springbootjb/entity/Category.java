package com.maslov.springbootjb.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Set;

@Entity
@Table(name = "category", schema = "public", catalog = "worklist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Category {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "catname")
    private String catname;
    @Basic
    @Column(name = "in_process")
    private Integer inProcess;
    @Basic
    @Column(name = "finished")
    private Integer finished;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany // связь таблиц осуществляется методом "один ко многим"
            (mappedBy = "category", // название поля, по которому осуществляется связь, в соответствующем классе ("Tasks")
                    fetch = FetchType.LAZY) // тип загрузки (ленивый)
    private Set<Tasks> tasks; // создаём сет присоединённых объектов

}
