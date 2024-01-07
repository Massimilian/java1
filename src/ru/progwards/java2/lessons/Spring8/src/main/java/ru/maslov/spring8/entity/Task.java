package ru.maslov.spring8.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "prefix")
    private String prefix;
    @Basic
    @Column(name = "isdone")
    private Boolean isdone;
    @Basic
    @Column(name = "priority")
    private String priority;
    @Basic
    @Column(name = "userdataid", insertable = false, updatable = false)
    private Integer userdataid;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "userdataid", referencedColumnName = "id")
    Userdata userdata;
}
