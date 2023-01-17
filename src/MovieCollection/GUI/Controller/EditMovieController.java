package MovieCollection.GUI.Controller;

import MovieCollection.BE.Category;
import MovieCollection.BE.Movie;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditMovieController implements Initializable {
    @FXML private Button btnSubCombobox1;
    @FXML private ComboBox comboBoxMovie;
    @FXML private TextField txtFieldTittle;
    @FXML private TextField txtFieldIMDBLink;
    @FXML private TextField txtInterpersonalScore;
    @FXML private Button btnConfirm;
    @FXML private Button btnCancel;
    @FXML private ComboBox comboBoxCategory;
    @FXML private Button btnAddCombobox;
    @FXML private TextField txtFieldCCat;
    @FXML private Label lblDisplayMissingElement;

    private IndexDataModel indexDataModel;
    private ArrayList<Category> categories;
    private Movie chosenMovie;


    /**
     * gets categories and saves them in a list
     * get current movies that can be changed and saves them in a list
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
        comboBoxMovie.setItems(indexDataModel.getMovieObservableList());
    }

    /**
     * displays the error to the user
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
     * checks if the changes is valid
     * if they are it saves the changes.
     * if they are not, it prints a message to user
     * @param actionEvent
     */
    public void ConfirmEditMovie(ActionEvent actionEvent) {
        try {
            if (chosenMovie == null) return;

            String name = txtFieldTittle.getText();
            float imdb = Float.parseFloat(txtFieldIMDBLink.getText().replace(',','.'));
            float personal = Float.parseFloat(txtInterpersonalScore.getText().replace(',','.'));

            if (!checkData(name,categories,imdb,personal)) return; //if check returns false, code end here
            chosenMovie.setMovieTittle(name);
            chosenMovie.setImdbRating(imdb);
            chosenMovie.setPersonalRating(personal);

            chosenMovie.setCategories(categories);
            chosenMovie.setLastPlayDate(LocalDateTime.now());

            System.out.println(chosenMovie.getLastPlayDate().toString());


            indexDataModel.editMovieConfirm(chosenMovie);

            Stage stage = (Stage) btnCancel.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            displayError(e);
        }
    }

    /**
     * cheks if data is valid
     * @param name
     * @param cat
     * @param imdb
     * @param personal
     * @return
     */
    private boolean checkData(String name, ArrayList<Category> cat, float imdb, float personal){
        if (chosenMovie == null) {
            lblDisplayMissingElement.setText("Chose A Movie");
            return false;
        }
        else if (name == null || name.isEmpty() || name.trim().isEmpty()){
            lblDisplayMissingElement.setText("Missing a name.");
            return false;
        }
        else if (cat.isEmpty()) {
            lblDisplayMissingElement.setText("Missing one or more categories.");
            return false;
        }
        else if (imdb < 0.0 || imdb > 10.00) {
            lblDisplayMissingElement.setText("The IMDB Rating encountered an error");
            return false;
        }
        else if (personal < 0.0 || personal > 10.00) {
            lblDisplayMissingElement.setText("The rating is not between 0 and 10.");
            return false;
        }

        return true;
    }

    /**
     * classes window
     * @param actionEvent
     */
    public void cancelAddMovie(ActionEvent actionEvent) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    /**
     * adds the selected category to movie
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

    /**
     * Removes category from the arrayList.
     * @param actionEvent
     */
    public void subComboBox(ActionEvent actionEvent) {
        try {
            if (comboBoxCategory.getValue() == null) return;

            categories.remove((Category) comboBoxCategory.getValue());
            for (Category category: categories){
                if (category.getCatName().equals(((Category) comboBoxCategory.getValue()).getCatName())){
                    categories.remove(category);
                }
            }
            txtFieldCCat.setText(String.valueOf(categories));
        } catch (Exception e){
            displayError(e);
        }
    }


    /**
     * sets the data from the selected movie, so there can be made changes.
     * @param actionEvent
     * @throws Exception
     */
    public void SetDefaultValues(ActionEvent actionEvent) throws Exception{
        try {
            chosenMovie = (Movie) comboBoxMovie.getValue();
            txtFieldTittle.setText(chosenMovie.getMovieTittle());
            txtFieldIMDBLink.setText(String.valueOf(chosenMovie.getImdbRating()));
            txtInterpersonalScore.setText(String.valueOf(chosenMovie.getPersonalRating()));

            if (chosenMovie.getCategories().isEmpty()) return;
            System.out.println(chosenMovie.getCategories().size());

            categories.clear();

            for (Category category : chosenMovie.getCategories()) {
                categories.add(category);
            }
            txtFieldCCat.setText(String.valueOf(categories));

        } catch (Exception e) {
            displayError(e);
        }
    }
}
