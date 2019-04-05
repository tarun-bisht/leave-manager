/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_classes;

/**
 *
 * @author Tarun Bisht
 */
public class Session 
{
    private static int id=0;
    private static String username="";
    private static String pwd="";
    public static void SetSession(int userid,String adminusername,String adminpassword)
    {
        id=userid;
        username=adminusername;
        pwd=adminpassword;
    }
    public static String GetPassword()
    {
        return pwd;
    }
    public static String GetUsername()
    {
        return username;
    }
    public static int GetId()
    {
        return id;
    }
}
