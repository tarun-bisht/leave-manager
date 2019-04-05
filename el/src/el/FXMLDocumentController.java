/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;

import data_classes.read_write;
import dialogs.special;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Tarun Bisht
 */
public class FXMLDocumentController implements Initializable 
{

    @FXML
    private void goToDashboard(ActionEvent event) throws IOException
    {
        special s = new special();
        s.ChangeScene(event, "/fxml_docs/dbLogin.fxml",true);
    }
    @FXML
    private void Settings(ActionEvent event) throws IOException
    {
        special s = new special();
        s.NewWindow(event, "/fxml_docs/settings.fxml");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        read_write rw=new read_write();
        String[] key={"host","port"};
        String[] content =rw.Read(key);
        String host=content[0];
        String port=content[1];
        if(!host.isEmpty())
            connection.connection.SetHost(host);
        if(!port.isEmpty())
            connection.connection.SetPort(port);
    }
}
