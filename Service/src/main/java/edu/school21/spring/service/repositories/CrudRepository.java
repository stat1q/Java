package edu.school21.spring.service.repositories;

import java.util.List;

public interface CrudRepository<T> {
    T findById(Long Id);

    List<T> findAll();

    void save(T entity, String password);

    void update(T entity);

    void delete(Long id);
}
