package MovieCollection.GUI.Controller;

import MovieCollection.BE.Category;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddCategoryController implements Initializable {

    @FXML private TextField txtFieldCategoryName;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    private IndexDataModel indexDataModel;

    /**
     * initialise controller
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
    }

    /**
     * Displays error  to the User
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
     * saves the data to a new categories and classes the window
     * @param actionEvent
     */
    public void ConfirmAddMovie(ActionEvent actionEvent) {
        String catName = txtFieldCategoryName.getText();
        Category category = new Category(catName, -1);

        try {
            indexDataModel.addCategoryConfirm(category);
        } catch (Exception e) {
            displayError(e);
        }

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * classes the window
     * @param actionEvent
     */
    public void cancelAddSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
