package ru.job4j.service.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.car.BodyDaoImpl;
import ru.job4j.entity.car.Body;

import java.util.List;

public class BodyService {
    private static final Logger LOG = LogManager.getLogger(BodyService.class.getName());
    private static final BodyDaoImpl BODY_DAO = BodyDaoImpl.getInstance();
    private static final BodyService INSTANCE = new BodyService();

    public BodyService() {
    }

    public static BodyService getInstance() {
        return INSTANCE;
    }
    public int save(Body body) {
        int id = 0;
        if (body != null) {
            id = BODY_DAO.save(body);
        }
        return id;
    }
    public void update(Body body) {
        if (body != null) {
            BODY_DAO.update(body);
        }
    }
    public void delete(Body body) {
        if (body != null) {
            BODY_DAO.delete(body);
        }
    }
    public List<Body> getBodies() {
        return BODY_DAO.getEntities();
    }
    public boolean contains(String name) {
        return this.getBodies().stream().anyMatch(bodyHas -> bodyHas.getName().toLowerCase().equals(name.toLowerCase()));
    }
    public Body getByName(String name) {
        return this.getBodies()
                .stream()
                .filter(body -> body.getName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
