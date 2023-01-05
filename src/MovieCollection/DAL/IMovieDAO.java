package MovieCollection.DAL;

import MovieCollection.BE.Movie;

import java.util.ArrayList;

public interface IMovieDAO {
   ArrayList<Movie> getAllMovies() throws Exception;
   void deleteMovie(int iD) throws Exception;
   void createMovie(Movie movie) throws Exception;
   Movie updateMovie(Movie movie) throws Exception;
}
