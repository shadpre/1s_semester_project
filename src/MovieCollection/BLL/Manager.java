package MovieCollection.BLL;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BE.Subject;
import MovieCollection.DAL.CategoryDAO;
import MovieCollection.DAL.MovieDAO;
import MovieCollection.DAL.SubjectDAO;

import java.util.ArrayList;

public class Manager {
    private SubjectDAO subjectDAO;
    private CategoryDAO categoryDAO;
    private MovieDAO movieDAO;

    public Manager() {
        subjectDAO = new SubjectDAO();
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
