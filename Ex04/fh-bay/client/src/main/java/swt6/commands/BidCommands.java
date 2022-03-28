package swt6.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.datamodel.Article;
import swt6.datamodel.Bid;
import swt6.datamodel.Customer;
import swt6.services.ArticleService;
import swt6.services.BidService;
import swt6.services.CustomerService;

import java.time.LocalDateTime;

@ShellComponent
@ShellCommandGroup("Bid Commands")
public class BidCommands {
    private BidService bidService;
    private ArticleService articleService;
    private CustomerService customerService;

    public BidCommands(BidService bidService, ArticleService articleService, CustomerService customerService) {
        this.bidService = bidService;
        this.articleService = articleService;
        this.customerService = customerService;
    }

    @ShellMethod("place bid")
    public void placeBid(@ShellOption Long articleId, @ShellOption double amount, @ShellOption Long bidder){
        Article article = articleService.findById(articleId).get();
        Customer customer = customerService.findById(bidder).get();
        Bid bid = new Bid(amount, customer, article, LocalDateTime.now());

        bidService.placeBidForArticle(article.getId(), bid);
    }
}
