package MovieCollection.GUI.Controller;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexViewController implements Initializable {
    @FXML private ListView listViewSubject;
    @FXML private ListView listViewCategory;
    @FXML private TableView<Movie> tableViewMovies;
    @FXML private TableColumn tableIMDBRating;
    @FXML private TableColumn tableTittle;
    @FXML private TableColumn tableTime;
    @FXML private TableColumn tableOwnRatting;
    @FXML private Button btnAddMovie;
    @FXML private Button btnAddCategory;
    @FXML private Button btnDeleteCategory;
    @FXML private Button btnDeleteMovie;
    private IndexDataModel indexDataModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            indexDataModel = new IndexDataModel();
        } catch (Exception e) {
            displayError(e);
        }
        //tart();
        listViewSubject.setItems(indexDataModel.getSubjectObservableList());
        listViewCategory.setItems(indexDataModel.getCategoryObservableList());
        initTreeMovies();
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
    }

    private void start() {
        try {
            indexDataModel.startPopUp();
        } catch (Exception e) {
            displayError(e);
        }
    }

    private void initTreeMovies(){
        tableViewMovies.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tableViewMovies.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });

        tableViewMovies.setItems(indexDataModel.getMovieObservableList());

        tableIMDBRating.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(indexDataModel.getIMDBRatingForMovie(param.getValue())
                ));
        tableTittle.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(indexDataModel.getTittleForMovie(param.getValue())
                ));
        tableTime.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(indexDataModel.getLastViewed(param.getValue())
                ));
        tableOwnRatting.setCellValueFactory((Callback<TableColumn.CellDataFeatures<Movie, String>, ObservableValue<String>>)
                param -> new SimpleStringProperty(indexDataModel.getPersonalRating(param.getValue())
                ));
    }

    public void addMovie(ActionEvent actionEvent) {
        try {
            System.out.println("Add Movie");

            indexDataModel.openAddMovieWindow();

        } catch (Exception e) {
            displayError(e);
        }
    }

    public void addCategory(ActionEvent actionEvent) {
        try {
            System.out.println("Add Category");

            indexDataModel.openAddCategoryWindow();

            listViewCategory.refresh();
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void deleteCategory(ActionEvent actionEvent) {
        try {
            System.out.println("Delete Category");

            Dialog<Category> deleteSongDialog = new ChoiceDialog<>(null, indexDataModel.getCategoryObservableList());
            deleteSongDialog.setGraphic(null);
            deleteSongDialog.setHeaderText(null);
            deleteSongDialog.setTitle("Delete a Cat");
            deleteSongDialog.setContentText("Delete Category: ");
            var result = deleteSongDialog.showAndWait();
            result.ifPresent(selectedCategory -> {
                try {
                    indexDataModel.deleteCategory(selectedCategory);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            displayError(e);
        }
    }

    public void deleteMovie(ActionEvent actionEvent) {
        try {
            System.out.println("Delete movies");

            Dialog<Movie> deleteSongDialog = new ChoiceDialog<>(null, indexDataModel.getMovieObservableList());
            deleteSongDialog.setGraphic(null);
            deleteSongDialog.setHeaderText(null);
            deleteSongDialog.setTitle("Delete a Movie");
            deleteSongDialog.setContentText("Delete Movie: ");
            var result = deleteSongDialog.showAndWait();
            result.ifPresent(selectedMovie -> {
                try {
                    indexDataModel.deleteMovie(selectedMovie);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (Exception e) {
            displayError(e);
        }
    }
}
