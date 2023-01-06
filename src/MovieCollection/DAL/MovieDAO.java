package MovieCollection.DAL;

import MovieCollection.BE.Movie;
import MovieCollection.BLL.DatabaseConnector;

import java.sql.Connection;
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

            String sql ="SELECT * FROM MOVIE;";

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
    public void deleteMovie(int iD) throws Exception {
        //TODO create delete
    }

    @Override
    public void createMovie(Movie movie) throws Exception {
        //TODO create create movie
    }

    @Override
    public void updateMovie(Movie movie) throws Exception {
    }
}
