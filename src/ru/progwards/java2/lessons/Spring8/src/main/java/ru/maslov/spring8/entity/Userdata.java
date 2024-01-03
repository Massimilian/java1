package ru.maslov.spring8.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Userdata {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "create_task")
    private Boolean createTask;
    @Basic
    @Column(name = "read_task")
    private Boolean readTask;
    @Basic
    @Column(name = "update_task")
    private Boolean updateTask;
    @Basic
    @Column(name = "delete_task")
    private Boolean deleteTask;
}
