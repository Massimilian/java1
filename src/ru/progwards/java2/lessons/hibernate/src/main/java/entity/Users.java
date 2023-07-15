package entity;

import dao.DAO;
import dao.HibernateUtil;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "users", schema = "public", catalog = "mail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Users implements DAO<Users> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "login")
    private String login;
    @Basic
    @Column(name = "pass")
    private String pass;
    @Basic
    @Column(name = "user_info_id", insertable = false, updatable = false)
    private Integer userInfoId;

    @OneToMany(mappedBy = "u", fetch = FetchType.EAGER)
    List<Mailbox> mailboxes;

    @ManyToOne
    @JoinColumn (name="user_info_id", referencedColumnName = "id")
    UserInfo ui;

    public Users(String login, String pass) {
        this.login = login;
        this.pass = pass;
    }

    @Override
    public Users get(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.get(Users.class, (int)id);
    }

    @Override
    public Users getByTwoParameters(String first, String second) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM users WHERE login='%s' AND pass='%s';", first, second));
        nq.addEntity(Users.class);
        List<Users> temp = nq.getResultList();
        session.close();
        if (!temp.isEmpty()) {
            return temp.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void add() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        NativeQuery nq = session.createNativeQuery(String.format("INSERT INTO users (login, pass, user_info_id) VALUES ('%s', '%s', %d);", this.login, this.pass, this.userInfoId));
        nq.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete() {
        for (int i = 0; i < mailboxes.size(); i++) {
            mailboxes.get(i).delete();
        }
        mailboxes = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(this);
        session.getTransaction().commit();
        session.close();
    }

    public void updateUI(int uiId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        NativeQuery nq = session.createNativeQuery(String.format("UPDATE users SET user_info_id=%d WHERE login='%s' AND pass='%s';", uiId, this.login, this.pass));
        nq.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public boolean checkIfIs(String login) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery<BigInteger> query = session.createNativeQuery(String.format("SELECT COUNT (*) FROM users u WHERE login='%s';", login));
        int res = query.uniqueResult().intValue();
        return res != 0;
    }

    public Users getByLoginAndUII(String login, int UII) {
        Users result = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM users WHERE login='%s' AND user_info_id=%d;", login, UII));
        nq.addEntity(Users.class);
        List<Users> list = nq.getResultList();
        if (!list.isEmpty()) {
            result = list.get(0);
        }
        return result;

    }
}
