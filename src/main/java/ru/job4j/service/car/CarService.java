package ru.job4j.service.car;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.car.CarDaoImpl;
import ru.job4j.entity.car.Car;

import java.util.List;

public class CarService {
    private static final Logger LOG = LogManager.getLogger(CarService.class.getName());
    private static final CarDaoImpl CAR_DAO = CarDaoImpl.getInstance();
    private static final CarService INSTANCE = new CarService();

    public CarService() {
    }

    public static CarService getInstance() {
        return INSTANCE;
    }
    public int save(Car car) {
        int id = 0;
        if (car != null) {
            id = CAR_DAO.save(car);
        }
        return id;
    }
    public void update(Car car) {
        if (car != null) {
            CAR_DAO.update(car);
        }
    }
    public void delete(Car car) {
        if (car != null) {
            CAR_DAO.delete(car);
        }
    }
    public List<Car> getCars() {
        return CAR_DAO.getEntities();
    }
    public List<Car> getCarsByUserId(int userId) {
        return CAR_DAO.getCarsByUserId(userId);
    }
}
