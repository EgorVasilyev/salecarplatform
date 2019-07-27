package ru.job4j.persistent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;

public class DbProvider {

    private static final DbProvider INSTANCE = new DbProvider();
    private final SessionFactory sessionFactory;

    private DbProvider() {
        this.sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static DbProvider getInstance() {
        return INSTANCE;
    }

    public <T> T fn(final Function<Session, T> command) {
        Session session = this.sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            T t = command.apply(session);
            transaction.commit();
            return t;
        } catch (Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void cn(final Consumer<Session> command) {
        final Session session = this.sessionFactory.openSession();
        final Transaction transaction = session.beginTransaction();
        try {
            command.accept(session);
            transaction.commit();
        } catch (final Exception e) {
            transaction.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void close() {
        this.sessionFactory.close();
    }
}