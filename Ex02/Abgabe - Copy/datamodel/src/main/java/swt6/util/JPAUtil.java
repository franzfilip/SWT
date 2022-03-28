package swt6.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory emFactory;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

    public static synchronized EntityManagerFactory getEMFactory(){
        if(emFactory == null){
            emFactory = Persistence.createEntityManagerFactory("FHBay");
        }

        return emFactory;
    }

    public static synchronized EntityManager getEntityManager() {
        if(emThread.get() == null){
            emThread.set(getEMFactory().createEntityManager());
        }

        return emThread.get();
    }

    public static synchronized void closeEntityManager() {
        if(emThread.get() != null){
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if(!tx.isActive()){
            tx.begin();
        }

        return em;
    }

    public static synchronized void commit() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        if(tx.isActive()){
            tx.commit();
        }
        closeEntityManager();
    }

    public static synchronized void rollback() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if(tx.isActive()){
            tx.rollback();
        }
        closeEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        if(emFactory != null){
            emFactory.close();
            emFactory = null;
        }
    }

    private static synchronized  void resetTable(String table){
        EntityManager em = getTransactedEntityManager();
        var query = em.createQuery("delete from "+ table);
        query.executeUpdate();
        JPAUtil.commit();
    }

    public static synchronized void resetDb(){
        resetTable("Bid");
        resetTable("Article");
        resetTable("Customer");
    }
}
