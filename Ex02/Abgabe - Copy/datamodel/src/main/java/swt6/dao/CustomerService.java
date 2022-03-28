package swt6.dao;

import swt6.domain.Customer;

public interface CustomerService extends BaseService<Customer> {
    Customer getByEmail(String email);
}
