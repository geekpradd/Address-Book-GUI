package sample;

import javafx.fxml.FXML;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;


/**
 * Created by Pradipta on 02-04-2015.
 */
public class AboutDialogController {
    @FXML
    private TextFlow txt;
    private Main app;
    private Stage stage;

    public void setApp(Main a) {
        this.app = a;
    }

    public void setStage(Stage s) {
        this.stage = s;
    }

    @FXML
    private void initialize() {
        Text t = new Text("Created By Pradipta using Java and the JavaFX Library.\n\nMIT Licensed. Copyright 2015.");
        t.setFill(Color.WHITE);
        t.setFont(Font.font("Segoe UI", FontPosture.REGULAR, 15));
        txt.getChildren().add(t);
    }

    @FXML
    private void handleClose() {
        stage.close();
    }
}
