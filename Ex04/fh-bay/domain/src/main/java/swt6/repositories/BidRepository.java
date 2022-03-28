package swt6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.datamodel.Article;
import swt6.datamodel.Bid;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByArticleOrderByAmountDesc(Article article);
}
