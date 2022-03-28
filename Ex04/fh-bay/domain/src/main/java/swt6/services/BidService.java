package swt6.services;

import swt6.datamodel.Article;
import swt6.datamodel.Bid;

import java.util.List;

public interface BidService {
    Bid placeBidForArticle(Long id, Bid bid);
    List<Bid> getHighestBidsForArticle(Article article);
}
