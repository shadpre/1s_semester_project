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

    public ArrayList<Subject> getAllSubjects() throws Exception{
        return subjectDAO.getAllSubjects();
    }

    public ArrayList<Category> getAllCategories() throws Exception {
        return categoryDAO.getAllCategories();
    }

    public ArrayList<Movie> getAllMovies() throws Exception {
        return movieDAO.getAllMovies();
    }
}
