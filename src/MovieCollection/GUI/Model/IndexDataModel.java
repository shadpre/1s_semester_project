package MovieCollection.GUI.Model;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BE.Subject;
import MovieCollection.BLL.Util.TupleCategory;
import MovieCollection.BLL.Util.TupleMovie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexDataModel {
    private ObservableList<Movie> movieObservableList;
    private ObservableList<Category> categoryObservableList;
    private ObservableList<Subject> subjectObservableList;
    private TupleMovie tbMovie;
    private TupleCategory tbCat;

    public IndexDataModel() {
        movieObservableList = FXCollections.observableArrayList();
        categoryObservableList = FXCollections.observableArrayList();
        subjectObservableList = FXCollections.observableArrayList();

        tbMovie = new TupleMovie();
        tbCat = new TupleCategory();
    }

    public ObservableList getMovieObservableList() {
        return movieObservableList;
    }
    public ObservableList getCategoryObservableList() {
        return categoryObservableList;
    }

    public ObservableList getSubjectObservableList() {
        return subjectObservableList;
    }

    public void openAddMovieWindow() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/addMovie.fxml"));
        Parent root1 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.showAndWait();

        Movie tbMovieWindowResult = tbMovie.getTbMovie();
        if (tbMovieWindowResult == null || movieObservableList.contains(tbMovieWindowResult)) return;

        movieObservableList.add(tbMovieWindowResult);

        //TODO INSERT DATA INTO DAO

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

        categoryObservableList.add(tbCategoryEWindowResult);

        //TODO INSERT DATA INTO DAO

        tbCat.setCategory(null);
    }

    public void startPopUp() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MovieCollection/GUI/View/PopUpDelete.fxml"));
        Parent root3 = (Parent) fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root3));
        stage.showAndWait();
    }

    public void deleteCategory(Category selectedCategory) throws Exception {
        if (selectedCategory == null || !categoryObservableList.contains(selectedCategory))  throw new Exception("Item Does Not Exist");

        //TODO DELETE CATEGORY

    }

    public void deleteMovie(Movie selectedMovie) throws Exception {
        if (selectedMovie == null || !movieObservableList.contains(selectedMovie))  throw new Exception("Item Does Not Exist");

        //TODO DELETE MOVIE

    }
}
