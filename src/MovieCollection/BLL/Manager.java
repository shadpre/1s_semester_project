package MovieCollection.BLL;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BE.Subject;
import MovieCollection.BLL.Util.Searcher;
import MovieCollection.DAL.CategoryDAO;
import MovieCollection.DAL.MovieDAO;
import MovieCollection.DAL.SubjectDAO;

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
        //return movieDAO.getAllMovies();
        ArrayList<Movie> arr = new ArrayList<>();
        arr.add(new Movie("Scream", 4, 8, "PATH",-1));
        arr.add(new Movie("dam", 7, 2, "PATH", -1));

        return arr;
    }

    public List<Movie> searchMovies(String query) throws Exception{
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchResult = searcher.search(allMovies, query);
        return searchResult;
    }

    public Movie addNewMovie(Movie movie) throws Exception {
        //TODO INSERT DATA INTO DAO Needs to add new ID to Movie before return
        return movie;
    }

    public Category addCategory(Category category) throws Exception {
        //TODO INSERT DATA INTO DAO Needs to add new ID to Category before return
        return category;
    }

    public void removeCatefory(Category category) throws  Exception {
        //TODO delete Category from DAO
    }

    public void removeMovie(Movie movie) throws Exception{
        //TODO delete movie from DAO
    }
}
