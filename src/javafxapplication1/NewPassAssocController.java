/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import com.donation.Service.ServiceAssociation;
import com.donation.Service.ServiceUsers;
import com.donation.Utils.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hatem
 */
public class NewPassAssocController implements Initializable {

    @FXML
    private PasswordField newPass;
    @FXML
    private PasswordField confirm;
    
    ServiceUsers serUsers = new ServiceUsers();
    ServiceAssociation SA = new ServiceAssociation();
    private final Connection con;
    private Statement ste;
    
    public NewPassAssocController() {
        con = DataBase.getInstance().getConnection();

        // initialize the Pattern object
        //pattern = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void passChanged(ActionEvent event) throws SQLException, IOException {
        String pass = newPass.getText();
        String conf = confirm.getText();
        /*
            *
            *test on password length
            *
             */
            if (pass.length() < 5) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Password too short, minimum length 5 characters");
                alert.showAndWait();
                newPass.clear();
                confirm.clear();
                return;
            }
            /*
        *
        *testing the match between the two passwords
        *
             */
            if (!conf.equals(pass)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Failure");
                alert.setHeaderText(null);
                alert.setContentText("Passwords don't match");
                alert.showAndWait();
                newPass.clear();
                confirm.clear();
                return;
            }else{
            con.createStatement().executeUpdate("UPDATE `donation`.`association2` SET `Password_Association` = '" + newPass.getText() + "' and redeem = NULL WHERE `Id_Association` = " + 
                    ServiceUsers.currentUser.getId_user() + ";");
            
        
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("Home.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tableViewScene);
            window.show();
        }
    }

    @FXML
    private void logOut(ActionEvent event) throws SQLException, IOException {
        SA.SignOut();
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("AssociationSignIn.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }
    
}
