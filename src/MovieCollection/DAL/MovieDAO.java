package MovieCollection.DAL;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BLL.DatabaseConnector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MovieDAO implements IMovieDAO{
    private DateTimeFormatter formatter;
    public MovieDAO() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Override
    public ArrayList<Movie> getAllMovies() throws Exception {

        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             Statement stmt = connection.createStatement()){

            String sql ="SELECT Id, Name, ROUND(ImdbRating, 2) ImdbRating, ROUND(PersonalRating, 2) PersonalRating, Filelink, CONVERT(NVARCHAR,LastView,20) LastView FROM MOVIE;";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                int iD = rs.getInt("Id");
                String Name = rs.getString("Name");
                float imdb = rs.getFloat("ImdbRating");
                float personalRating = rs.getFloat("PersonalRating");
                String path = rs.getString("Filelink");
                LocalDateTime lastViewDate = LocalDateTime.parse(rs.getString("LastView"), formatter);

                Movie movie = new Movie(Name, imdb,personalRating,path,iD);
                movie.setLastPlayDate(lastViewDate);
                movie.setCategories(getCategories(movie));

                allMovies.add(movie);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return allMovies;

    }

    @Override
    public ArrayList<Movie> getMoviesByCategory(int categoryId) throws Exception{

        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String sql ="SELECT Id, Name, ROUND(ImdbRating, 2) ImdbRating, ROUND(PersonalRating, 2) PersonalRating, Filelink, CONVERT(NVARCHAR,LastView,20) LastView FROM MOVIE WHERE " +
                    "Id IN (SELECT DISTINCT MovieId FROM CatMovie WHERE CategoryId = ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, categoryId);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int iD = rs.getInt("Id");
                String Name = rs.getString("Name");
                float imdb = rs.getFloat("ImdbRating");
                float personalRating = rs.getFloat("PersonalRating");
                String path = rs.getString("Filelink");
                LocalDateTime lastViewDate = LocalDateTime.parse(rs.getString("LastView"), formatter);

                Movie movie = new Movie(Name, imdb,personalRating,path,iD);
                movie.setLastPlayDate(lastViewDate);
                movie.setCategories(getCategories(movie));

                allMovies.add(movie);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return allMovies;
    }

    @Override
    public  ArrayList<Movie> getMoviesForDeletion() throws Exception{
        ArrayList<Movie> allMovies = new ArrayList<>();
        try(Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "SELECT Id, Name, ImdbRating, PersonalRating, Filelink, CONVERT(NVARCHAR,LastView,20) LastView " +
                    "FROM Movie WHERE LastView < (DATEADD(YEAR,-2,GETDATE())) and PersonalRating < 6";

            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();


            while(rs.next()) {
                int iD = rs.getInt("Id");
                String Name = rs.getString("Name");
                float imdb = rs.getFloat("ImdbRating");
                float personalRating = rs.getFloat("PersonalRating");
                String path = rs.getString("Filelink");
                LocalDateTime lastViewDate = LocalDateTime.parse(rs.getString("LastView"), formatter);

                Movie movie = new Movie(Name, imdb,personalRating,path,iD);
                movie.setLastPlayDate(lastViewDate);

                allMovies.add(movie);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }
        return allMovies;
    }


    @Override
    public void deleteMovie(int id) throws Exception {


        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "DELETE CatMovie WHERE MovieId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            query = "DELETE Movie WHERE ID = ?";

            statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            int result = statement.executeUpdate();

            if (result == 0){
                Exception ex = new Exception("Intet slettet");
                throw ex;
            }
        }
    }

    @Override
    public int createMovie(Movie movie) throws Exception {

        try(Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "INSERT INTO Movie ( Name, ImdbRating, PersonalRating, Filelink, LastView) VALUES (?,?,?,?,GETDATE())";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,movie.getMovieTittle());
            statement.setFloat(2, movie.getImdbRating());
            statement.setFloat(3, movie.getPersonalRating());
            statement.setString(4, movie.getLocalFilePath());

            var res = statement.executeQuery();



            if (res.next()) {
                int result = res.getInt(1);
                query = "INSERT INTO CatMovie (CategoryId, MovieId) Values (?, ?)";

                for (Category category: movie.getCategories()
                ) {
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, category.getID());
                    statement.setInt(2, result);
                    statement.executeUpdate();
                }
                return result;
            }

        }
        return -1;
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {

        try(Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "UPDATE Movie SET Name = ?, ImdbRating = ?, PersonalRating = ?, FileLink = ? WHERE Id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1,movie.getMovieTittle());
            statement.setFloat(2, movie.getImdbRating());
            statement.setFloat(3, movie.getPersonalRating());
            statement.setString(4, movie.getLocalFilePath());
            statement.setInt(5, movie.getiD());

            int result = statement.executeUpdate();

            query = "DELETE CatMovie WHERE MovieId = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, movie.getiD());
            statement.executeUpdate();

            query = "INSERT INTO CatMovie (CategoryId, MovieId) Values (?, ?)";

            for (Category category: movie.getCategories()
            ) {
                statement = connection.prepareStatement(query);
                statement.setInt(1, category.getID());
                statement.setInt(2, movie.getiD());
                statement.executeUpdate();
            }

            if (result == 0){
                Exception ex = new Exception("Intet opdateret");
                throw ex;
            }
        }
    }

    @Override
    public void setViewDate(int id) throws Exception{
        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "UPDATE Movie SET LastView = GETDATE() WHERE Id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setInt(1, id);

            int result = statement.executeUpdate();

            if (result == 0){
                Exception ex = new Exception("Intet opdateret");
                throw ex;
            }
        }
    }


    /**
     * This is duplicate code to avoid a infinite loop
     * original is in category DAO
     * @param movie
     * @return
     * @throws Exception
     */
    private ArrayList<Category> getCategories(Movie movie) throws Exception{
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
