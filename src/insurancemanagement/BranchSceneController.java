/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancemanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import Model.Employee;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class BranchSceneController implements Initializable {

    @FXML
    private TextField branchCode;
    @FXML
    private TextField branchName;
    @FXML
    private TextField branchAdrs;
    @FXML
    private Label warnMsg;
    @FXML
    private Button subBranchBTn;
    @FXML
    private TextField employeeName;
    @FXML
    private TextField employeeCntc;
    @FXML
    private TextArea employeeAdrs;
    @FXML
    private TableView<Employee> employeeTable;
    @FXML
    private TableView<?> branchTable;

    /**
     * Initializes the controller class.
     */
     DisplayDatabase branchData = new DisplayDatabase();
    @FXML
    private Button addEmployeeBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        branchData.buildData(branchTable, "Select * from branchTable;");
        createStaffTable();
    }    
String bCode = "";
String bName = "";
String bAdd = "";

String eName = "";
String eCntc = "";
String eAdrs = "";  

ObservableList<Employee> staffList = FXCollections.observableArrayList();

    @FXML
    private void addEmployee(ActionEvent event) {
        
        getEmployeeFields();
                
        staffList.add(new Employee(eName,eCntc,eAdrs));
        employeeTable.setItems(staffList);
        
        clearFields();
            
   
     //bookTableData.buildData(bookTable, "Select * from bookingtable;");
    }

    private void getEmployeeFields() {
        eName = employeeName.getText();
        eCntc = employeeCntc.getText();
        eAdrs = employeeAdrs.getText();
    }

    private void clearFields() {
        employeeName.clear();
        employeeCntc.clear();
        employeeAdrs.clear();

    }
    
    
    
    
    


    private void createStaffTable() {
       
        TableColumn col_name = new TableColumn("Name");
        col_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getName());              
           }            
         });  
        employeeTable.getColumns().addAll(col_name);
        
           TableColumn col_cont = new TableColumn("Contact");
        col_cont.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getContact());              
           }            
         });  
        employeeTable.getColumns().addAll(col_cont);
        
           TableColumn col_add = new TableColumn("Address");
        col_add.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Employee,String>,ObservableValue<String>>(){            
           public ObservableValue<String> call(TableColumn.CellDataFeatures<Employee, String> param) {                                                 
             return new SimpleStringProperty(param.getValue().getAdd());              
           }            
         }); 
        
        employeeTable.getColumns().addAll(col_add);
        
        
        
    }

    @FXML
    private void mDeleteStaff(ActionEvent event) {
        int index = employeeTable.getSelectionModel().getSelectedIndex();
        staffList.remove(index);
        branchTable.refresh();
    }



    private boolean getBranchFields() {
        
        bCode = branchCode.getText();
        bName = branchName.getText();
        bAdd = branchAdrs.getText();
        
        
        if(bCode==null || bCode.isEmpty()){
            branchCode.requestFocus();
            warnMsg.setText("Pls enter branch code.");
            return false;
        }
        
        if(bName==null || bName.isEmpty()){
            branchName.requestFocus();
            warnMsg.setText("Pls enter branch Name.");
            return false;
        }
        
        if(bAdd==null || bAdd.isEmpty()){
            branchAdrs.requestFocus();
            warnMsg.setText("Pls enter branch Address.");
            return false;
        }
      
        
        return true;
    }

    private void clearAllFields() {
        branchName.clear();
        branchCode.clear();
        branchAdrs.clear();
     
        staffList.clear();
        branchTable.refresh();
        
    }

    @FXML
    private void mDeleteBranch(ActionEvent event) {
         int index = branchTable.getSelectionModel().getSelectedIndex();
         ObservableList data = branchData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "BranchTable");
         
         branchData.buildData(branchTable, "Select * from branchTable;");
    }


    
    @FXML
    private void submitBranch(ActionEvent event) {
        
       boolean val =  getBranchFields();
       if(!val){
       return;
       }
       
       Connection c;
       try{
           c = DBConnection.connect();
            String query = "INSERT INTO BranchTable (branchcode,branchName,branchAddress)VALUES("+
"'"+bCode+"',\n" +
"'"+bName+"',\n" +
"'"+bAdd+"');";                    
         
            c.createStatement().execute(query);
            
            for(Employee i: staffList){
          
             query = "INSERT INTO EmployeeTable (BCode,EmployeeName,EmployeeContact,EmployeeAddress)VALUES("+
                "'"+bCode+"',\n" +
                "'"+i.getName()+"',\n" +
                "'"+i.getContact()+"',\n" +
                "'"+i.getAdd()+"');";                    
         
            c.createStatement().execute(query);
            
            }
            
            
            
            c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(BranchSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         branchData.buildData(branchTable, "Select * from branchTable;");
    }

    
}

