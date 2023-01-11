package MovieCollection.GUI.Controller;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.BLL.Util.TupleCategory;
import MovieCollection.BLL.Util.TupleMovie;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddMovieController implements Initializable {
    @FXML  private TextField txtFieldTittle;
    @FXML  private TextField txtFieldIMDBLink;
    @FXML  private TextField txtInterpersonalScore;
    @FXML  private Button btnSelectFile;
    @FXML  private TextField txtFieldPath;
    @FXML  private Button btnConfirm;
    @FXML  private Button btnCancel;
    @FXML  private ComboBox comboBoxCategory;
    @FXML  private Button btnAddCombobox;
    @FXML  private TextField txtFieldCCat;
    @FXML  private Label lblDisplayMissingElement;

    private IndexDataModel indexDataModel;
    private ArrayList<Category> categories;
    private TupleMovie tbMovie;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        categories = new ArrayList<>();
        tbMovie = new TupleMovie();

        try {
            indexDataModel = new IndexDataModel();
        } catch (Exception e) {
            displayError(e);
        }
        comboBoxCategory.setItems(indexDataModel.getCategoryObservableList());
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
        try {
            String name = txtFieldTittle.getText();
            float imdb = Float.parseFloat(txtFieldIMDBLink.getText().replace(',','.'));
            float personal = Float.parseFloat(txtInterpersonalScore.getText().replace(',','.'));
            String path = txtFieldPath.getText();

            if (!checkData(name,categories,imdb,personal,path)) return; //if check returns false, code end here
            Movie movie = new Movie(name, imdb, personal, path, -1);

            movie.setCategories(categories);
            movie.setLastPlayDate(LocalDateTime.now());

            System.out.println(movie.getLastPlayDate().toString());

            tbMovie.setTbMovie(movie);

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            displayError(e);
        }
    }

    private boolean checkData(String name, ArrayList<Category> cat, float imdb, float personal, String path){
        if (name == null || name.isEmpty() || name.trim().isEmpty()){
            lblDisplayMissingElement.setText("Missing a name.");
            return false;
        }
        if (cat.isEmpty()) {
            lblDisplayMissingElement.setText("Missing one or more categories.");
            return false;
        }
        if (imdb < 0.0 || imdb > 10.00) {
            lblDisplayMissingElement.setText("The IMDB Rating encountered an error");
            return false;
        }
        if (personal < 0.0 || personal > 10.00) {
            lblDisplayMissingElement.setText("The rating is not between 0 and 10.");
            return false;
        }
        if (path == null || path.isEmpty()) {
            lblDisplayMissingElement.setText("You need to select a file.");
            return false;
        }

        return true;
    }

    public void cancelAddSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    public void addComboBox(ActionEvent actionEvent) {
        try {
            if (comboBoxCategory.getValue() == null) return;

            if (categories.contains(comboBoxCategory.getValue())) return;

            categories.add((Category) comboBoxCategory.getValue());
            txtFieldCCat.setText(String.valueOf(categories));
        } catch (Exception e){
            displayError(e);
        }
    }

}


