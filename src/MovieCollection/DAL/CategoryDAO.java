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
            statement.executeQuery();

            var resultSet = statement.getResultSet();

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        catch (Exception ex){
            throw ex;
        }
        return -1;
    }

    @Override
    public Category getCategory(int id) throws Exception {


        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "SELECT Name FROM Category WHERE ID = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.executeQuery();

            var rs = statement.getResultSet();
            if (rs.next()){
            return new Category(rs.getString(1),id);}

        }
        return null;
    }

    @Override
    public ArrayList<Category> getAllCategories() throws Exception {
        ArrayList<Category> allCategories = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "SELECT Id, Name FROM Category";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeQuery();

            var rs = statement.getResultSet();

            while (rs.next()){
                allCategories.add(new Category(
                        rs.getString("Name"),
                        rs.getInt("Id")
                ));
            }
        }
        return allCategories;
    }

    @Override
    public int editCategory(Category category) throws Exception {


        //TODO create update category

        return 0;
    }

    @Override
    public void deleteCategory(int id) throws Exception {

        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "DELETE Category WHERE ID = ?";

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            int result = statement.executeUpdate();

            if (result == 0){
                Exception ex = new Exception("Intet slettet");
            }
        }

    }
}
