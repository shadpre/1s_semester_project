package MovieCollection.GUI.Controller;

import MovieCollection.BE.Movie;
import MovieCollection.GUI.Model.IndexDataModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PopUpDeleteController implements Initializable {
    public ListView listViewDelete;
    public Button btnConfirm;
    private ObservableList moviesForDeletion;
    private IndexDataModel indexDataModel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        indexDataModel = new IndexDataModel();
        moviesForDeletion = FXCollections.observableArrayList();
        if (isThereData()) {

        }
        else {

        }

        listViewDelete.setItems(moviesForDeletion);
    }

    private boolean isThereData(){
        return moviesForDeletion != null && moviesForDeletion.isEmpty();
    }
    public void closeWindow(ActionEvent actionEvent) {
        Stage stage = (Stage) btnConfirm.getScene().getWindow();
        stage.close();
    }
}
