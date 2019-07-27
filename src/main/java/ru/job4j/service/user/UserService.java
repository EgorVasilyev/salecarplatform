package ru.job4j.service.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.user.UserDaoImpl;
import ru.job4j.entity.user.User;

import java.util.List;

public class UserService {
    private static final Logger LOG = LogManager.getLogger(UserService.class.getName());
    private static final UserDaoImpl USER_DAO = UserDaoImpl.getInstance();
    private static final UserService INSTANCE = new UserService();

    public UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
    public int save(User user) {
        int id = 0;
        if (user != null) {
             id = USER_DAO.save(user);
        }
        return id;
    }
    public void update(User user) {
        if (user != null) {
            USER_DAO.update(user);
        }
    }
    public void delete(User user) {
        if (user != null) {
            USER_DAO.delete(user);
        }
    }
    public User isCredential(String login, String password) {
        return this.getUsers().stream()
                .filter(user -> user.getLogin().equals(login) && user.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
    
    public boolean contains(String login) {
        return this.getUsers().stream().anyMatch(user -> user.getLogin().equals(login));
    }

    public List<User> getUsers() {
        return USER_DAO.getEntities();
    }

    public User getUserById(int id) {
        return USER_DAO.getUserById(id);
    }
}
