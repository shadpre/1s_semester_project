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
    @FXML private Button btnFilter;
    @FXML private TextField txtFieldSearcher;
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

        start();
        initCategories();
        initTreeMovies();

        txtFieldSearcher.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                indexDataModel.searchForMovie(newValue);
            } catch (Exception e){
                displayError(e);
            }
        });
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
    private void initCategories(){
        listViewCategory.setItems(indexDataModel.getCategoryObservableList());

        listViewCategory.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var categoryId = listViewCategory.getSelectionModel().getSelectedIndex();

                tableViewMovies.setItems(indexDataModel.getMoviesFromCategory(categoryId));
            }
        });
    }
    private void initTreeMovies(){
        tableViewMovies.widthProperty().addListener((source, oldWidth, newWidth) -> {
            TableHeaderRow header = (TableHeaderRow) tableViewMovies.lookup("TableHeaderRow");
            header.reorderingProperty().addListener((observable, oldValue, newValue) -> header.setReordering(false));
        });

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

            listViewCategory.refresh();
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

            tableViewMovies.refresh();

        } catch (Exception e) {
            displayError(e);
        }
    }

    public void controlFilter(ActionEvent actionEvent) {
        var categoryId = listViewCategory.getSelectionModel().getSelectedIndex();

        TextInputDialog dialog = new TextInputDialog();
        dialog.setGraphic(null);
        dialog.setHeaderText(null);
        dialog.setTitle("Add Minimum IMDB rating");
        dialog.setContentText("rating:");
        var result = dialog.showAndWait();
        result.ifPresent(rating -> {
            try {
                indexDataModel.addMinimumImdbRating(rating, categoryId);
            } catch (Exception e) {
                displayError(e);
            }
        });
    }
}
