package sample;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.stage.Stage;

public class PersonEditDialogController {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField streetField;
    @FXML
    private TextField postalCodeField;
    @FXML
    private TextField cityField;
    @FXML
    private TextField birthdayField;


    private Stage dialogStage;
    private Model person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }
    public void setStage(Stage s){
        this.dialogStage = s;
    }
    public boolean isOkClicked(){
        return okClicked;
    }
    public void setPerson(Model m){
        this.person = m;
        firstNameField.setText(m.getFirstName());
        lastNameField.setText(m.getLastName());
        streetField.setText(m.getStreet());
        cityField.setText(m.getCity());
        postalCodeField.setText(Integer.toString(m.getPostalCode()));
        birthdayField.setText(DateUtil.format(m.getBirthday()));

    }
    @FXML
    private void handleOk(){
        if (isInputValid()){
            this.person.setFirstName(firstNameField.getText());
            this.person.setLastName(lastNameField.getText());
            this.person.setCity(cityField.getText());
            this.person.setBirthday(DateUtil.parse(birthdayField.getText()));
            this.person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
            this.person.setStreet(streetField.getText());
            okClicked = true;
            dialogStage.close();
        }
    }
    @FXML
    private void handleCancel(){
        dialogStage.close();
    }
    public boolean isInputValid(){
        String errormsg = "";
        if (firstNameField.getText() == null || firstNameField.getText().length() ==0){
            errormsg += "No First Name entered!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() ==0){
            errormsg += "No Last Name entered!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() ==0){
            errormsg += "No Street entered! \n";
        }
        if (cityField.getText()== null || streetField.getText().length()==0){
            errormsg+="No City entered!";
        }
        if (postalCodeField.getText()==null || postalCodeField.getText().length() == 0){
            errormsg += "No Postal Code entered!\n";
        } else{
            try{
                Integer.parseInt(postalCodeField.getText());
            }
            catch (NumberFormatException e){
                errormsg += "Invalid Postal Code entered! Enter a Number\n";
            }
        }
        if (birthdayField.getText() == null || birthdayField.getText().length() == 0){
            errormsg += "No Birthday Entered! \n";
        } else{
            if (!DateUtil.validDate(birthdayField.getText())) {
                errormsg += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
        if (errormsg.length()==0){
            return true;
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct the following errors:");
            alert.setContentText(errormsg);
            alert.showAndWait();
            return false;
        }
    }
}

