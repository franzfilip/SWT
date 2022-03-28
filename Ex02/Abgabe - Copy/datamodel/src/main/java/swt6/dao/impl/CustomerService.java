package swt6.dao.impl;

import swt6.domain.Customer;
import swt6.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class CustomerService extends BaseService<Customer> implements swt6.dao.CustomerService {

    public CustomerService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    Class<Customer> getType() {
        return Customer.class;
    }

    @Override
    public List<Customer> getAll() {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        return this.entityManager.createQuery("select c from Customer c", Customer.class).getResultList();
    }

    @Override
    public Customer getByEmail(String email) {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        TypedQuery<Customer> query = this.entityManager.createQuery("select c from Customer c where c.email = :email", Customer.class);
        query.setParameter("email", email);
        var result = query.getResultList();
        if(result.size() > 1){
            throw new RuntimeException("Multiple Customers with same ID found.");
        }

        return result.get(0);
    }
}
