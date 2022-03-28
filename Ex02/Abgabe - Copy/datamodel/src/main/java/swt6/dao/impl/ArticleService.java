package swt6.dao.impl;

import swt6.dao.ServiceFactory;
import swt6.domain.Article;
import swt6.domain.Bid;
import swt6.util.JPAUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class ArticleService extends BaseService<Article> implements swt6.dao.ArticleService{

    public ArticleService() {
    }

    public ArticleService(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    Class<Article> getType() {
        return Article.class;
    }

    @Override
    public List<Article> getAll() {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        return this.entityManager.createQuery("select a from Article a", Article.class).getResultList();
    }

    @Override
    public Article markArticleAsSold(Article article) {
        var bidService = ServiceFactory.getBidService();

        if(article == null){
            throw new IllegalArgumentException("Article must not be null");
        }

        var bids = bidService.getHighestBidsForArticle(article);
        if(bids == null){
            throw new RuntimeException("Not enough Bids found");
        }

        if(bids.size() == 2){
            article.setFinalPrice(bids.get(1).getAmount());
            article.setBuyer(bids.get(0).getBidder());
        }
        else if(bids.size() == 1){
            article.setFinalPrice(bids.get(0).getAmount());
            article.setBuyer(bids.get(0).getBidder());
        }
        this.entityManager = JPAUtil.getTransactedEntityManager();
        article = this.entityManager.merge(article);

        return article;
    }

    @Override
    public List<Article> getByNameOrDescription(String searchText) {
        this.entityManager = JPAUtil.getTransactedEntityManager();
        var query = this.entityManager.createQuery("select a from Article a where a.name like :adaptedSearchText or a.description like :adaptedSearchText", Article.class);
        query.setParameter("adaptedSearchText", "%"+searchText+"%");

        return query.getResultList();
    }
}
