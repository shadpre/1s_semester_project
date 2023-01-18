package MovieCollection.DAL;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BLL.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class CategoryDAO implements ICategoryDAO{
    /**
     *Creates a new category in DB
     * @param category
     * @return ID of the new Category
     * @throws Exception
     */
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

    /**
     * Get the category from DB by ID
     * @param id
     * @return The category
     * @throws Exception
     */
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

    /**
     * Get a list of all categories in the DB
     * @return List of categories
     * @throws Exception
     */
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

    /**
     * Deletes category and relations from the DB from the ID
     * @param id
     * @throws Exception
     */
    @Override
    public void deleteCategory(int id) throws Exception {

        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "Delete CatMovie where CategoryId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.executeUpdate();


            query = "DELETE Category WHERE ID = ?";

            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            int result = statement.executeUpdate();

            if (result == 0){
                Exception ex = new Exception("Intet slettet");
            }
        }

    }

    /**
     * Returns a list of categories for a specific movie
     * @param movie
     * @return
     * @throws Exception
     */
    @Override
    public ArrayList<Category> getCategoriesByMovie(Movie movie) throws Exception{
        ArrayList<Category> MovieCategories = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "SELECT Id, Name FROM Category WHERE Id IN " +
                    "(SELECT DISTINCT CategoryId FROM CatMovie WHERE MovieId = ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, movie.getiD());

            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                MovieCategories.add(new Category(
                        rs.getString("Name"),
                        rs.getInt("Id")
                ));
            }
        }
        return MovieCategories;
    }
}
