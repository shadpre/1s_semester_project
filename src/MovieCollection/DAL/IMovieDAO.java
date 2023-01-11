package MovieCollection.DAL;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;

import java.util.ArrayList;

public interface IMovieDAO {
   ArrayList<Movie> getAllMovies() throws Exception;
   ArrayList<Movie> getMoviesByCategory(Category category) throws Exception;
   ArrayList<Movie> getMoviesForDeletion() throws Exception;
   void deleteMovie(int id) throws Exception;
   int createMovie(Movie movie) throws Exception;
   void updateMovie(Movie movie) throws Exception;
   void setViewDate(int id) throws Exception;

}
