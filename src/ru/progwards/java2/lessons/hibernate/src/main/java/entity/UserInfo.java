package entity;

import dao.DAO;
import dao.HibernateUtil;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user_info", schema = "public", catalog = "mail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserInfo implements DAO<UserInfo> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "address")
    private String address;
    @Basic
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "ui", fetch = FetchType.EAGER)
    List<Users> user;

    public UserInfo(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }

    @Override
    public UserInfo get(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.get(UserInfo.class, (int) id);
    }

    @Override
    public UserInfo getByTwoParameters(String first, String second) {
        UserInfo result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM user_info WHERE address='%s' AND phone='%s';", first, second));
        nq.addEntity(UserInfo.class);
        List<UserInfo> list = nq.getResultList();
        if (!list.isEmpty()) {
            result = list.get(0);
        }
        return result;
    }

    @Override
    public void add() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        NativeQuery nq = session.createNativeQuery(String.format("INSERT INTO user_info (address, phone) SELECT * FROM (SELECT '%s' AS address, '%s' AS phone) AS tmp WHERE NOT EXISTS (SELECT (address, phone) FROM user_info WHERE address ='%s' AND phone = '%s' LIMIT 1);", this.address, this.phone, this.address, this.phone));
        nq.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.delete(this);
        session.close();
    }

    @Override
    public String toString() {
        return String.format("Доступная информация о пользователе: адрес - '%s', телефон - '%s'.", address, phone);
    }
}
