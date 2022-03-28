package swt6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.datamodel.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
