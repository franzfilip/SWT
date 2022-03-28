package swt6.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swt6.datamodel.Category;
import swt6.repositories.CategoryRepository;
import swt6.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category save(Category category){
        if(category == null){
            throw new IllegalArgumentException("Category must not be null");
        }

        return categoryRepository.save(category);
    }
}
