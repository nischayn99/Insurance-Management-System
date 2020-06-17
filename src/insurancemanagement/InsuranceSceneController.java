/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancemanagement;

import DB.DBConnection;
import DB.DeleteDatabase;
import DB.DisplayDatabase;
import DB.QueryDatabase;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class InsuranceSceneController implements Initializable {

    @FXML
    private DatePicker iSDate;
    @FXML
    private DatePicker iEDate;
    @FXML
    private Button addInsuranceBtn;
    @FXML
    private TableView<?> insuranceTable;
    @FXML
    private TextField iNum;
    
    @FXML
    private TextField amount;
    @FXML
    private TextField iPayMonth;
DisplayDatabase insuranceData = new DisplayDatabase();
ObservableList<String> cList = FXCollections.observableArrayList(); 
    @FXML
    private Label warnMsg;
    @FXML
    private MenuItem deleteInsuranceBtn;
    @FXML
    private TextField cName;
    @FXML
    private ComboBox<String> branchCombo;
    @FXML
    private ComboBox<String> comboEmployee;
     ObservableList<String> bList = FXCollections.observableArrayList();
     ObservableList<String> eList = FXCollections.observableArrayList(); 
    @FXML
    private ComboBox<String> inTypeCombo;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        insuranceData.buildData(insuranceTable, "Select * from insuranceTable;");
        iSDate.setValue(LocalDate.now());
        iEDate.setValue(LocalDate.now());
        warnMsg.setText("");
         ResultSet rs = QueryDatabase.QueryDatabase("Select branchcode from branchTable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    bList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsuranceSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
        branchCombo.setItems(bList);
        ObservableList<String> iList = FXCollections.observableArrayList();
        iList.add("Life Insurance");
        iList.add("Health Insurance");
       iList.add("Long-Term Disability Coverage");
       iList.add("Auto Insurance");
       
       
       inTypeCombo.setItems(iList);
       
       rs = QueryDatabase.QueryDatabase("Select clientName from clientTable ;");
        if(rs!=null){
            try {
                while(rs.next()){
                    cList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsuranceSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
          AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(cName,cList); 
      
     
        
        
        
        // TODO
    }    
String inNum="";
String clName="";
String inType="";
double inAmount=0;
String inPayM="";
LocalDateTime startDate;
LocalDateTime endDate;
String bCode="";
String eName="";

    @FXML
    private void AddInsurance(ActionEvent event) throws SQLException {
        Connection c;
        boolean val =  GetInsuranceFields();
       if(!val){
       return;
       }
       c = DBConnection.connect();
       try{
           if(!update){
           
            String query = "INSERT INTO insuranceTable (ClientsName,insuranceNum,insuranceType,Amount,PayMonth,StartDate,EndDate,BranchName,EmployeeName)VALUES("+
"'"+clName+"',\n" +
"'"+inNum+"',\n" +
"'"+inType+"',\n" +
  "'"+inAmount+"',\n" +
  "'"+inPayM+"',\n" +
   "'"+startDate+"',\n" +
"'"+endDate+"',\n" + 
 "'"+bCode+"',\n" +                   
"'"+eName+"');";            
         
            c.createStatement().execute(query);
            }else{
              c = DBConnection.connect();
           String query = "Update insuranceTable set ClientsName='"+clName+"',insuranceNum='"+inNum+"',insuranceType='"+inType+"',"
                   + "Amount='"+inAmount+"',PayMonth='"+inPayM+"',StartDate='"+startDate+"',EndDate='"+endDate+"',BranchName='"+bCode+"',EmployeeName='"+eName+"' Where Id='"+id+"';";
                  System.out.println(query);
           c.createStatement().executeUpdate(query);
          } 
             c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(InsuranceSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         insuranceData.buildData(insuranceTable, "call stored_proc()");
        
       
        
    }
    
    private boolean GetInsuranceFields() {
       inNum = iNum.getText();
        clName = cName.getText();
        inType= inTypeCombo.getValue();
        String iamt    = amount.getText();
        inPayM    = iPayMonth.getText();    
        startDate  = LocalDateTime.of( iSDate.getValue(),LocalTime.now());
        endDate  =  LocalDateTime.of( iEDate.getValue(),LocalTime.now());    
         bCode = (String) branchCombo.getValue();
         eName  = (String) comboEmployee.getValue();
        if(inNum==null || inNum.isEmpty()){
            iNum.requestFocus();
            warnMsg.setText("Pls enter Insurance Number");
            return false;
        }
        
        if(clName==null || clName.isEmpty()){
           cName.requestFocus();
            warnMsg.setText("Please enter Insurance Name.");
            return false;
        }
        
        if(inType==null || inType.isEmpty()){
            
            warnMsg.setText("Please Give Insurance Type.");
            return false;
        }
        if(iamt==null || iamt.isEmpty()){
            amount.requestFocus();
            warnMsg.setText("Please enter Amount.");
            return false;
        }else{
        inAmount = Double.parseDouble(iamt);
        }
        if(inPayM==null || inPayM.isEmpty()){
            iPayMonth.requestFocus();
            warnMsg.setText("Please enter Pay Per Month.");
            return false;
        }
        if(bCode==null || bCode.isEmpty()){
            
            warnMsg.setText("Please Select a Branch.");
            return false;
        }
        if(eName==null || eName.isEmpty()){
            
            warnMsg.setText("Please Select an Employee.");
            return false;
        }
        
        
        return true;
    }

   

    @FXML
    private void deleteInsurance(ActionEvent event) {
   int index = insuranceTable.getSelectionModel().getSelectedIndex();
         ObservableList data = insuranceData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "InsuranceTable");
         
         insuranceData.buildData(insuranceTable, "CALL stored_proc()");
         
    }

    @FXML
    private void getEmployee(ActionEvent event) {
         eList.clear();
        String brCode = branchCombo.getValue();
         ResultSet rs = QueryDatabase.QueryDatabase("Select EmployeeName from EmployeeTable where bcode = '"+brCode+"';");
        if(rs!=null){
            try {
                while(rs.next()){
                    eList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsuranceSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
        comboEmployee.setItems(eList);
        
        
    }
    DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
     private void clearAllFields() {
       iNum.clear();
       cName.clear(); 
             
        amount.clear();       
       iPayMonth.clear();        
       iSDate.setValue(LocalDate.now());
        iEDate.setValue(LocalDate.now()); 
        warnMsg.setText("");
        addInsuranceBtn.setText("Add");
        update=false;
    }
    
boolean update = false;
    int id;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @FXML
    private void UpdateInsurance(ActionEvent event) {
        int index = insuranceTable.getSelectionModel().getFocusedIndex();
      ObservableList<ObservableList> data = insuranceData.getData();
      ObservableList<String> itemData = data.get(index);
      


String[] sdate = itemData.get(6).split(" ");  // get only date from DateTime
            String[] edate = itemData.get(7).split(" ");
            iSDate.setValue(LocalDate.parse(sdate[0],dateformat));
            iEDate.setValue(LocalDate.parse(edate[0],dateformat));
      
      id = Integer.parseInt(itemData.get(0));
      cName.setText(itemData.get(1));
        iNum.setText(itemData.get(2));
        inTypeCombo.setValue(itemData.get(3));
        amount.setText(itemData.get(4));
        iPayMonth.setText(itemData.get(5));
          branchCombo.setValue(itemData.get(8));
        comboEmployee.setValue(itemData.get(9));
        
        update=true;
        addInsuranceBtn.setText("Update");
    }
    
}
