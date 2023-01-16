package MovieCollection.GUI.Controller;

import MovieCollection.BE.Movie;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PopUpDeleteController implements Initializable {
    @FXML private ListView listViewDelete;
    @FXML private Button btnConfirm;
    private ObservableList moviesForDeletion;
    private IndexDataModel indexDataModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        moviesForDeletion = FXCollections.observableArrayList();

        try {
            indexDataModel = new IndexDataModel();
            moviesForDeletion.addAll(indexDataModel.getAllOldMovies());
            System.out.println(indexDataModel.getAllOldMovies());
        } catch (Exception e) {
            displayError(e);
        }
        if (isThereData()) {

        }
        else {

        }

        listViewDelete.setItems(moviesForDeletion);
    }

    private void displayError(Throwable t)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("!!ERROR!!");
        alert.setHeaderText("Something went wrong, \n ERROR:      " + t.getMessage());
        alert.showAndWait();
    }

    private boolean isThereData(){
        return moviesForDeletion != null && moviesForDeletion.isEmpty();
    }
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        stage.close();
    }
}
