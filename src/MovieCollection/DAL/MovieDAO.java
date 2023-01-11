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
    @Override
    public ArrayList<Movie> getAllMovies() throws Exception {

        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             Statement stmt = connection.createStatement()){

            String sql ="SELECT Id, Name, ImdbRating, PersonalRating, Filelink, CONVERT(NVARCHAR,LastView,20) LastView FROM MOVIE;";

            ResultSet rs = stmt.executeQuery(sql);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while(rs.next()) {
                int iD = rs.getInt("Id");
                String Name = rs.getString("Name");
                int imdb = rs.getInt("ImdbRating");
                int personalRating = rs.getInt("PersonalRating");
                String path = rs.getString("Filelink");
                LocalDateTime lastViewDate = LocalDateTime.parse(rs.getString("LastView"), formatter);

                Movie movie = new Movie(Name, imdb,personalRating,path,iD);
                movie.setLastPlayDate(lastViewDate);

                allMovies.add(movie);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return allMovies;

    }

    @Override
    public ArrayList<Movie> getMoviesByCategory(Category category) throws Exception{

        ArrayList<Movie> allMovies = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getInstance().getConnection()){


            String sql ="SELECT Id, Name, ImdbRating, PersonalRating, Filelink, CONVERT(NVARCHAR,LastView,20) LastView FROM MOVIE WHERE " +
                    "Id IN (SELECT DISTINCT MovieId FROM CatMovie WHERE CategoryId = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, category.getID());

            ResultSet rs = stmt.executeQuery();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            while(rs.next()) {
                int iD = rs.getInt("Id");
                String Name = rs.getString("Name");
                int imdb = rs.getInt("ImdbRating");
                int personalRating = rs.getInt("PersonalRating");
                String path = rs.getString("Filelink");
                LocalDateTime lastViewDate = LocalDateTime.parse(rs.getString("LastView"), formatter);

                Movie movie = new Movie(Name, imdb,personalRating,path,iD);
                movie.setLastPlayDate(lastViewDate);

                allMovies.add(movie);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return allMovies;
    }

    @Override
    public void deleteMovie(int id) throws Exception {


        try (Connection connection = DatabaseConnector.getInstance().getConnection()){
            String query = "DELETE Movie WHERE ID = ?";

            PreparedStatement statement = connection.prepareStatement(query);
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
            String query = "INSERT INTO Movie ( Name, ImdbRating, PersonalRating, Filelink) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1,movie.getMovieTittle());
            statement.setFloat(2, movie.getImdbRating());
            statement.setFloat(3, movie.getPersonalRating());
            statement.setString(4, movie.getLocalFilePath());

            var res = statement.executeQuery();

            if (res.next()) {
                return res.getInt(1);
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

            int result = statement.executeUpdate();

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
}
