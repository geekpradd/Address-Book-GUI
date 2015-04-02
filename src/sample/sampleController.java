package sample;
import javafx.fxml.FXML;

import javafx.scene.control.*;


public class sampleController {
    //@fxml is like a decorator that provides access to Scene Builder, the private variables
    @FXML
    private TableView<Model> personTable;
    //Will be a string based from a Model (each Column inherits sort of from our Model
    @FXML
    private TableColumn<Model, String> firstNameColumn;
    @FXML
    private TableColumn<Model, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    public Main app; //Main.java file !

    public sampleController(){
        //Do nothing
    }
    @FXML
    public void initialize(){
        /*
        This is called immediately after the constructor.. We will set up the Controller and the Tablee
         */
        //First Name column is a Model.. The lambda function takes itself and sets the value by the
        //firstname property in our Model declarations
        firstNameColumn.setCellValueFactory(cellData-> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cell->cell.getValue().lastNameProperty());
        personTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setPersonData(newValue));
    }
    public void setPersonData(Model p){
        if (p!=null){
            firstNameLabel.setText(p.getFirstName());
            lastNameLabel.setText(p.getLastName());
            streetLabel.setText(p.getStreet());
            postalCodeLabel.setText(Integer.toString(p.getPostalCode()));
            cityLabel.setText(p.getCity());
            birthdayLabel.setText(DateUtil.format(p.getBirthday()));
        }
        else{
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }
    @FXML
    private void handleNew(){
        Model x = new Model();
        boolean status = app.editDialog(x);
        if (status) {
            app.getPersons().add(x);
        }
    }
    @FXML
    private void handleEdit(){
        Model index = personTable.getSelectionModel().getSelectedItem();
        if (index !=null){
            boolean status = app.editDialog(index);
            if (status){
                setPersonData(index);
            }
        }
    }
    @FXML
    private void deleteHandler(){
        int index = personTable.getSelectionModel().getSelectedIndex();
        System.out.println("Deleting..");
        if (index>=0){
            personTable.getItems().remove(index);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(app.getPrimaryStage());
            alert.setTitle("No Entry Selected");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();

        }
    }
    public void setApp(Main a){
        this.app = a;
        personTable.setItems(this.app.getPersons());

    }


}
