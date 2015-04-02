package sample;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.FileChooser;

/**
 * Created by Pradipta on 02-04-2015.
 */
public class RootController {
    private Main app;
    public void setApp(Main instance){
        this.app = instance;
    }
    @FXML
    private void handleNew(){
        app.getPersons().clear();
        app.setSavedPath(null);
    }
    @FXML
    private void handleOpen(){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(filter);

        File f = fileChooser.showOpenDialog(app.getPrimaryStage());

        if (f!=null){
            app.loadDataFromFile(f);
        }
    }
    @FXML
    private void handleSave(){
        File file = app.getSavedFile();
        if (file !=null){
            app.saveDataToFile(file);
        }
        else{
            handleSaveAs();
        }
    }
    @FXML
    private void handleChart(){
        app.birthdayGraph();
    }
    @FXML
    private void handleSaveAs(){
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("XML Files (*.xml)", "*.xml");
        chooser.getExtensionFilters().add(ext);
        File f = chooser.showSaveDialog(app.getPrimaryStage());
        if (f!=null){
            if(!f.getPath().endsWith(".xml")){
                f = new File(f.getPath()+".xml");
            }
            app.saveDataToFile(f);
        }
    }
    @FXML
    private void handleAbout(){
        app.AboutDialog();
    }
    @FXML
    private void handleExit(){
        System.exit(0);
    }
}
