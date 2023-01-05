package MovieCollection.GUI.Controller;

import MovieCollection.BE.Category;
import MovieCollection.BLL.Util.TupleCategory;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.application.Application;
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
    private TupleCategory tbCategory;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            indexDataModel = new IndexDataModel();
        } catch (Exception e) {
            displayError(e);
        }
        tbCategory = new TupleCategory();
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
    }

    public void ConfirmAddMovie(ActionEvent actionEvent) {
        String catName = txtFieldCategoryName.getText();
        Category category = new Category(catName, -1);

        tbCategory.setCategory(category);

        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void cancelAddSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
