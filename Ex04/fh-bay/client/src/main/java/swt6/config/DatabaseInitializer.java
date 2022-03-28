package swt6.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import swt6.datamodel.*;
import swt6.services.ArticleService;
import swt6.services.CustomerService;

import java.time.LocalDateTime;

@Component
@Profile("dev")
@Order(-1)
public class DatabaseInitializer implements CommandLineRunner {
    private ArticleService articleService;
    private CustomerService customerService;

    public DatabaseInitializer(ArticleService articleService, CustomerService customerService) {
        this.articleService = articleService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer("max", "mustermann", "test@gmail.com", new Address("4040", "Linz", "iwo"), new Address("3313", "Wallsee", "am kaff 5"));
        Customer customer2 = new Customer("max2", "mustermann", "test2@gmail.com", new Address("4040", "Linz", "iwo"), new Address("3313", "Wallsee", "am kaff 5"));
        Customer customer3 = new Customer("max3", "mustermann", "test3@gmail.com", new Address("4040", "Linz", "iwo"), new Address("3313", "Wallsee", "am kaff 5"));

        customerService.save(customer);
        customerService.save(customer2);
        customerService.save(customer3);

        Article article = new Article("Schwert", "altes Schwert", 100.0, LocalDateTime.now(), LocalDateTime.now(), customer, new Category("Waffen", null), BiddingState.NOT_STARTED);
        article = articleService.save(article);
    }
}
