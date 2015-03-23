package messagepane;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Tanisha
 */
public class MessageBoxWindow
{

    private int number;

    public int message(String title, String message)
    {
        // Create a new Stage.
        Stage stage = new Stage();
        // Set title.
        stage.setTitle(title);
        // Create a horizontal layout.
        VBox hbox = new VBox();
        hbox.getChildren().addAll(messageBox(message), buttonBox(stage));
        // Create a scene.
        Scene scene = new Scene(hbox);

        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);

        stage.showAndWait();
        return number;
    }

    private HBox buttonBox(Stage stage)
    {
        // Create buttons.
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        // Listeners.
        yesButton.setOnAction(e ->
        {
            number = 0;
            stage.close();
        });
        noButton.setOnAction(e ->
        {
            number = 1;
            stage.close();
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(yesButton, noButton);
        return hbox;
    }

    private HBox messageBox(String message)
    {
        Label messageLabel = new Label(message);
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(messageLabel);
        return hbox;
    }
}
