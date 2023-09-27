package com.maslov.springbootjb.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.sql.Timestamp;

@Entity
@Table(name = "tasks", schema = "public", catalog = "worklist")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tasks {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "done")
    private Boolean done;
    @Basic
    @Column(name = "dateandtime")
    private Timestamp dateandtime;
    @Basic
    @Column(name = "category_id", insertable = false, updatable = false)
    private Integer categoryId;
    @Basic
    @Column(name = "priority_id", insertable = false, updatable = false)
    private Integer priorityId;
    @Basic
    @Column(name = "user_data_id", insertable = false, updatable = false)
    private Integer userDataId;


    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne // связь таблиц осуществляется методом "много к одному"
    @JoinColumn // прописываем, по каким колонкам связываемся
            (name = "category_id", // какая колонка...
                    referencedColumnName = "id") // ... с какой связывается
    private Category category; // привязанный объект

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "priority_id", referencedColumnName = "id")
    private Priority priority;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "user_data_id", referencedColumnName = "id")
    private UserData user;

}
