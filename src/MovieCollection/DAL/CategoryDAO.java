package MovieCollection.DAL;

import MovieCollection.BE.Category;

import java.util.ArrayList;

public class CategoryDAO implements ICategoryDAO{
    @Override
    public int createCategory(String category) throws Exception {

        //TODO create create category

        return 0;
    }

    @Override
    public Category getCategory(int id) throws Exception {

        //TODO create get category
        return null;
    }

    @Override
    public ArrayList<Category> getAllCategories() throws Exception {
        ArrayList<Category> allCategories = new ArrayList<>();
        //TODO create create all categories

        allCategories.add(new Category("Drama", -1)); //MOCK DATA
        return allCategories;
    }

    @Override
    public int editCategory(Category category) throws Exception {


        //TODO create update category

        return 0;
    }

    @Override
    public void deleteCategory(int id) throws Exception {



        //TODO create delete category

    }
}
