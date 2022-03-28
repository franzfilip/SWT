package swt6.dao.impl;

import swt6.domain.Article;
import swt6.domain.Bid;
import swt6.domain.Customer;
import swt6.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class BidService extends BaseService<Bid> implements swt6.dao.BidService{

    public BidService() {
    }

    public BidService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    Class<Bid> getType() {
        return Bid.class;
    }

    @Override
    public List<Bid> getAll() {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        return this.entityManager.createQuery("select b from Bid b", Bid.class).getResultList();
    }

    @Override
    public List<Bid> getHighestBidsForArticle(Article article) {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        if(article == null){
            throw new IllegalArgumentException("Article must not be null");
        }

        var query = this.entityManager.createQuery("select b from Bid b where b.article.id = :id order by amount desc", Bid.class);
        query.setParameter("id", article.getId());
        query.setMaxResults(2);

        var result = query.getResultList();

        if(result.size() == 0){
            return null;
        }

        return result;
    }
}
