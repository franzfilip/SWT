package swt6.commands;

import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import swt6.datamodel.Article;
import swt6.datamodel.BiddingState;
import swt6.datamodel.Category;
import swt6.datamodel.Customer;
import swt6.services.ArticleService;
import swt6.services.CustomerService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ShellComponent
@ShellCommandGroup("Article Commands")
public class ArticleCommands {
    private ArticleService articleService;
    private CustomerService customerService;

    public ArticleCommands(ArticleService articleService, CustomerService customerService) {
        this.articleService = articleService;
        this.customerService = customerService;
    }

    @ShellMethod("create Article")
    public void createArticle(@ShellOption String name,
                              @ShellOption String description,
                              @ShellOption double startPrice,
                              @ShellOption Long customerId,
                              @ShellOption BiddingState state,
                              @ShellOption String categoryName){
        Customer customer = customerService.findById(customerId).get();
        Category category = new Category(categoryName, null);
        Article article = new Article(name, description, startPrice, LocalDateTime.now(), LocalDateTime.now(), customer, category, state);
        System.out.println(articleService.save(article));
    }

    @ShellMethod("search Article by Name and Desc")
    public void searchArticle(@ShellOption String searchText){
        System.out.println(articleService.getByNameOrDescription(searchText));
    }

    @ShellMethod("start bidding for article")
    public void enableArticle(@ShellOption Long id){
        Article article = articleService.findById(id).get();
        article.setState(BiddingState.STARTED);
        System.out.println(articleService.save(article));
    }

    @ShellMethod("end bid process for article")
    public void stopBiddingProcess(@ShellOption Long id){
        Article article = articleService.findById(id).get();

        System.out.println(articleService.endBidProcess(article));
    }

    @ShellMethod("get all articles")
    public void getAllArticles(){
        articleService.findAll().forEach(System.out::println);
    }
}
