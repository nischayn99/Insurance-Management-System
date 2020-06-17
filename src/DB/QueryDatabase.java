/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DB;
import java.sql.Connection;  
 import java.sql.ResultSet;  
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;
/**
 *
 * @author Sameer
 */
public class QueryDatabase {
    
    
     public static ResultSet QueryDatabase(String query){
        ResultSet rs = null;
         Connection c ;  
        try { 
            c = DBConnection.connect();
        
        rs = c.createStatement().executeQuery(query);
    //    System.out.println("Queried: "+SQL);
        }catch(SQLException ex){
             System.out.println("Queried: "+query + "Error in sql");
        }
        
        
        return rs;  
    }

   
  
}
