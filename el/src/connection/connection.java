/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

/**
 *
 * @author Tarun Bisht
 */
public class connection 
{
    private static String host="localhost";
    private static String port="3307";
    private static String DatabaseName="el";
    private static String username="";
    private static String password="";
    public static String GetHost()
    {
        return host;
    }
    public static String GetPort()
    {
        return port;
    }
    public static String GetConnectionURL()
    {
        return "jdbc:mysql://"+host+":"+port+"/"+DatabaseName;
    }
    public static String GetDatabaseUsername()
    {
        return username;
    }
    public static String GetDatabasePassword()
    {
        return password;
    }
    public static void SetHost(String databaseHost)
    {
        host=databaseHost;
    }
    public static void SetPort(String databasePort)
    {
        port=databasePort;
    }
    public static void SetDBUsername(String dbusername)
    {
        username=dbusername;
    }
    public static void SetDBPassword(String dbpassword)
    {
        password=dbpassword;
    }
}
