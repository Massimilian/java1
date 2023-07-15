package dao;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;

// класс для инициализации Hibernate
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * метод по построению SessionFactory
     * @return
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure(new File("src\\main\\resources\\hibernate.cfg.xml")).buildSessionFactory();
        } catch (Throwable throwable) {
            System.out.println("Initial Session Factory failed" + throwable);
            throw new ExceptionInInitializerError(throwable);
        }
    }

    /**
     * Метод по возврату SessionFactory
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Метод по закрытию SessionFactory
     */
    public static void shutdown() {
        getSessionFactory().close();
    }
}
