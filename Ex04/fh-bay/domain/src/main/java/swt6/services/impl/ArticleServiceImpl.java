package swt6.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.datamodel.Article;
import swt6.datamodel.BiddingState;
import swt6.repositories.ArticleRepository;
import swt6.repositories.BidRepository;
import swt6.services.ArticleService;
import swt6.services.BidService;
import swt6.services.CategoryService;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {
    private ArticleRepository articleRepository;
    private BidService bidService;
    private CategoryService categoryService;

    public ArticleServiceImpl(ArticleRepository articleRepository, BidService bidService, CategoryService categoryService) {
        this.articleRepository = articleRepository;
        this.bidService = bidService;
        this.categoryService = categoryService;
    }

    @Override
    @Transactional
    public Article save(Article article) {
        if(article == null){
            throw new IllegalArgumentException("Article must not be null");
        }

        if(article.getCategory() != null){
            article.setCategory(categoryService.save(article.getCategory()));
        }

        return articleRepository.save(article);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Article> getByNameOrDescription(String searchText){
        return articleRepository.findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(searchText, searchText);
    }

    @Override
    public Article endBidProcess(Article article) {
        if(article == null){
            throw new IllegalArgumentException("Article must not be null");
        }
        var bids = bidService.getHighestBidsForArticle(article);
        if(bids.size() == 2){
            article.setFinalPrice(bids.get(1).getAmount());
            article.setBuyer(bids.get(0).getBidder());
        }
        else if(bids.size() == 1){
            article.setFinalPrice(bids.get(0).getAmount());
            article.setBuyer(bids.get(0).getBidder());
        }
        else if(bids.size() == 0){
            article.setState(BiddingState.NOT_SOLD);
        }

        article = this.articleRepository.save(article);

        return article;
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll();
    }


}
