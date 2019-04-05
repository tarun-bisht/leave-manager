/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package query;

import data_classes.Session;
import connection.databaseConnection;
import dialogs.special;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.mindrot.jbcrypt.BCrypt;
/**
 *
 * @author Tarun Bisht
 */
public class Query 
{
    private static String get_insert_sql_string(String tablename,String[] fields,int parameters_length)
    {
        int iter=fields.length;
        if(iter!=parameters_length)
        {
            return null;
        }
        String sql="insert into "+tablename+"(";
        for (int i=0;i<iter;i++) 
        {
            if(i<iter-1)
            {
                sql += fields[i] + ", ";
            }
            else if(i==iter-1)
            {
                sql+=fields[i]+") ";
            }
        }
        sql+="values(";
        for (int i=0;i<parameters_length;i++) 
        {
            if(i<iter-1)
            {
                sql += "?, ";
            }
            else if(i==iter-1)
            {
                sql+="?)";
            }
        }
        return sql;
    }
    public static boolean Insert_Query(String tablename,String[] fields,Object[] parameters) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
        PreparedStatement stmt=null;
        Connection conn=null;
        try
        {
            conn = dbconn.getConnection();
            int iter=parameters.length;
            String sql=get_insert_sql_string(tablename,fields,iter);
            stmt = conn.prepareStatement(sql);
            for (int i=0;i<iter;i++) 
            {
                stmt.setObject(i+1,parameters[i]);
            }
            return stmt.executeUpdate()>=1;
        }
        finally
        {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
    public static boolean Insert_Query(String sql_1,String sql_2,Object[] parameters_1,Object[] parameters_2) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
        PreparedStatement stmt_1=null,stmt_2=null;
        Connection conn=null;
        try
        {
            conn = dbconn.getConnection();
            conn.setAutoCommit(false);
            stmt_1=conn.prepareStatement(sql_1);
            for(int j=0;j<parameters_1.length;j++)
            {
                stmt_1.setObject(j+1,parameters_1[j]);
            }
            if(stmt_1.executeUpdate()==1)
            {
                stmt_2=conn.prepareStatement(sql_2);
                for(int j=0;j<parameters_2.length;j++)
                {
                    stmt_2.setObject(j+1,parameters_2[j]);
                }
                if(stmt_2.executeUpdate()>=1)
                {
                    conn.commit();
                    return true;
                }
                conn.rollback();
                return false;
            }
            conn.rollback();
            return false;
        }
        finally
        {
            if(stmt_1!=null)
            {
                stmt_1.close();
            }
            if(stmt_2!=null)
            {
                stmt_2.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
    public static boolean Insert_Query(String sql,Object[] parameters) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
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
            return stmt.executeUpdate()>=1;
        }
        finally
        {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
    public static boolean LoginMechanism(String user , String pass ) throws SQLException
    {
        Object[] parameters={user};
        ArrayList logindata=execute_select("select * from el_admin where username = ?",parameters);
        if(logindata.size()<1)
        {
            special.ErrorAlert("Account", "No User Found!", "Check Login Details");
            return false;
        }
        else
        {
            HashMap map=(HashMap) logindata.get(0);
            if(BCrypt.checkpw(pass, (String) map.get("password")))
            {
                Session.SetSession((int)map.get("id"),BCrypt.hashpw(user, BCrypt.gensalt()),(String) map.get("password"));
                return true;
            }
            else
            {
                special.ErrorAlert("Account", "Check Credential!", "Check Login Details");
                return false;
            }
        }  
    }
    public static int sql_num_rows(String sql,Object[] parameters ) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
        PreparedStatement stmt=null;
        Connection conn=null;
        try
        {
            conn = dbconn.getConnection();
            stmt = conn.prepareStatement(sql);
            for(int i=0;i<parameters.length;i++)
            {
                stmt.setObject(i+1,parameters[i]);
            }
            ResultSet result=stmt.executeQuery();
            int count=0;
            while(result.next())
            {
                count+=1;
            }
            return count;
        }
        finally
        {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
    public static int sql_num_rows(String sql) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
        PreparedStatement stmt=null;
        Connection conn=null;
        try
        {
            conn = dbconn.getConnection();
            stmt = conn.prepareStatement(sql);
            ResultSet result=stmt.executeQuery();
            int count=0;
            while(result.next())
            {
                count+=1;
            }
            return count;
        }
        finally
        {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
    public static Boolean remove(String table_name,String condition,Object[] parameters) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
        Connection conn=null;
        PreparedStatement stmt=null;
        try
        {
            conn = dbconn.getConnection();
            String sql="DELETE FROM "+table_name+" WHERE "+ condition;
            stmt = conn.prepareStatement(sql); 
            for (int i=0;i<parameters.length;i++) 
            {
                stmt.setObject(i+1,parameters[i]);
            }
            return stmt.executeUpdate() >=1;
        }
        finally
        {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
    public static boolean Validate_Session(String Password)
    {
        return BCrypt.checkpw(Password,Session.GetPassword());
    }
    public static ArrayList execute_select(String sql) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
        PreparedStatement stmt=null;
        Connection conn=null;
        try
        {
            conn = dbconn.getConnection();
            stmt = conn.prepareStatement(sql);
            ArrayList list;
            try (ResultSet result = stmt.executeQuery()) 
            {
                ResultSetMetaData meta=result.getMetaData();
                list = new ArrayList();
                int column=meta.getColumnCount();
                while(result.next())
                {
                    HashMap data=new HashMap(column);
                    for(int i=1;i<=column;++i)
                    {
                        data.put(meta.getColumnName(i), result.getObject(i));
                    }
                    list.add(data);
                }
            }
            return list;
        }
        finally
        {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
    public static ArrayList execute_select(String sql,Object[] parameters) throws SQLException
    {
        databaseConnection  dbconn=new databaseConnection (connection.connection.GetConnectionURL(),connection.connection.GetDatabaseUsername(),connection.connection.GetDatabasePassword());
        PreparedStatement stmt=null;
        Connection conn=null;
        try
        {
            conn = dbconn.getConnection();
            stmt = conn.prepareStatement(sql);
            for (int i=0;i<parameters.length;i++) 
            {
                stmt.setObject(i+1,parameters[i]);
            }
            ResultSet result = stmt.executeQuery();
            ResultSetMetaData meta=result.getMetaData();
            ArrayList list=new ArrayList();
            int column=meta.getColumnCount();
            while(result.next())
            {
                HashMap data=new HashMap(column);
                for(int i=1;i<=column;++i)
                {
                    data.put(meta.getColumnName(i), result.getObject(i));
                }
                list.add(data);
            }
            return list;
        }
        finally
        {
            if(stmt!=null)
            {
                stmt.close();
            }
            if(conn!=null)
            {
                conn.close();
            }
        }
    }
}
