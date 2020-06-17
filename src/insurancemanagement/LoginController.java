/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancemanagement;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import DB.DBConnection;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 *
 * @author Delete
 */
public class LoginController implements Initializable {
    
    
    @FXML
    private void handleClose(MouseEvent event) {
       System.exit(0);
    }
    @FXML
    private TextField txtUsername;
    
     @FXML
    private TextField txtPassword;
     
      @FXML
    private Button btnSignin;
        @FXML
    private Label error;
    

      @FXML
        private void handleButtonAction(ActionEvent event) throws IOException { 
       
            
        if(event.getSource()==btnSignin){
          boolean login=logIn();
           if(login){
              try{
                  System.out.println("Yes");
                  Parent root;
         root = FXMLLoader.load(getClass().getResource("InsuranceMain.fxml"));
         Stage stage = new Stage();
         stage.setTitle("My New Stage Title");
         stage.setScene(new Scene(root, 700, 550));
         stage.show();
         // Hide this current window (if this is what you want)
         ((Node)(event.getSource())).getScene().getWindow();
  
           }catch(Exception e)
                   {
                   System.err.println(e);
                   }
           }
        }
      
        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public LoginController(){
        try {
            con=DBConnection.connect();
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   Connection con=null;
    PreparedStatement preparedStatement=null;
    ResultSet resultSet=null;

    public boolean logIn(){
        String email;
        email = txtUsername.getText();
        String password;
        password = txtPassword.getText();
        
        
        
        String sql="select * from admins where email = ? and password = ?";
        try{
        preparedStatement =con.prepareStatement(sql);
        preparedStatement.setString(1,email);
        preparedStatement.setString(2,password);
        resultSet=preparedStatement.executeQuery();
        if(!resultSet.next())
        {
            error.setTextFill(Color.TOMATO);
            error.setText("Enter Correct Credentials");
           System.err.println("Wrong Login");
            return false;
        }
        else
        {
             error.setTextFill(Color.GREEN);
           error.setText("login Successful");
           System.out.println("SuccessfulLogin");
           return true;
        }
        }catch(SQLException ex){
          
         System.err.println(ex.getMessage());
     
        }
        return false;
    }

   
    }

    
    
    
    

