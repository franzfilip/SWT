package swt6.dao;

import java.util.List;

public interface BaseService<T> {
    T findById(long id);
    List<T> getAll();
    void insert(T entity);
    T update(T entity);
    boolean remove(T entity);
}
