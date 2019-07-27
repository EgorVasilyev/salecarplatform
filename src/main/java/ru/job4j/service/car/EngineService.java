package ru.job4j.service.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.car.EngineDaoImpl;
import ru.job4j.entity.car.Engine;

import java.util.List;

public class EngineService {
    private static final Logger LOG = LogManager.getLogger(EngineDaoImpl.class.getName());
    private static final EngineDaoImpl ENGINE_DAO = EngineDaoImpl.getInstance();
    private static final EngineService INSTANCE = new EngineService();

    public EngineService() {
    }

    public static EngineService getInstance() {
        return INSTANCE;
    }
    public int save(Engine engine) {
        int id = 0;
        if (engine != null) {
            id = ENGINE_DAO.save(engine);
        }
        return id;
    }
    public void update(Engine engine) {
        if (engine != null) {
            ENGINE_DAO.update(engine);
        }
    }
    public void delete(Engine engine) {
        if (engine != null) {
            ENGINE_DAO.delete(engine);
        }
    }
    public List<Engine> getEngines() {
        return ENGINE_DAO.getEntities();
    }
    public boolean contains(String name) {
        return this.getEngines().stream().anyMatch(engineHas -> engineHas.getName().toLowerCase().equals(name.toLowerCase()));
    }
    public Engine getByName(String name) {
        return this.getEngines()
                .stream()
                .filter(engine -> engine.getName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
