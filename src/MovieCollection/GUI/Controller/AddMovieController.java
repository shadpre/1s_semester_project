package MovieCollection.GUI.Controller;

import MovieCollection.BE.Movie;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class AddMovieController extends Application {
    public TextField txtFieldTittle;
    public TextField txtFieldIMDBLink;
    public TextField txtInterpersonalScore;
    public Button btnSelectFile;
    public TextField txtFieldPath;
    public Button btnConfirm;
    public Button btnCancel;
    public ComboBox comboBoxCategory;
    public Button btnAddCombobox;
    public TextField txtFieldCCat;

    private ArrayList <String> categories;

    @Override
    public void start(Stage primaryStage) throws Exception {
        categories = new ArrayList<>();
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
    }


    public void selectFile(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select song");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Video File", "*.mp4"));
            Stage stage = (Stage) btnSelectFile.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                txtFieldPath.setText(String.valueOf(selectedFile));
                txtFieldTittle.setText(selectedFile.getName());
            }
        } catch (Exception e) {
            displayError(e);
        }
    }

    public void ConfirmAddMovie(ActionEvent actionEvent) {
    }

    public void cancelAddSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void addComboBox(ActionEvent actionEvent) {
        try {
            if (comboBoxCategory.getValue() == null || comboBoxCategory.getValue().equals("")) return;

            String value = (String) comboBoxCategory.getValue();
            if (categories.contains(value)) return;

            categories.add(value);
            txtFieldCCat.setText(String.valueOf(categories));
        } catch (Exception e){
            displayError(e);
        }
    }

}
