package MovieCollection.GUI.Controller;

import MovieCollection.GUI.Model.IndexDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class IndexViewController implements Initializable {
    @FXML private ListView listViewSubject;
    @FXML private ListView listViewCategory;
    @FXML private TreeTableColumn treeIMDBRating;
    @FXML private TreeTableColumn TreeTittle;
    @FXML private TreeTableColumn TreeTime;
    @FXML private TreeTableColumn TreeOwnRatting;
    @FXML private Button btnAddMovie;
    @FXML private Button btnAddCategory;
    @FXML private Button btnDeleteCategory;
    @FXML private Button btnDeleteMovie;
    private IndexDataModel indexDataModel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexDataModel = new IndexDataModel();
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
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
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void deleteCategory(ActionEvent actionEvent) {
        try {
            System.out.println("Delete Category");
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void deleteMovie(ActionEvent actionEvent) {
        try {
            System.out.println("Delete movies");
        } catch (Exception e) {
            displayError(e);
        }
    }
}
