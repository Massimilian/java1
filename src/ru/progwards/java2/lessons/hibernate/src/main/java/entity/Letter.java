package entity;

import dao.DAO;
import dao.HibernateUtil;
import lombok.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "letter", schema = "public", catalog = "mail")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Letter implements DAO<Letter> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;
    @Basic
    @Column(name = "solddt")
    private Timestamp solddt;
    @Basic
    @Column(name = "readdt")
    private Timestamp readdt;
    @Basic
    @Column(name = "info")
    private String info;
    @Basic
    @Column(name = "from_mailbox_id")
    private Integer fromMailboxId;
    @Basic
    @Column(name = "to_mailbox_id")
    private Integer toMailboxId;
    @Basic
    @Column(name = "is_deleted_from_receiver")
    private Boolean isDeletedFromReceiver;
    @Basic
    @Column(name = "is_deleted_from_sender")
    private Boolean isDeletedFromSender;
    @Basic
    @Column(name = "is_read")
    private Boolean isRead;



    @Override
    public Letter get(long id) {
        return null;
    }

    @Override
    public Letter getByTwoParameters(String first, String second) {
        return null;
    }

    @Override
    public void add() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        session.save(this);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        NativeQuery nq = session.createNativeQuery(String.format("DELETE FROM letter WHERE id=%d;", this.id));
        nq.executeUpdate();
        session.close();
    }

    public void workWithNew() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN;").append(System.lineSeparator());
        sb.append(String.format("UPDATE mailbox SET num_of_news = (num_of_news - 1) WHERE id = (SELECT AVG(to_mailbox_id) FROM letter WHERE id = %d);", this.id)).append(System.lineSeparator());
        sb.append(String.format("UPDATE letter SET readdt = now() WHERE id = %d;", this.id)).append(System.lineSeparator());
        sb.append(String.format("UPDATE letter SET is_read = 'true' WHERE id = %d;", this.id)).append(System.lineSeparator());
        sb.append(String.format("UPDATE mailbox SET num_of_has_read = (num_of_has_read + 1) WHERE id = (SELECT AVG(to_mailbox_id) FROM letter WHERE id = %d);", this.id)).append(System.lineSeparator());
        sb.append("COMMIT;");
        NativeQuery nq = session.createNativeQuery(sb.toString());
        nq.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteOld() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN;").append(System.lineSeparator());
        sb.append(String.format("UPDATE mailbox SET num_of_has_read = (num_of_has_read - 1) WHERE id = (SELECT AVG(to_mailbox_id) FROM letter WHERE id = %d);", this.id)).append(System.lineSeparator());
        sb.append(String.format("UPDATE letter SET is_deleted_from_receiver = true WHERE id = %d;", this.id)).append(System.lineSeparator());
        sb.append("UPDATE mailbox SET num_of_has_read = (num_of_has_read + 1) WHERE id = 0;").append(System.lineSeparator());
        if ((int)this.getFromMailboxId() == (int)this.getToMailboxId()) {
            sb.append(String.format("UPDATE mailbox SET num_of_has_sent = (num_of_has_sent - 1) WHERE id = (SELECT AVG(from_mailbox_id) FROM letter WHERE id = %d);", this.id)).append(System.lineSeparator());
            sb.append(String.format("UPDATE letter SET is_deleted_from_sender = true WHERE id = %d;", this.id)).append(System.lineSeparator());
            sb.append(String.format("UPDATE mailbox SET num_of_has_sent = (num_of_has_sent + 1) WHERE id = 0;", this.getId())).append(System.lineSeparator());
        }
        sb.append("COMMIT;");
        NativeQuery nq = session.createNativeQuery(sb.toString());
        nq.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    public void deleteSent() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.getTransaction().begin();
        StringBuilder sb = new StringBuilder();
        sb.append("BEGIN;").append(System.lineSeparator());
        sb.append(String.format("UPDATE mailbox SET num_of_has_sent = (num_of_has_sent - 1) WHERE id = (SELECT AVG(from_mailbox_id) FROM letter WHERE id = %d);", this.id)).append(System.lineSeparator());
        sb.append(String.format("UPDATE letter SET is_deleted_from_sender = true WHERE id = %d;", this.id)).append(System.lineSeparator());
        sb.append(String.format("UPDATE mailbox SET num_of_has_sent = (num_of_has_sent + 1) WHERE id = 0;", this.getId())).append(System.lineSeparator());
        if ((int)this.getFromMailboxId() == (int)this.getToMailboxId()) {
            sb.append(String.format("UPDATE mailbox SET num_of_has_read = (num_of_has_read - 1) WHERE id = (SELECT AVG(to_mailbox_id) FROM letter WHERE id = %d);", this.id)).append(System.lineSeparator());
            sb.append(String.format("UPDATE letter SET is_deleted_from_receiver = true WHERE id = %d;", this.id)).append(System.lineSeparator());
            sb.append("UPDATE mailbox SET num_of_has_read = (num_of_has_read + 1) WHERE id = 0;").append(System.lineSeparator());
        }
        sb.append("COMMIT;");
        NativeQuery nq = session.createNativeQuery(sb.toString());
        nq.executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
