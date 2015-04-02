package sample;

import java.io.IOException;

import java.io.File;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javax.xml.bind.*;

public class Main extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;


    //Create the list that can be tracked
    private ObservableList<Model> persons = FXCollections.observableArrayList();

    public Main() {
        // Add some sample data

    }
    public ObservableList<Model> getPersons(){
        return persons;
    }
    public File getSavedFile(){
        Preferences pref = Preferences.userNodeForPackage(Main.class);
        String path = pref.get("filepath", null);
        if (path!=null){
            return new File(path);
        }
        else{
            return null;
        }
    }
    public void setSavedPath(File f){
        Preferences p = Preferences.userNodeForPackage(Main.class);
        if (f!=null){
            p.put("filepath",f.getPath());
            primaryStage.setTitle("AddressApp - " + f.getName());
        }
        else{
            p.remove("filepath");
            primaryStage.setTitle("AddressApp");
        }
    }
    public void start(Stage primaryStage) {
        System.out.println("javafx.runtime.version: " + System.getProperties().get("javafx.runtime.version"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        initRootLayout();

        showPersonOverview();
    }
    public boolean editDialog(Model p){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialog = new Stage();
            dialog.setTitle("Edit/New Person");
            dialog.initOwner(primaryStage);
            dialog.initModality(Modality.WINDOW_MODAL);
            Scene sc = new Scene(page);
            dialog.setScene(sc);

            PersonEditDialogController controller = loader.getController();
            controller.setPerson(p);
            controller.setStage(dialog);

            dialog.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean AboutDialog(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("AboutDialog.fxml"));
        try {
            AnchorPane pane = (AnchorPane) loader.load();

            Stage stage = new Stage();
            stage.setTitle("About Address App");
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            Scene sc = new Scene(pane);
            stage.setScene(sc);

            AboutDialogController controller = loader.getController();
            controller.setApp(this);
            controller.setStage(stage);

            stage.showAndWait();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }
    public void birthdayGraph(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("Chart.fxml"));

        try {
            AnchorPane pane = (AnchorPane) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Birthday Graph");
            stage.initOwner(primaryStage);
            stage.initModality(Modality.WINDOW_MODAL);
            Scene sc = new Scene(pane);
            stage.setScene(sc);

            ChartController controller = loader.getController();
            controller.setChartData(persons);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("Root.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            RootController controller = loader.getController();
            controller.setApp(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the Model overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load Model overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("sample.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set Model overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            //Controller
            sampleController c = loader.getController(); //We get the controller as defined in the view file
            c.setApp(this);
            loadDataFromFile(getSavedFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDataFromFile(File file){
        try {
            JAXBContext context = JAXBContext.newInstance(ModelWrapper.class);
            Unmarshaller um = context.createUnmarshaller();

            ModelWrapper wrap = (ModelWrapper) um.unmarshal(file);

            persons.clear();
            persons.addAll(wrap.getPersons());

            setSavedPath(file);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Error Loading File");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from " + file.getPath());
            alert.showAndWait();
        }
    }
    public void saveDataToFile(File file){
        try{
            JAXBContext context = JAXBContext.newInstance(ModelWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(m.JAXB_FORMATTED_OUTPUT,true);
            ModelWrapper w = new ModelWrapper();
            w.setPersons(persons);
            m.marshal(w, file);
            setSavedPath(file);
        }
        catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(primaryStage);
            alert.setTitle("Error Saving File");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data from " + file.getPath());
            alert.showAndWait();
        }
    }
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}