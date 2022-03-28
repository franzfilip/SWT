import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import swt6.dao.ServiceFactory;
import swt6.domain.Address;
import swt6.domain.Article;
import swt6.domain.Customer;
import swt6.util.JPAUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ArticleServiceTests {

    @AfterEach
    void afterEach(){
        JPAUtil.resetDb();
    }

    @Test
    void insert(){
        var a = generateTestData();
        ServiceFactory.getArticleService().insert(a);

        var c2 = ServiceFactory.getArticleService().findById(a.getId());
        assertEquals(a.getId(), c2.getId());
    }

    @Test
    void update(){
        var a = generateTestData();
        ServiceFactory.getArticleService().insert(a);

        a.setFinalPrice(1000.0);
        var c2 = ServiceFactory.getArticleService().findById(a.getId());
        assertEquals(1000.0, c2.getFinalPrice());
    }

    @Test
    void remove(){
        var a = generateTestData();
        ServiceFactory.getArticleService().insert(a);

        ServiceFactory.getArticleService().remove(a);
        var result = ServiceFactory.getArticleService().findById(a.getId());
        assertNull(result);
    }

    @Test
    void getAll(){
        var a = generateTestData();
        ServiceFactory.getArticleService().insert(a);

        var result = ServiceFactory.getArticleService().getAll();

        assertEquals(1, result.size());
    }

    private Article generateTestData(){
        Customer c1 = new Customer("max", "mustermann", "test@gmail.com", new Address("4040", "Linz", "iwo"), new Address("3313", "Wallsee", "am kaff 5"));
        ServiceFactory.getCustomerService().insert(c1);
        Customer c2 = new Customer("max", "mustermäx", "test@gmail.com", new Address("4040", "Linz", "iwo"), new Address("3313", "Wallsee", "am kaff 5"));
        ServiceFactory.getCustomerService().insert(c2);

        return new Article("test", "desc", 100.0, LocalDateTime.now(), LocalDateTime.now(), c1);
    }

}
