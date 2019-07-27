package ru.job4j.dao.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.EntityDao;
import ru.job4j.entity.car.Color;
import ru.job4j.persistent.DbProvider;

import java.util.List;

public class ColorDaoImpl implements EntityDao<Color> {
    private static final Logger LOG = LogManager.getLogger(ColorDaoImpl.class.getName());
    private static final ColorDaoImpl COLOR_DAO = new ColorDaoImpl();
    private static final DbProvider DB_PROVIDER = DbProvider.getInstance();

    public static ColorDaoImpl getInstance() {
        return COLOR_DAO;
    }

    private ColorDaoImpl() {

    }

    @Override
    public List<Color> getEntities() {
        return (List<Color>) DB_PROVIDER.fn(
                session -> session.createQuery("from Color order by id").list()
        );
    }

    @Override
    public int save(Color color) {
        return (int) DB_PROVIDER.fn(
                session -> {
                    LOG.info("save - " + color);
                    return session.save(color);
                }
        );
    }

    @Override
    public void update(Color color) {
        DB_PROVIDER.cn(
                session -> {
                    session.update(color);
                    LOG.info("update - " + color);
                }
        );
    }

    @Override
    public void delete(Color color) {
        DB_PROVIDER.cn(
                session -> {
                    session.delete(color);
                    LOG.info("delete - " + color);
                }
        );
    }
}
