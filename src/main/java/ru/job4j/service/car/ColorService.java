package ru.job4j.service.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.car.ColorDaoImpl;
import ru.job4j.entity.car.Color;

import java.util.List;

public class ColorService {
    private static final Logger LOG = LogManager.getLogger(ColorService.class.getName());
    private static final ColorDaoImpl COLOR_DAO = ColorDaoImpl.getInstance();
    private static final ColorService INSTANCE = new ColorService();

    public ColorService() {
    }

    public static ColorService getInstance() {
        return INSTANCE;
    }
    public int save(Color color) {
        int id = 0;
        if (color != null) {
            id = COLOR_DAO.save(color);
        }
        return id;
    }
    public void update(Color color) {
        if (color != null) {
            COLOR_DAO.update(color);
        }
    }
    public void delete(Color color) {
        if (color != null) {
            COLOR_DAO.delete(color);
        }
    }
    public List<Color> getColors() {
        return COLOR_DAO.getEntities();
    }
    public boolean contains(String name) {
        return this.getColors().stream().anyMatch(colorHas -> colorHas.getName().toLowerCase().equals(name.toLowerCase()));
    }
    public Color getByName(String name) {
        return this.getColors()
                .stream()
                .filter(color -> color.getName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
