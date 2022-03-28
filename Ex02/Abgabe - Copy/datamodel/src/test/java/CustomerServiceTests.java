import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import swt6.dao.ServiceFactory;
import swt6.domain.Address;
import swt6.domain.Customer;
import swt6.util.JPAUtil;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTests {
    @AfterEach
    void afterEach(){
        JPAUtil.resetDb();
    }

    @Test
    void insert(){
        var c = generateTestData();
        ServiceFactory.getCustomerService().insert(c);

        var c2 = ServiceFactory.getCustomerService().findById(c.getId());
        assertEquals(c.getId(), c2.getId());
    }

    @Test
    void update(){
        var c = generateTestData();
        ServiceFactory.getCustomerService().insert(c);

        c.setEmail("test");
        var c2 = ServiceFactory.getCustomerService().findById(c.getId());
        assertEquals("test", c2.getEmail());
    }

    @Test
    void remove(){
        var c = generateTestData();
        ServiceFactory.getCustomerService().insert(c);

        ServiceFactory.getCustomerService().remove(c);
        var result = ServiceFactory.getCustomerService().findById(c.getId());
        assertNull(result);
    }

    @Test
    void getAll(){
        var c = generateTestData();
        ServiceFactory.getCustomerService().insert(c);

        var result = ServiceFactory.getCustomerService().getAll();

        assertEquals(1, result.size());
    }

    private Customer generateTestData(){
        return new Customer("max", "mustermann", "test@gmail.com", new Address("4040", "Linz", "iwo"), new Address("3313", "Wallsee", "am kaff 5"));
    }
}
