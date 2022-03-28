package swt6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.datamodel.Article;

public interface CategoryRepository extends JpaRepository<Article, Long> {
}
