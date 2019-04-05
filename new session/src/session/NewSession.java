/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import classes.query;
import java.io.Console;

/**
 *
 * @author Tarun Bisht
 */
public class NewSession {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        System.out.println("Employee Leave Manager\n Copyright (C) 2019 Tarun Bisht. All rights reserved");
        System.out.println("\n\t************Start New Session*************\n");
        Console c=System.console();
        String host,port,username,password;
        host=c.readLine("Enter Host of database: ");
        port=c.readLine("Enter Port of database: ");
        username=c.readLine("Enter Database Username: ");
        password=new String(c.readPassword("Enter Database Password: "));
        c.flush();
        String sql="update employee_leave set Total_Leave=Total_Leave+Leave_1_6+Leave_7_12,Leave_1_6=?,Leave_7_12=?;";
        Object[] parameters={16,15};
        if(query.Query(host, port, username, password, sql, parameters))
        {
            System.out.println("New Session Started Successfully");
        }
        else
        {
            System.out.println("Cannot Start new Session");
        }
    }
    
}
