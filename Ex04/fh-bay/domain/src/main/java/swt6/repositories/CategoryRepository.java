package swt6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import swt6.datamodel.Article;
import swt6.datamodel.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
