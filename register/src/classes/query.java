/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Tarun Bisht
 */
public class query 
{
    public static boolean Query(String host,String port,String username, String password,String sql,Object[] parameters)
    {
        String url="jdbc:mysql://"+host+":"+port+"/el";
        dbConnection  dbconn=new dbConnection (url,username,password);
        PreparedStatement stmt=null;
        Connection conn=null;
        try
        {
            conn = dbconn.getConnection();
            stmt=conn.prepareStatement(sql);
            for(int j=0;j<parameters.length;j++)
            {
                stmt.setObject(j+1,parameters[j]);
            }
            System.out.println("PROCESSING:::");
            return stmt.executeUpdate()>=1;
        }
        catch(SQLException e)
        {
            System.out.println("Error occur while starting new session "+"ERROR::: "+e.getMessage());
        }
        finally
        {
            if(stmt!=null)
            {
                try {
                    stmt.close();
                } catch (SQLException ex) {
                    System.out.println("Error occur while starting new session "+"ERROR::: "+ex.getMessage());
                }
            }
            if(conn!=null)
            {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Error occur while starting new session "+"ERROR::: "+ex.getMessage());
                }
            }
        }
        return false;
    }
}
