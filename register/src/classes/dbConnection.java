/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tarun Bisht
 */
public class dbConnection 
{
    private final String url;
    private final String username;
    private final String password;
    private Connection conn;
    public dbConnection(String url,String username,String password)
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
            System.out.println("Connected");
        }
        catch(ClassNotFoundException | SQLException e)
        {
            conn=null;
            System.out.println("Failed to Load Mysql Driver" +e.getMessage());
        }
    }
    public Connection getConnection()
    {
        SetupConnection();
        return conn;
    }
}
