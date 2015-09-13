package ua.edu.ssu.sotnik;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * This class consists main entry point of application
 * using JavaFX to build graphical interface.
 *
 * @author Yaroslav Sotnyk
 * @see javafx.application.Application
 * @version  0.2
 *
 */
public class App extends Application {

    public static void main( String[] args ) throws Exception {

        launch(args);

    }

    /**
     * Searching for main fxml file with
     * marking of interface
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = "/fxml/MainWindow.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setResizable(false);
        stage.setTitle("Product parser v 0.1");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
