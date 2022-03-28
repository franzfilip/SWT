package swt6.dao.impl;

import swt6.util.JPAUtil;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public abstract class BaseService<T> implements swt6.dao.BaseService<T> {
    protected EntityManager entityManager;

    public BaseService(){ }

    public BaseService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    abstract Class<T> getType();

    @Override
    public T findById(long id) {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        return this.entityManager.find(getType(), id);
    }

    @Override
    public abstract List<T> getAll();

    @Override
    public void insert(T entity) {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        this.entityManager.persist(entity);
    }

    @Override
    public T update(Serializable id, T entity, Class<T> entityType) {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        var storedEntity = this.entityManager.find(entityType, id);

        if(storedEntity == null){
            throw new RuntimeException("Entity does not exist");
        }

        storedEntity = this.entityManager.merge(entity);

        return storedEntity;
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
