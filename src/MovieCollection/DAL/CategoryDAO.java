package MovieCollection.DAL;

import MovieCollection.BE.Category;
import MovieCollection.BLL.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class CategoryDAO implements ICategoryDAO{
    @Override
    public int createCategory(String category) throws Exception {

        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "insert into Category (Name) Values (?)";

            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, category);

            var resultSet = statement.getGeneratedKeys();
            return resultSet.getInt(1);


        }
        catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public Category getCategory(int id) throws Exception {

        //TODO create get category
        return null;
    }

    @Override
    public ArrayList<Category> getAllCategories() throws Exception {

        //TODO create create all categories

        return null;
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
