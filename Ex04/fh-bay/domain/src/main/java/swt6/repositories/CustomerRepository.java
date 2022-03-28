package swt6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.datamodel.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
