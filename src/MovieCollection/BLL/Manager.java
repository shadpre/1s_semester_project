package MovieCollection.BLL;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BLL.Util.Searcher;
import MovieCollection.DAL.CategoryDAO;
import MovieCollection.DAL.MovieDAO;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private Searcher searcher;
    private CategoryDAO categoryDAO;
    private MovieDAO movieDAO;

    public Manager() {
        searcher = new Searcher();
        categoryDAO = new CategoryDAO();
        movieDAO = new MovieDAO();
    }

    public ArrayList<Category> getAllCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }

    public ArrayList<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    public List<Movie> searchMovies(String query) throws Exception{
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = searcher.search(allMovies, query);
        return searchResult;
    }

    public Movie addNewMovie(Movie movie) throws Exception {
        movie.setId(movieDAO.createMovie(movie));
        return movie;
    }

    public Category addCategory(Category category) throws Exception {
        category.setId(categoryDAO.createCategory(String.valueOf(category)));
        return category;
    }

    public void removeCategory(Category category) throws  Exception {
        categoryDAO.deleteCategory(category.getID());
    }

    public void removeMovie(Movie movie) throws Exception{
        movieDAO.deleteMovie(movie.getiD());
    }

    public void updateDate(Movie movie) throws Exception{
        movieDAO.setViewDate(movie.getiD());
    }

    public List<Movie> getAllMovieCategories(int categoryId) throws Exception{
        return movieDAO.getMoviesByCategory(categoryId);
    }

    public List<Movie> getAllMoviesForDeletion() throws Exception {
        return movieDAO.getMoviesForDeletion();
    }
}
