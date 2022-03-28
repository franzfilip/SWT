package swt6.dao;

import swt6.domain.Article;

import java.util.List;

public interface ArticleService extends BaseService<Article> {
    Article markArticleAsSold(Article article);
    List<Article> getByNameOrDescription(String searchText);
}
