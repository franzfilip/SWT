package swt6.dao;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T> {
    T findById(long id);
    List<T> getAll();
    void insert(T entity);
    T update(Serializable id, T entity, Class<T> entityType);
    boolean remove(T entity);
}
