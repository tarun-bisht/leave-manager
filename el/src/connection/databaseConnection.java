/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;
import dialogs.special;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tarun Bisht
 */
public class databaseConnection 
{
    private final String url;
    private final String username;
    private final String password;
    private Connection conn;
    public databaseConnection(String url,String username,String password)
    {
        this.url=url;
        this.username=username;
        this.password=password;
    }
    private void SetupConnection()
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            conn=DriverManager.getConnection(url,username,password);
        }
        catch(ClassNotFoundException | SQLException e)
        {
            conn=null;
            special.ErrorAlert("Error", "Failed to Connect","Check Database Credentials or Check for Host and Port in Settings");
        }
    }
    public Connection getConnection()
    {
        SetupConnection();
        return conn;
    }
}
