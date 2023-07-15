import dao.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;


@Log4j2
public class Main {
    public static void main(String[] args) {
        log.fatal("Hello world from Hibernate!");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Actioner actioner = new Actioner();
        boolean toCont = true;
        while(toCont) {
            toCont = actioner.mainAction();
            actioner.reNew();
        }
        session.close();
        HibernateUtil.shutdown();
    }
}
