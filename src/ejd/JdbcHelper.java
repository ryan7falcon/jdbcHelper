//JdbcHelper.java
//===============
//simple light way JDBC Helper class to interactwith database
//
//AUTHOR:  Valeriya Rud (lr@mail.ru)
//CREATED: 18.09.2015
//UPDATED: 24.09.2015

package ejd;
//can be used import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;


public class JdbcHelper {

    //instance member variable
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    String errorMessage;
    
    //return the current error message
    public String getErrorMessage(){
        return errorMessage;
    }
    //can be put as instance variable as well
    //privat boolean connected = false;
    //public boolean isConnected(){return connected;}
    
    //constructor
    
    //connect to database with three param; URL, username, password
    //it returns true if success, otherwise it returns false
    //three exceptions: classNotFound, SQLException, Exception 
    public boolean connect(String url, String user, String pass){
        //inicialize variable as a false to begin with
        boolean connected = false;
        
        try{
            //generic purpose but can use only mysql
            //make db connecion using 3 param it will load driver automatically
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, pass);
            //create sql statement object
            statement = connection.createStatement();
            //everything is fine - we are gettint connection
            connected = true;
        }
        catch(ClassNotFoundException e){
            System.out.println("[ERROR]" + e.getMessage());
            //the same
            //System.out.println(e.getMessage()); 
        }
        catch(SQLException e){
            System.out.println("[ERROR]" + e.getSQLState() + ": " + e.getMessage()); 
        }
        catch(Exception e){
            System.out.println("[ERROR]" + e.getMessage());
        }
           
        return connected;
    }
    //
    public void disconnect(){//can be used, but not the best way throws Exception
        try{resultSet.close();}catch(Exception e){}
        try{statement.close();} catch(Exception e){}
        try{connection.close();} catch(Exception e){}
    }
    
    //
    public ResultSet query(String sql){
        //reset
        errorMessage = "";
        resultSet = null;
        
        try{
            resultSet = statement.executeQuery(sql);
        }
        catch(SQLException e){
           errorMessage = "[ERROR]" + e.getSQLState() + e.getMessage();
           System.err.println(errorMessage);
        }
        catch(Exception e){
            errorMessage = "[ERROR]" + e.getMessage();
        }
        return this.resultSet;
    }
    
    //executes the sql and return zero or # of rows updated 
    //if failed, it returns -1
    public int update(String sql){
        int result = -1; //default return value
        errorMessage = ""; 
        
        try{
            result = statement.executeUpdate(sql);
        }
        catch(SQLException e){
            errorMessage = "[ERROR] "  + e.getSQLState() + ": " + e.getMessage();
            System.err.println(errorMessage);
        }
        catch(Exception e){
             
        }
        //if it is success, it will return prorer value, othervise will be exception throun
        return result;
    }
}
