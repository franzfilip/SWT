package swt6.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.datamodel.Customer;
import swt6.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements swt6.services.CustomerService {
    private CustomerRepository customerRepo;

    public CustomerServiceImpl(CustomerRepository repo) {
        this.customerRepo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Long id) {
        return customerRepo.findById(id);
    }

    @Override
    public Customer save(Customer customer) {
        if(customer == null){
            throw new IllegalArgumentException("Customer must not be null");
        }

        return customerRepo.save(customer);
    }
}
