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

    /**
     * Maneger constructer
     * create a new
     * - Searcher
     * - CategoryDAO
     * - MovieDAO
     */
    public Manager() {
        searcher = new Searcher();
        categoryDAO = new CategoryDAO();
        movieDAO = new MovieDAO();
    }

    /**
     * returns an Arraylist of all categories from DAO to whoever requested it.
     * @return
     * @throws Exception
     */
    public ArrayList<Category> getAllCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }

    /**
     * returns an Arraylist of all movies from DAO to whoever requested it.
     * @return
     * @throws Exception
     */
    public ArrayList<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }

    /**
     * returns an Arraylist of movies based on the give query to whoever requested it.
     * @param query
     * @return
     * @throws Exception
     */
    public List<Movie> searchMovies(String query) throws Exception{
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = searcher.search(allMovies, query);
        return searchResult;
    }

    /**
     * gives new movie to DAO and returns an updated movie with a Id from db
     * @param movie
     * @return
     * @throws Exception
     */
    public Movie addNewMovie(Movie movie) throws Exception {
        movie.setId(movieDAO.createMovie(movie));
        return movie;
    }

    /**
     * gives new Category to DAO and returns an updated category with a Id from db
     * @param category
     * @return
     * @throws Exception
     */
    public Category addCategory(Category category) throws Exception {
        category.setId(categoryDAO.createCategory(String.valueOf(category)));
        return category;
    }

    /**
     * removes given Category from DB
     * @param category
     * @throws Exception
     */
    public void removeCategory(Category category) throws  Exception {
        categoryDAO.deleteCategory(category.getID());
    }

    /**
     * Removes given Movie From DB
     * @param movie
     * @throws Exception
     */
    public void removeMovie(Movie movie) throws Exception{
        movieDAO.deleteMovie(movie.getiD());
    }

    /**
     * Updates movie in DB
     * @param movie
     * @throws Exception
     */

    public void updateDate(Movie movie) throws Exception{
        movieDAO.setViewDate(movie.getiD());
    }

    /**
     * Get all movies based on a category Id and returns the Arraylist from DAO
     * @param categoryId
     * @return
     * @throws Exception
     */
    public List<Movie> getAllMovieCategories(int categoryId) throws Exception{
        return movieDAO.getMoviesByCategory(categoryId);
    }

    /**
     * gets all movies program recommend to be deleted
     * @return
     * @throws Exception
     */
    public List<Movie> getAllMoviesForDeletion() throws Exception {
        return movieDAO.getMoviesForDeletion();
    }

    /**
     * Changes values of a movie
     * @param changedMovie
     * @throws Exception
     */
    public void changeMovie(Movie changedMovie) throws Exception{
        movieDAO.updateMovie(changedMovie);
    }


    public ArrayList<Category> getCategoriesForMovie(Movie movie) throws Exception{
        return categoryDAO.getCategoriesByMovie(movie);
    }
}
