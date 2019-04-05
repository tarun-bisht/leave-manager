/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;

import dialogs.special;
import java.io.IOException;
import query.Query;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Tarun Bisht
 */
public class LoginFXMLController implements Initializable {
    @FXML
    TextField username,password;
    
    @FXML
    private void Login(ActionEvent event) throws Exception
    {
        String user,pass;
        user=username.getText();
        pass=password.getText();
        special s = new special();
        if(!Query.LoginMechanism(user, pass))
        {
            username.clear();
            password.clear();
        }
        else
        {
            s.ChangeScene(event,"/fxml_docs/FXMLDashboard.fxml",false);
        }
    }
    @FXML
    private void Close()
    {
        Platform.exit();
        System.exit(0);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
