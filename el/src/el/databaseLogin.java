/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package el;

import connection.databaseConnection;
import dialogs.special;
import java.io.IOException;
import java.sql.Connection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 *
 * @author Tarun Bisht
 */
public class databaseLogin 
{
    @FXML
    TextField userid;
    @FXML
    PasswordField password;
    @FXML
    private void databaseLogin(ActionEvent event) throws IOException
    {
        String user=userid.getText();
        String pwd=password.getText();
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),user,pwd);
        Connection conn = dbconn.getConnection();
        if(conn==null)
        {
            userid.clear();
            password.clear();
            return;
        }
        special s = new special();
        connection.connection.SetDBUsername(user);
        connection.connection.SetDBPassword(pwd);
        s.ChangeScene(event, "/fxml_docs/LoginFXML.fxml",true);
    }
    @FXML
    private void Close()
    {
        Platform.exit();
        System.exit(0);
    }
}
