package ru.job4j.dao;

import java.util.List;

public interface EntityDao<T> {
    List<T> getEntities();
    int save(T t);
    void update(T t);
    void delete(T t);
}
