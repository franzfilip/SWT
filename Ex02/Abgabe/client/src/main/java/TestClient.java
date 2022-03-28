import swt6.dao.ServiceFactory;
import swt6.domain.Address;
import swt6.domain.Article;
import swt6.domain.Bid;
import swt6.domain.Customer;
import swt6.util.JPAUtil;

import java.time.LocalDateTime;

public class TestClient {
    public static void main(String[] args) {
        JPAUtil.getEMFactory();
        try{
            simulateBidding();
        }
        catch (Exception ex){
            JPAUtil.rollback();
            System.out.println(ex.getMessage());
        }
        finally {
            JPAUtil.closeEntityManagerFactory();
        }
    }

    private static void simulateBidding(){
        var customerService = ServiceFactory.getCustomerService();
        var articleService = ServiceFactory.getArticleService();
        var bidService = ServiceFactory.getBidService();

        var addr1 = new Address("4040", "Linz", "Irgendwo");
        var sellingCustomer = new Customer("Elisa", "Huber", "elisa.huber@gmail.com", addr1, addr1);
        customerService.insert(sellingCustomer);
        JPAUtil.commit();
        printStatus("inserted Customer #1");

        var addr2 = new Address("4040", "Linz", "Irgendwo anders 2");
        var customer2 = new Customer("Matthias", "Niemand", "matzl.niemand@gmail.com", addr2, addr2);
        customerService.insert(customer2);
        JPAUtil.commit();
        printStatus("inserted Customer #2");

        var addr3 = new Address("4040", "Linz", "Ganzwoanders 3");
        var customer3 = new Customer("Andi", "Wenzl", "gefreiterwenzl@gmail.com", addr3, addr3);
        customerService.insert(customer3);
        JPAUtil.commit();
        printStatus("inserted Customer #3");

        var addr4 = new Address("4040", "Linz", "Nochmal Ganzwoanders 6");
        var customer4 = new Customer("Sepp", "Yeeterich", "sepp.yeeterich@gmail.com", addr4, addr4);
        customerService.insert(customer4);
        JPAUtil.commit();
        printStatus("inserted Customer #4");

        var articleToGet = new Article("Angelrute", "lange kräftige Angelrute", 20.0, LocalDateTime.now(), LocalDateTime.now().plusSeconds(5), sellingCustomer);
        articleService.insert(articleToGet);
        JPAUtil.commit();
        printStatus("inserted Article #1");

        var articleToSelect = new Article("Angelrute", "lange schmale Angelrute", 30.0, LocalDateTime.now(), LocalDateTime.now().plusSeconds(5), sellingCustomer);
        articleService.insert(articleToSelect);
        JPAUtil.commit();
        printStatus("inserted Article #2");

        var bid1 = new Bid(10.0, customer2, articleToGet, LocalDateTime.now());
        bidService.insert(bid1);
        JPAUtil.commit();
        printStatus("inserted Bid #1");

        var bid2 = new Bid(100.0, customer3, articleToGet, LocalDateTime.now());
        bidService.insert(bid2);
        JPAUtil.commit();
        printStatus("inserted Bid #2");

        var bid3 = new Bid(95.0, customer4, articleToGet, LocalDateTime.now());
        bidService.insert(bid3);
        JPAUtil.commit();
        printStatus("inserted Bid #3");

        articleToGet = articleService.markArticleAsSold(articleToGet);
        JPAUtil.commit();
        printStatus("Bids have been evaluated");
        System.out.println("Buyer: " + articleToGet.getBuyer().getEmail());
        System.out.println("Price: " + articleToGet.getFinalPrice());

        var articles = articleService.getByNameOrDescription("Angelrute");
        System.out.println("Found following articles:");
        for(var a : articles){
            System.out.println(a.getName());
        }
    }

    private static void printStatus(String status){
        System.out.println("----------------" + status + "----------------");
    }
}
