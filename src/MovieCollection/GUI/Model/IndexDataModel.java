package MovieCollection.GUI.Model;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BLL.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class IndexDataModel {
    private ObservableList<Movie> oldMovies;
    private ObservableList<Movie> movieObservableList;
    private ObservableList<Category> categoryObservableList;
    private ObservableList<Movie> movieObservableListByCategory;
    private Manager manager;


    /**
     * Constructor
     * Initialises data to list and creates observable list and the manager
     * @throws Exception
     */
    public IndexDataModel() throws Exception{
        oldMovies = FXCollections.observableArrayList();
        movieObservableList = FXCollections.observableArrayList();
        categoryObservableList = FXCollections.observableArrayList();
        movieObservableListByCategory = FXCollections.observableArrayList();

        manager = new Manager();

        categoryObservableList.addAll(manager.getAllCategories());
        movieObservableList.addAll(manager.getAllMovies());
    }

    public ObservableList getMovieObservableList() {
        return movieObservableList;
    }
    public ObservableList getCategoryObservableList() {
        return categoryObservableList;
    }

    /**
     * clear old movie list and gets and new list from manager for movies that it recommend to be deleted.
     * @return
     * @throws Exception
     */
    public ObservableList getAllOldMovies() throws Exception{
        oldMovies.clear();
        oldMovies.addAll(manager.getAllMoviesForDeletion());
        return oldMovies;
    }

    /**
     * get tittle for given Movie
     * and returns it as a string
     * @param movie
     * @return
     */
    public String getTittleForMovie(Movie movie){
        return movie.getMovieTittle();
    }

    /**
     * get IMDB rating for given Movie
     * and returns it as a string
     * @param movie
     * @return
     */
    public String getIMDBRatingForMovie(Movie movie) {
        return String.valueOf(movie.getImdbRating());
    }

    /**
     * get Listview date for given Movie
     * and returns it as a string
     * @param movie
     * @return
     */
    public String getLastViewed(Movie movie) {
        return String.valueOf(movie.getLastPlayDate());
    }

    /**
     * get peregrinating for given Movie
     * and returns it as a string
     * @param movie
     * @return
     */
    public String getPersonalRating(Movie movie) {
        return String.valueOf(movie.getPersonalRating());
    }

    /**
     * clear the list of categories, and return a new one from manager based on category Id
     * @param categoryId
     * @return
     * @throws Exception
     */
    public ObservableList getMoviesFromCategory(int categoryId) throws Exception{
        movieObservableListByCategory.clear();
        movieObservableListByCategory.setAll(manager.getAllMovieCategories(categoryId));

        return movieObservableListByCategory;
    }


    /**
     * opens the add movie window
     * @throws Exception
     */
    public void openAddMovieWindow() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/addMovie.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.showAndWait();

        System.out.println("movie Added");
    }

    /**
     * adds new movie to DB and MovieObservable list
     * @param movie
     * @throws Exception
     */

    public void addMovieConfirm(Movie movie) throws Exception {
        if (movie == null || movieObservableList.contains(movie)) return;

        movieObservableList.add(manager.addNewMovie(movie));
    }

    /**
     * Opens add category Window
     * @throws Exception
     */
    public void openAddCategoryWindow() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/addCategory.fxml"));
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.showAndWait();

        categoryObservableList.clear();
        categoryObservableList.addAll(manager.getAllCategories());
    }

    /**
     * adds new category to DB
     * @param category
     * @throws Exception
     */
    public void addCategoryConfirm(Category category) throws Exception {
        if (category == null || categoryObservableList.contains(category)) return;
        manager.addCategory(category);

    }

    /**
     * OPens edit movie window
     * @throws Exception
     */
    public void editMovie() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/EditMovie.fxml"));
        Parent root2 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root2));
        stage.showAndWait();
    }

    /**
     * Saves changes to movie in DB
     * @param chosenMovie
     * @throws Exception
     */
    public void editMovieConfirm(Movie chosenMovie) throws Exception{
        if (chosenMovie == null) return;
        manager.changeMovie(chosenMovie);
    }

    /**
     * opens the start pop-up window
     * @throws Exception
     */
    public void startPopUp() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/PopUpDelete.fxml"));
        Parent root3 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root3));
        stage.showAndWait();
    }

    /**
     * sends Query to manager and edits the observable list to display the query result.
     * @param query
     * @throws Exception
     */
    public void searchForMovie(String query) throws Exception {
        List<Movie> searchResults = manager.searchMovies(query);
        movieObservableListByCategory.clear();
        movieObservableListByCategory.addAll(searchResults);

        System.out.println("Searching..." + movieObservableList);
    }

    /**
     * Delete the given category from db, and refreshes category list
     * @param selectedCategory
     * @throws Exception
     */
    public void deleteCategory(Category selectedCategory) throws Exception {

        if (selectedCategory == null || !categoryObservableList.contains(selectedCategory))  throw new Exception("Item Does Not Exist");

        manager.removeCategory(selectedCategory);

        categoryObservableList.clear();
        categoryObservableList.addAll(manager.getAllCategories());
    }

    /**
     * delete the given  movie from db and refreshes the movie list
     * @param selectedMovie
     * @throws Exception
     */
    public void deleteMovie(Movie selectedMovie) throws Exception {
        if (selectedMovie == null || !movieObservableList.contains(selectedMovie))  throw new Exception("Item Does Not Exist");

        manager.removeMovie(selectedMovie);

        movieObservableList.clear();
        movieObservableList.addAll(manager.getAllMovies());
    }

    /**
     * sorts movies after the given imdb rating as the lowest possible value
     * @param rating
     * @param categoryId
     * @throws Exception
     */
    public void filterImdb(String rating, int categoryId) throws Exception {
        if (rating.isEmpty()) return;

        ArrayList<Movie> ratedList = new ArrayList<>();
        getMoviesFromCategory(categoryId);
        float rate = Float.parseFloat(rating.replace(',','.'));

        for (Movie movie:movieObservableListByCategory) {

            if (movie.getImdbRating() >= rate){
                ratedList.add(movie);
            }
        }

        System.out.println(ratedList);

        movieObservableListByCategory.clear();
        movieObservableListByCategory.addAll(ratedList);
    }

    /**
     * sorts movies after the given personal rating as the lowest possible value
     * @param rating
     * @param categoryId
     * @throws Exception
     */
    public void filterPersonal(String rating, int categoryId) throws Exception {
        if (rating.isEmpty()) return;

        ArrayList<Movie> ratedList = new ArrayList<>();
        getMoviesFromCategory(categoryId);
        float rate = Float.parseFloat(rating.replace(',','.'));

        for (Movie movie:movieObservableListByCategory) {
            if (movie.getPersonalRating() >= rate){
                ratedList.add(movie);
            }
        }

        System.out.println(ratedList);

        movieObservableListByCategory.clear();
        movieObservableListByCategory.addAll(ratedList);
    }

    /**
     * opens the given movie in the OS media player
     * based on the movies saved filePath
     * @param movie
     * @throws Exception
     */
    public void openMovieInPlayer(Movie movie) throws Exception{
        File file = new File(movie.getLocalFilePath());
        Desktop.getDesktop().open(file);

        LocalDateTime localDateTime = LocalDateTime.now();

        movie.setLastPlayDate(localDateTime);
        manager.updateDate(movie);
    }

}
