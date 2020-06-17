/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancemanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class ClientSceneController implements Initializable {

    @FXML
    private Button addClientBtn;
    @FXML
    private TextField cName;
    @FXML
    private TextField cAdd;
    @FXML
    private TextField cContact;
    @FXML
    private TextField cAdhaar;
DisplayDatabase clientData = new DisplayDatabase();
    @FXML
    private TableView<?> clientTable;
    @FXML
    private Label warnMsg;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        warnMsg.setText("");
       
            
            clientData.buildData(clientTable, "Select * from clientTable;");
            // TODO
    }
String clContact="";
String clName="";
String clAdd="";
String clAdhaar="";

    @FXML
    private void AddClient(ActionEvent event) {
         Connection c;
        boolean val =  GetClientFields();
       if(!val){
       return;
       }
       try{
           c = DBConnection.connect();
            String query = "INSERT INTO clientTable (clientName,clientContact,clientAddress,clientAdhaar)VALUES("+
"'"+clName+"',\n" +
"'"+clContact+"',\n" +
"'"+clAdd+"',\n" +                 
"'"+clAdhaar+"');";            
         
            c.createStatement().execute(query);
             c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(ClientSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         clientData.buildData(clientTable, "Select * from clientTable;");
    }

    private boolean GetClientFields() {
         clContact = cContact.getText();
 clName= cName.getText();
 clAdd= cAdd.getText();
 clAdhaar= cAdhaar.getText();
 if(clName==null || clName.isEmpty()){
           cName.requestFocus();
            warnMsg.setText("Pls enter Clients Name.");
            return false;
        }
 if(clAdd==null || clAdd.isEmpty()){
           cAdd.requestFocus();
            warnMsg.setText("Pls enter Clients Address.");
            return false;
        }
 if(clContact==null || clContact.isEmpty()){
           cContact.requestFocus();
            warnMsg.setText("Pls enter Clients Contact.");
            return false;
        }
 
 
 if(clAdhaar==null || clAdhaar.isEmpty()){
           cAdhaar.requestFocus();
            warnMsg.setText("Pls enter Clients Adhaar.");
            return false;
        }
 return true;
        
    }

    private void clearAllFields() {
        cContact.clear();
         cName.clear();       
         cAdd.clear();       
         cAdhaar.clear();
         warnMsg.setText("");
                
    }

    @FXML
    private void deleteClient(ActionEvent event) {
         int index = clientTable.getSelectionModel().getSelectedIndex();
         ObservableList data = clientData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "clientTable");
         
        clientData.buildData(clientTable, "Select * from clientTable;");
    }
    
  
    
}
