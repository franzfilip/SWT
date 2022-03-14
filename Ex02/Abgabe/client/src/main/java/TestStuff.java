import swt6.dao.ServiceFactory;
import swt6.domain.Address;
import swt6.domain.Customer;
import swt6.util.JPAUtil;

public class TestStuff {
    public static void main(String[] args) {
        var service = ServiceFactory.getCustomerService();
        service.insert(new Customer("ff2", "schörg", "ff.schörg@gmail.com", new Address("3313","wallsee", "test"), new Address("3312", "x", "test2")));
        JPAUtil.commit();
    }
}
