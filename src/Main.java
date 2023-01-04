import MovieCollection.BLL.DatabaseConnector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {
    public static void main(String[] args) throws IOException {
        //System.out.println("Hello world!");
        var url =  Main.class.getResource("config.properties");

        Properties props = new Properties();
        if(url != null)
            try(InputStream input = url.openStream()) {
                props.load(input);
            }

        var dbIp = props.getProperty("DB_IP");
        var dbPort = Integer.parseInt(props.getProperty("DB_PORT"));
        var dbName= props.getProperty("DB_NAME");
        var dbUsername = props.getProperty("DB_USERNAME");
        var dbPassword = props.getProperty("DB_PASSWORD");

        DatabaseConnector.init(dbIp, dbPort, dbName, dbUsername, dbPassword);

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/MovieCollection/GUI/View/indexView.fxml"));
        primaryStage.setTitle("Movie Collection");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}