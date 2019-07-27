package ru.job4j.dao.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.Query;
import ru.job4j.dao.EntityDao;
import ru.job4j.entity.user.User;
import ru.job4j.persistent.DbProvider;

import java.util.List;

public class UserDaoImpl implements EntityDao<User> {
    private static final Logger LOG = LogManager.getLogger(UserDaoImpl.class.getName());
    private static final UserDaoImpl USER_DAO = new UserDaoImpl();
    private static final DbProvider DB_PROVIDER = DbProvider.getInstance();

    public static UserDaoImpl getInstance() {
        return USER_DAO;
    }

    private UserDaoImpl() {

    }

    @Override
    public List<User> getEntities() {
        return (List<User>) DB_PROVIDER.fn(
                session -> session.createQuery("from User  order by id").list()
        );
        /*where login != 'guest'*/
    }

    public User getUserById(int id) {
        return DB_PROVIDER.fn(
                session -> {
                    Query<User> query = session.createQuery("from User where id = :id", User.class);
                    query.setParameter("id", id);
                    return query.getSingleResult();
                }
        );
    }


    @Override
    public int save(User user) {
        return (int) DB_PROVIDER.fn(
                session -> {
                    LOG.info("save - " + user);
                    return session.save(user);
                }
        );
    }

    @Override
    public void update(User user) {
        DB_PROVIDER.cn(
                session -> {
                    session.update(user);
                    LOG.info("update - " + user);
                }
        );
    }

    @Override
    public void delete(User user) {
        DB_PROVIDER.cn(
                session -> {
                    session.delete(user);
                    LOG.info("delete - " + user);
                }
        );
    }
}
