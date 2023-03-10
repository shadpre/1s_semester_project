package MovieCollection.GUI.Controller;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexViewController implements Initializable {
    @FXML private ComboBox comboBoxFilter;
    @FXML private Button btnFilterPersonal;
    @FXML private Button btnFilter;
    @FXML private Button btnEditMovie;

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
    private ObservableList<String> filterList;

    /**
     * Initialise the program with the data model and lunches the startup program, and listens to the filter.
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            indexDataModel = new IndexDataModel();
        } catch (Exception e) {
            displayError(e);
        }

        filterList = FXCollections.observableArrayList();

        start();
        initCategories();
        initTreeMovies();
        initFilterList();

        txtFieldSearcher.textProperty().addListener((observableValue, oldValue, newValue) -> {
            if (comboBoxFilter.getValue() == "Search") {
                try {
                    indexDataModel.searchForMovie(newValue);
                } catch (Exception e) {
                    displayError(e);
                }
            } else if (comboBoxFilter.getValue() == "IMDB min") {
                try {
                    var category = (Category) listViewCategory.getSelectionModel().getSelectedItem();
                    indexDataModel.filterImdb(newValue, category.getID());
                } catch (Exception e) {
                    displayError(e);
                }
            } else if (comboBoxFilter.getValue() == "Personal min") {
                try {
                    var category = (Category) listViewCategory.getSelectionModel().getSelectedItem();
                    indexDataModel.filterPersonal(newValue,category.getID());
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }

    /**
     * display the error to the user.
     * @param t
     */
    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
    }

    /**
     * sends mesage to cthe model to launch the start window
     */
    private void start() {
        try {
            indexDataModel.startPopUp();
        } catch (Exception e) {
            displayError(e);
        }
    }

    /**
     * set filter to combobox
     */
    private void initFilterList() {
        filterList.add("Search");
        filterList.add("IMDB min");
        filterList.add("Personal min");
        comboBoxFilter.setItems(filterList);

        //TODO
    }

    /**sets categories to the listview
     * and opens movie dependent on selected category
     */
    private void initCategories(){
        listViewCategory.setItems(indexDataModel.getCategoryObservableList());

        listViewCategory.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var category = (Category) listViewCategory.getSelectionModel().getSelectedItem();

                try {
                    tableViewMovies.setItems(indexDataModel.getMoviesFromCategory(category.getID()));
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }

    /**
     * display the movies in columns dependent on data
     * and tels data model to play a selected movie if double-clicked
     */
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

        tableViewMovies.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                var movie = tableViewMovies.getSelectionModel().getSelectedItem();

                try {
                    indexDataModel.openMovieInPlayer(movie);
                } catch (Exception e) {
                    displayError(e);
                }
            }
        });
    }

    /**
     * tells the model that we want to add a movie
     * @param actionEvent
     */
    public void addMovie(ActionEvent actionEvent) {
        try {
            System.out.println("Add Movie");

            indexDataModel.openAddMovieWindow();

        } catch (Exception e) {
            displayError(e);
        }
    }

    /**
     * tells the model that we want to add a category(cat)
     * @param actionEvent
     */
    public void addCategory(ActionEvent actionEvent) {
        try {
            System.out.println("Add Category");

            indexDataModel.openAddCategoryWindow();

            //listViewCategory.refresh();
        } catch (Exception e) {
            displayError(e);
        }
    }

    /**
     * Tells the model that we want to delete a category
     * @param actionEvent
     */
    public void deleteCategory(ActionEvent actionEvent) {
        try {
            System.out.println("Delete Category");

            Dialog<Category> deleteCategory = new ChoiceDialog<>(null, indexDataModel.getCategoryObservableList());
            deleteCategory.setGraphic(null);
            deleteCategory.setHeaderText(null);
            deleteCategory.setTitle("Delete a Cat");
            deleteCategory.setContentText("Delete Category: ");
            var result = deleteCategory.showAndWait();
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

    /**
     * Tells the model that we want to delete a movie
     * @param actionEvent
     */
    public void deleteMovie(ActionEvent actionEvent) {
        try {
            System.out.println("Delete movies");

            Dialog<Movie> deleteMovie = new ChoiceDialog<>(null, indexDataModel.getMovieObservableList());
            deleteMovie.setGraphic(null);
            deleteMovie.setHeaderText(null);
            deleteMovie.setTitle("Delete a Movie");
            deleteMovie.setContentText("Delete Movie: ");
            var result = deleteMovie.showAndWait();
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

    /**
     * Tells the model that we want to edit a movie
     * model then open movie window
     * @param actionEvent
     */
    public void editMovie(ActionEvent actionEvent) {
        try {
            System.out.println("Edit movies");

            indexDataModel.editMovie();

            tableViewMovies.refresh();

        } catch (Exception e) {
            displayError(e);
        }
    }
}
