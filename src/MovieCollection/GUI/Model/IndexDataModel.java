package MovieCollection.GUI.Model;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BLL.Manager;
import MovieCollection.BLL.Util.TupleCategory;
import MovieCollection.BLL.Util.TupleMovie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IndexDataModel {
    private ObservableList<Movie> oldMovies;
    private ObservableList<Movie> movieObservableList;
    private ObservableList<Category> categoryObservableList;
    private ObservableList<Movie> movieObservableListByCategory;
    private TupleMovie tbMovie;
    private TupleCategory tbCat;
    private Manager manager;


    public IndexDataModel() throws Exception{
        oldMovies = FXCollections.observableArrayList();
        movieObservableList = FXCollections.observableArrayList();
        categoryObservableList = FXCollections.observableArrayList();
        movieObservableListByCategory = FXCollections.observableArrayList();

        manager = new Manager();

        tbMovie = new TupleMovie();
        tbCat = new TupleCategory();

        categoryObservableList.addAll(manager.getAllCategories());
        movieObservableList.addAll(manager.getAllMovies());
    }

    public ObservableList getMovieObservableList() {
        return movieObservableList;
    }
    public ObservableList getCategoryObservableList() {
        return categoryObservableList;
    }
    public ObservableList getAllOldMovies(){
        float minimumRating = 6;

        for(Movie movie:movieObservableList){
            if (movie.getPersonalRating() < minimumRating){
                oldMovies.add(movie);
            } //else if () {} //TODO check if movie have not been seen more than 2 years ago
        }

        return oldMovies;
    }

    public String getTittleForMovie(Movie movie){
        return movie.getMovieTittle();
    }
    public String getIMDBRatingForMovie(Movie movie) {
        return String.valueOf(movie.getImdbRating());
    }
    public String getLastViewed(Movie movie) {
        return String.valueOf(movie.getLastPlayDate());
    }

    public String getPersonalRating(Movie movie) {
        return String.valueOf(movie.getPersonalRating());
    }

    public ObservableList getMoviesFromCategory(int cateforyId) {
        movieObservableListByCategory.clear();

        for (Movie movie: movieObservableList) { //TODO Make it add movies by the category Id
            movieObservableListByCategory.add(movie);
        }
        return movieObservableListByCategory;
    }

    public void openAddMovieWindow() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/addMovie.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.showAndWait();


        Movie tbMovieWindowResult = tbMovie.getTbMovie();
        if (tbMovieWindowResult == null || movieObservableList.contains(tbMovieWindowResult)) return;

        movieObservableList.add(manager.addNewMovie(tbMovieWindowResult));

        tbMovie.setTbMovie(null);
    }

    public void openAddCategoryWindow() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/addCategory.fxml"));
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.showAndWait();

        Category tbCategoryEWindowResult = tbCat.getCategory();
        if (tbCategoryEWindowResult == null || categoryObservableList.contains(tbCategoryEWindowResult)) return;

        categoryObservableList.add(manager.addCategory(tbCategoryEWindowResult));

        tbCat.setCategory(null);
    }

    public void startPopUp() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/PopUpDelete.fxml"));
        Parent root3 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root3));
        stage.showAndWait();
    }

    public void searchForMovie(String query) throws Exception {
        List<Movie> searchResults = manager.searchMovies(query);
        movieObservableListByCategory.clear();
        movieObservableListByCategory.addAll(searchResults);

        System.out.println("Searching..." + movieObservableList);
    }

    public void deleteCategory(Category selectedCategory) throws Exception {

        if (selectedCategory == null || !categoryObservableList.contains(selectedCategory))  throw new Exception("Item Does Not Exist");

        manager.removeCatefory(selectedCategory);

        categoryObservableList.clear();
        categoryObservableList.addAll(manager.getAllCategories());

        //TODO DELETE CATEGORY

    }

    public void deleteMovie(Movie selectedMovie) throws Exception {
        if (selectedMovie == null || !movieObservableList.contains(selectedMovie))  throw new Exception("Item Does Not Exist");

        manager.removeMovie(selectedMovie);

        movieObservableList.clear();
        movieObservableList.addAll(manager.getAllMovies());
        //TODO DELETE MOVIE

    }
    public void filterImdb(String rating, int categoryId) throws Exception {
        ArrayList<Movie> ratedList = new ArrayList<>();
        getMoviesFromCategory(categoryId);
        float rate = Float.parseFloat(rating);

        for (Movie movie:movieObservableListByCategory) {
            if (movie.getImdbRating() >= rate){
                ratedList.add(movie);
            }
        }

        movieObservableListByCategory.clear();
        movieObservableListByCategory.addAll(ratedList);
    }

    public void filterPersonal(String rating, int categoryId) throws Exception {
        ArrayList<Movie> ratedList = new ArrayList<>();
        getMoviesFromCategory(categoryId);
        float rate = Float.parseFloat(rating);

        for (Movie movie:movieObservableListByCategory) {
            if (movie.getPersonalRating() >= rate){
                ratedList.add(movie);
            }
        }

        movieObservableListByCategory.clear();
        movieObservableListByCategory.addAll(ratedList);
    }

    public void openMovieInPlayer(Movie movie) throws Exception{
        File file = new File(movie.getLocalFilePath());
        Desktop.getDesktop().open(file);

        //TODO update last Vive movie date.
    }

}
