package MovieCollection.GUI.Controller;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.GUI.Model.IndexDataModel;
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

    /**
     * initialize window and save categories in a arraylist
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
    public void initialize(URL location, ResourceBundle resources){
        categories = new ArrayList<>();

        try {
            indexDataModel = new IndexDataModel();
        } catch (Exception e) {
            displayError(e);
        }
        comboBoxCategory.setItems(indexDataModel.getCategoryObservableList());
    }

    /**
     * Displays error to User
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
     * opens a filechosser window and saves selected file, name, and path. and checks if selected file type is valid.
     * @param actionEvent
     */
    public void selectFile(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select song");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Video File", "*.mp4", "*.mpeg4"));
            Stage stage = (Stage) btnSelectFile.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null && selectedFile.getName().endsWith(".mp4") || selectedFile != null && selectedFile.getName().endsWith(".mpeg4")) {
                txtFieldPath.setText(String.valueOf(selectedFile));
                txtFieldTittle.setText(selectedFile.getName());
            } else {
                lblDisplayMissingElement.setText("select a movie type file");
            }
        } catch (Exception e) {
            displayError(e);
        }
    }

    /**
     * Saves movie if all data is valid.
     * @param actionEvent
     */
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

            indexDataModel.addMovieConfirm(movie);

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            displayError(e);
        }
    }

    /**
     * ches if given data is valid to be saved to a movie
     * @param name
     * @param cat
     * @param imdb
     * @param personal
     * @param path
     * @return
     */
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

    /**
     * classes window
     * @param actionEvent
     */
    public void cancelAddSong(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * adds categories to the movie.
     * @param actionEvent
     */
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


