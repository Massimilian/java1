package entity;

import dao.DAO;
import dao.HibernateUtil;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "mailbox", schema = "public", catalog = "mail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Mailbox implements DAO<Mailbox> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "num_of_news")
    private Integer numOfNews;
    @Basic
    @Column(name = "num_of_has_read")
    private Integer numOfHasRead;
    @Basic
    @Column(name = "num_of_has_sent")
    private Integer numOfHasSent;
    @Basic
    @Column(name = "users_id", insertable = false, updatable = false)
    private Integer usersId;

    @ManyToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private Users u;

    public List<Letter> getNews() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM letter WHERE to_mailbox_id=%d AND is_read=false;", this.id));
        nq.addEntity(Letter.class);
        List<Letter> temp = nq.getResultList();
        session.close();
        return temp;
    }

    public List<Letter> getOlds() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM letter WHERE to_mailbox_id=%d AND is_read=true AND is_deleted_from_receiver=false;", this.id));
        nq.addEntity(Letter.class);
        List<Letter> temp = nq.getResultList();
        session.close();
        return temp;
    }

    public List<Letter> getSents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM letter WHERE from_mailbox_id=%d AND is_deleted_from_sender=false;", this.id));
        nq.addEntity(Letter.class);
        List<Letter> temp = nq.getResultList();
        session.close();
        return temp;
    }

    @Override
    public Mailbox get(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        return session.get(Mailbox.class, (int) id);
    }

    @Override
    public Mailbox getByTwoParameters(String first, String second) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM mailbox WHERE name='%s' AND users_id=%s", first, second));
        nq.addEntity(Mailbox.class);
        List<Mailbox> list = nq.getResultList();
        Mailbox result = null;
        if (list.size() > 0) {
            result = list.get(0);
        }
        return result;
    }

    @Override
    public void add() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("INSERT INTO mailbox (name, num_of_news, num_of_has_read, num_of_has_sent, users_id) VALUES ('%s', 0, 0, 0, %d);", this.getName(), this.getUsersId()));
        session.beginTransaction();
        nq.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.delete(this);
        session.getTransaction().commit();
        session.close();
    }

    public Integer findIdByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("SELECT * FROM mailbox WHERE name='%s';", name));
        nq.addEntity(Mailbox.class);
        List<Mailbox> list = nq.getResultList();
        Integer result = null;
        if (list.size() > 0) {
            result = list.get(0).getId();
        }
        return result;
    }
}
