package rs.raf.demo.repositories.category;

import rs.raf.demo.entities.Kategorija;
import rs.raf.demo.entities.User;

import java.util.List;

public interface CategoryRepository {
    public Kategorija addCategory(Kategorija kategorija);
    public List<Kategorija> allCategory();
    public Kategorija findCategory(String name);
    public void deleteCategory(String name);
    public Kategorija updateCategory(Kategorija kategorija, String name);
}
