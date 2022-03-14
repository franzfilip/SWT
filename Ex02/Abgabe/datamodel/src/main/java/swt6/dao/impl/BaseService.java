package swt6.dao.impl;

import swt6.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class BaseService<T> implements swt6.dao.BaseService<T> {
    protected EntityManager entityManager;

    public BaseService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract Class<T> getType();

    @Override
    public T findById(long id) {
        var em = JPAUtil.getTransactedEntityManager();
        return em.find(getType(), id);
    }

    @Override
    public abstract List<T> getAll();

    @Override
    public void insert(T entity) {
        var em = JPAUtil.getTransactedEntityManager();
        em.persist(entity);
    }

    @Override
    public T update(T entity) {
        var em = JPAUtil.getTransactedEntityManager();
        return em.merge(entity);
    }

    @Override
    public boolean remove(T entity) {
        var em = JPAUtil.getTransactedEntityManager();
        entity = em.merge(entity);
        try{
            em.remove(entity);
        }
        catch (Exception ex){
            return false;
        }
        return true;
    }
}
