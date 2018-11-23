package ru.erik182.repositories;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    Optional<T> findOne(Long id);
    void save(T model);
    void delete(Long id);
    void update(Long id, T model);

    List<T> findAll();
}