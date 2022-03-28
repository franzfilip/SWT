package swt6.dao;

import swt6.domain.Article;
import swt6.domain.Bid;

import java.util.List;

public interface BidService extends BaseService<Bid>{
    List<Bid> getHighestBidsForArticle(Article article);
}
