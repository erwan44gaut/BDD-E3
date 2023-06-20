package src.util;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class DatabaseConnection 
{
    private static Connection connection;

    public static void connect()
    {
        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } 
        catch (ClassNotFoundException e) 
        {
            e.printStackTrace();
        }
        try 
        {
            connection = DriverManager.getConnection
            (
                Config.getProperty("database.url"),
                Config.getProperty("database.username"),
                Config.getProperty("database.password")
            );
            System.out.println("Database connection opened");
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
    }

    public static void disconnect()
    {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database connection closed");
    }

    public static int executeUpdate(String action) 
    {
        try (Statement statement = connection.createStatement()) 
        {
            return statement.executeUpdate(action);
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean execute(String action) 
    {
        try (Statement statement = connection.createStatement()) 
        {
            return true;
        }
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        }
    }

    public static ResultSet query(String query) 
    {
        try (Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {
            CachedRowSet cachedResultSet = RowSetProvider.newFactory().createCachedRowSet();
            cachedResultSet.populate(resultSet);

            return cachedResultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CallableStatement prepareCall(String call)
    {
        CallableStatement statement;
        try {
            statement = connection.prepareCall(call);
            return statement;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void setStatement(CallableStatement statement, int index, int n)
    {
        try {
            statement.setInt(index, n);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setStatement(CallableStatement statement, int index, String s)
    {
        try {
            statement.setString(index, s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet queryStatement(CallableStatement statement)
    {
        try {
            return statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int updateStatement(CallableStatement statement)
    {
        try {
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean executeStatement(CallableStatement statement)
    {
        try {
            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
