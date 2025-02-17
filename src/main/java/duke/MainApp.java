package duke;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Represents the main entry point for the Duke chatbot application.
 */
public class MainApp extends Application {

    private Duke duke = new Duke();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            assert ap != null : "Anchor pane failed to load";
            Scene scene = new Scene(ap);

            stage.setScene(scene);
            stage.setX(1000);
            stage.setY(50);
            stage.setResizable(false);
            stage.setTitle("Duke");
            stage.getIcons().add(new Image(MainApp.class.getResourceAsStream("/images/DukeIcon.png")));


            fxmlLoader.<MainWindow>getController().setDuke(duke);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
