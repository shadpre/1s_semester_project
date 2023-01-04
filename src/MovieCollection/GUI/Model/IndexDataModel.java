package MovieCollection.GUI.Model;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BLL.Util.TupleCategory;
import MovieCollection.BLL.Util.TupleMovie;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class IndexDataModel {
    private ObservableList movieObservableList;
    private ObservableList categoryObservableList;
    private ObservableList subjectObservableList;
    private TupleMovie tbMovie;
    private TupleCategory tbCat;

    public IndexDataModel() {
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

    public void deleteCategory(Category selectedCategory) throws Exception {
        if (selectedCategory == null || !categoryObservableList.contains(selectedCategory))  throw new Exception("Item Does Not Exist");

        //TODO DELETE CATEGORY

    }

    public void deleteMovie(Movie selectedMovie) throws Exception {
        if (selectedMovie == null || !movieObservableList.contains(selectedMovie))  throw new Exception("Item Does Not Exist");

        //TODO DELETE MOVIE

    }
}
