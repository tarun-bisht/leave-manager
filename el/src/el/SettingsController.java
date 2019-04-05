/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;

import data_classes.read_write;
import dialogs.special;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Tarun Bisht
 */
public class SettingsController implements Initializable 
{
    @FXML
    TextField host ,port;
    /**
     * Initializes the controller class.
     */
    @FXML
    private void SetHost()
    {
        String hostname=host.getText();
        read_write rw=new read_write();
        String[] key={"host"};
        if(hostname.isEmpty())
        {
            special.ErrorAlert("Error", "Empty Field", "Please Fill all compulsory fields");
            return;
        }
        String[] content={hostname};
        connection.connection.SetHost(hostname);
        rw.Write(key, content);
    }
    @FXML
    private void SetPort()
    {
        String portnumber=port.getText();
        read_write rw=new read_write();
        String[] key={"port"};
        if(portnumber.isEmpty())
        {
            special.ErrorAlert("Error", "Empty Field", "Please Fill all compulsory fields");
            return;
        }
        String[] content={portnumber};
        connection.connection.SetPort(portnumber);
        rw.Write(key, content);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        host.setText(connection.connection.GetHost());
        port.setText(connection.connection.GetPort());
    }    
    
}
