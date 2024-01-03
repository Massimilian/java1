package com.maslov.mail.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NoArgsConstructor
@Getter
@Setter

public class Letter {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "head")
    private String head;
    @Basic
    @Column(name = "body")
    private String body;
    @Basic
    @Column(name = "fromuserid")
    private Integer fromuserid;
    @Basic
    @Column(name = "touserid", insertable = false, updatable = false)
    private Integer touserid;
    @Basic
    @Column(name = "startdate", insertable = false, updatable = false)
    private Timestamp startdate;
    @Basic
    @Column(name = "isdeleted")
    private Boolean isdeleted;

    @ManyToOne // связь таблиц осуществляется методом "много к одному"
    @JoinColumn // прописываем, по каким колонкам связываемся
            (name = "fromuserid", // какая колонка...
                    referencedColumnName = "id") // ... с какой связывается
    private Userinfo from; // привязанный объект

    @ManyToOne // связь таблиц осуществляется методом "много к одному"
    @JoinColumn // прописываем, по каким колонкам связываемся
            (name = "touserid", // какая колонка...
                    referencedColumnName = "id") // ... с какой связывается
    private Userinfo to; // привязанный объект

}
