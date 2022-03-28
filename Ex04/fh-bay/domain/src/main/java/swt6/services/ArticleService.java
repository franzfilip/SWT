package swt6.services;

import swt6.datamodel.Article;

import java.util.List;
import java.util.Optional;

public interface ArticleService {
    Article save(Article article);
    List<Article> getByNameOrDescription(String searchText);
    Article endBidProcess(Article article);
    Optional<Article> findById(Long id);
    List<Article> findAll();
}
