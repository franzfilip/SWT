package swt6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.datamodel.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
//    List<Article> findArticlesByDescriptionLikeAndNameIsLike
    List<Article> findByNameContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String name, String description);
}
