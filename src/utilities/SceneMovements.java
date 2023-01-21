package utilities;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

/**
 * This class defines movement between scenes that can be reused.
 */
public class SceneMovements {

    /**
     * Opens a scene with the passed parameters.
     * @param actionEvent Passed button press.
     * @param location Passed fxml location.
     * @param width Passed scene width.
     * @param height Passed scene height.
     * @param title Passed scene title.
     */
    public static void goToPage(ActionEvent actionEvent, String location, int width, int height, String title) throws IOException {

            Parent root = FXMLLoader.load(Objects.requireNonNull(SceneMovements.class.getResource("/view/" + location + ".fxml")));
            Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, width, height);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();

    }
}
