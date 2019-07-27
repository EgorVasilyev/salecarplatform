package ru.job4j.persistent;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.Proxy;

import static org.mockito.Mockito.mock;

/**
 * Connection, which rollback all commits.
 * It is used for integration test.
 */
public class SessionFactoryRollback {
    public static SessionFactory createSessionFactory(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        return (SessionFactory) Proxy.newProxyInstance(
                SessionFactoryRollback.class.getClassLoader(),
                new Class[]{
                        SessionFactory.class
                },
                (proxy, method, args) -> {
                    Object result = null;
                    switch (method.getName()) {
                        case "openSession":
                            result = SessionFactoryRollback.createSession(session);
                            break;
                        case "close":
                            session.getTransaction().rollback();
                            session.close();
                            break;
                        default:
                            result = method.invoke(factory, args);
                    }
                    return result;
                }
        );
    }

    private static Session createSession(Session session) {
        return (Session) Proxy.newProxyInstance(
                SessionFactoryRollback.class.getClassLoader(),
                new Class[]{
                        Session.class
                },
                (proxy, method, args) -> {
                    Object result = null;
                    switch (method.getName()) {
                        case "commit":
                        case "close":
                            break;
                        case "beginTransaction":
                            result = mock(Transaction.class);
                            break;
                        default:
                            result = method.invoke(session, args);
                    }
                    return result;
                }
        );
    }
}