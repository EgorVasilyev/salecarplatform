package ru.job4j.service.ad;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.dao.ad.AdDaoImpl;
import ru.job4j.dao.car.EngineDaoImpl;
import ru.job4j.entity.ad.Ad;

import java.util.List;
import java.util.Map;

public class AdService {
    private static final Logger LOG = LogManager.getLogger(EngineDaoImpl.class.getName());
    private static final AdDaoImpl AD_DAO = AdDaoImpl.getInstance();
    private static final AdService INSTANCE = new AdService();

    public AdService() {
    }

    public static AdService getInstance() {
        return INSTANCE;
    }
    public int save(Ad ad) {
        int id = 0;
        if (ad != null) {
            id = AD_DAO.save(ad);
        }
        return id;
    }
    public void update(Ad ad) {
        if (ad != null) {
            AD_DAO.update(ad);
        }
    }
    public void delete(Ad ad) {
        if (ad != null) {
            AD_DAO.delete(ad);
        }
    }
    public List<Ad> getAds() {
        return AD_DAO.getEntities();
    }

    public List<Ad> getAdsByUserId(int userId) {
        return AD_DAO.getAdsByUserId(userId);
    }

    public Ad getAdById(int id) {
        return AD_DAO.getAdById(id);
    }

    public List<Ad> getAdsByFilter(Map<String, String> filter) {
        return AD_DAO.getAdsByFilter(filter);
    }
}
