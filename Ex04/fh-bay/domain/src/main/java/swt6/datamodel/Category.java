package swt6.datamodel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {
    @Id @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private Set<Category> subcategories = new HashSet<>();

    public Category(){}

    public Category(String name, Category parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public void addSubCategory(Category subcategory){
        if(subcategory == null){
            throw new IllegalArgumentException("Subcategory must not be null");
        }

        if(subcategory.getParentCategory() != null){
            subcategory.getParentCategory().getSubcategories().remove(subcategory);
        }

        subcategory.setParentCategory(this);
        subcategories.add(subcategory);
    }

    public void removeSubCategory(Category subcategory){
        if(subcategory == null){
            throw new IllegalArgumentException("Subcategory must not be null");
        }

        if(subcategory.getParentCategory() == null){
            throw new IllegalStateException("Subcategory must have a parent");
        }

        subcategories.remove(subcategory);
        subcategory.setParentCategory(null);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public Set<Category> getSubcategories() {
        return subcategories;
    }

    public void setSubcategories(Set<Category> subcategories) {
        this.subcategories = subcategories;
    }
}
