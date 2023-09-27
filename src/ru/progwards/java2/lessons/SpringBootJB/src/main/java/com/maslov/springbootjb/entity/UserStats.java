package com.maslov.springbootjb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.Objects;

@Entity
@Table(name = "user_stats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserStats {
    @Id
    @Basic
    @Column(name = "users_id", nullable = false)
    private Long usersid;
    @Basic
    @Column(name = "in_process")
    private Integer inProcess;
    @Basic
    @Column(name = "finished")
    private Integer finished;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(fetch = FetchType.LAZY) // тип связи "один к одному", тип загрузки - "ленивый"
    @MapsId // помогает связать две таблицы одним ключом
    @JoinColumn(name="users_id", referencedColumnName = "id") // показывает, через какой ключ в данной таблице ('users_id') и к какому ключу в привязанной таблице ('id') идёт связь.
    private UserData user; // создаём сет присоединённых объектов

}
