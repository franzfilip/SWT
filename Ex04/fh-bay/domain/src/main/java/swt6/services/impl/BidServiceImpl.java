package swt6.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.datamodel.Article;
import swt6.datamodel.Bid;
import swt6.datamodel.BiddingState;
import swt6.datamodel.Customer;
import swt6.repositories.ArticleRepository;
import swt6.repositories.BidRepository;
import swt6.services.BidService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BidServiceImpl implements BidService {
    private BidRepository bidRepository;
    private ArticleRepository articleRepository;

    public BidServiceImpl(BidRepository bidRepository, ArticleRepository articleRepository) {
        this.bidRepository = bidRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    @Transactional
    public Bid placeBidForArticle(Long id, Bid bid) {
        if(bid == null){
            throw new IllegalArgumentException("Bid must not be null");
        }

        Article article = articleRepository.getById(id);

        if(article == null){
            throw new IllegalArgumentException("Article must not be null");
        }

        if(article.getState() != BiddingState.STARTED){
            throw new IllegalStateException("Article not available for bidding");
        }

        bid.setArticle(article);
        return bidRepository.save(bid);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Bid> getHighestBidsForArticle(Article article){
        if(article == null){
            throw new IllegalArgumentException("Article must not be null");
        }
        return bidRepository.findByArticleOrderByAmountDesc(article).stream().limit(2).collect(Collectors.toList());
    }
}
