import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import swt6.dao.ServiceFactory;
import swt6.domain.Address;
import swt6.domain.Article;
import swt6.domain.Bid;
import swt6.domain.Customer;
import swt6.util.JPAUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class BidServiceTests {
    @AfterEach
    void afterEach(){
        JPAUtil.resetDb();
    }

    @Test
    void insert(){
        var b = generateTestData();
        ServiceFactory.getBidService().insert(b);

        var c2 = ServiceFactory.getBidService().findById(b.getId());
        assertEquals(b.getId(), c2.getId());
    }

    @Test
    void update(){
        var a = generateTestData();
        ServiceFactory.getBidService().insert(a);

        a.setAmount(1000.0);
        var c2 = ServiceFactory.getBidService().findById(a.getId());
        assertEquals(1000.0, c2.getAmount());
    }

    @Test
    void remove(){
        var a = generateTestData();
        ServiceFactory.getBidService().insert(a);

        ServiceFactory.getBidService().remove(a);
        var result = ServiceFactory.getBidService().findById(a.getId());
        assertNull(result);
    }

    @Test
    void getAll(){
        var a = generateTestData();
        ServiceFactory.getBidService().insert(a);

        var result = ServiceFactory.getBidService().getAll();

        assertEquals(1, result.size());
    }

    @Test
    void getHighestBidsForArticle(){
        var a = generateTestData();
        ServiceFactory.getBidService().insert(a);

        var result = ServiceFactory.getBidService().getHighestBidsForArticle(ServiceFactory.getArticleService().getByNameOrDescription("test").get(0));
        assertEquals(1, result.size());
    }

    private Bid generateTestData(){
        Customer c1 = new Customer("max", "mustermann", "test@gmail.com", new Address("4040", "Linz", "iwo"), new Address("3313", "Wallsee", "am kaff 5"));
        ServiceFactory.getCustomerService().insert(c1);

        Article article = new Article("test", "desc", 100.0, LocalDateTime.now(), LocalDateTime.now(), c1);
        ServiceFactory.getArticleService().insert(article);
        return new Bid(100.0, c1, article, LocalDateTime.now());
    }
}
