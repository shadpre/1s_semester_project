package MovieCollection.DAL;

import MovieCollection.BE.Category;

import java.util.ArrayList;

public interface ICategoryDAO {
    int createCategory(String category) throws Exception;
    Category getCategory(int id) throws Exception;
    ArrayList<Category> getAllCategories() throws Exception;
    int editCategory(Category category) throws Exception;
    void deleteCategory(int id) throws Exception;
}
