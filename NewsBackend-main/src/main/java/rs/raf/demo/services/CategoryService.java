package rs.raf.demo.services;

import rs.raf.demo.entities.Kategorija;

import rs.raf.demo.repositories.category.CategoryRepository;

import javax.inject.Inject;
import java.util.List;

public class CategoryService {

    public CategoryService() {
        System.out.println(this);
    }

    @Inject
    private CategoryRepository categoryRepository;

    public Kategorija addCategory(Kategorija kategorija) {
        return this.categoryRepository.addCategory(kategorija);
    }

    public List<Kategorija> allCategory() {
        return this.categoryRepository.allCategory();
    }

    public Kategorija findCategory(String name) {
        return this.categoryRepository.findCategory(name);
    }

    public void deleteCategory(String name) {
        this.categoryRepository.deleteCategory(name);
    }


    public Kategorija updateCategory(Kategorija kategorija, String name) {
        return this.categoryRepository.updateCategory(kategorija, name);
    }
}
