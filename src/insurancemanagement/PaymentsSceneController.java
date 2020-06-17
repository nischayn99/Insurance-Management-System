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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author tanzeem
 */
public class PaymentsSceneController implements Initializable {

    @FXML
    private DatePicker payDate;
    @FXML
    private Label cName;
    @FXML
    private TextField iNumber;
    @FXML
    private ComboBox<String> payMonth;
    @FXML
    private ComboBox<String> payYear;
    @FXML
    private Label amountLable;
    @FXML
    private Label warnMsg;
    @FXML
    private TableView<String> paymentTable;
    @FXML
    private Button makePaymentBtn;
DisplayDatabase payData = new DisplayDatabase();
ObservableList<String> cList = FXCollections.observableArrayList(); 
ObservableList<String> iList = FXCollections.observableArrayList(); 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        payData.buildData(paymentTable, "Select * from paymentTable;");
        
         ResultSet rs = QueryDatabase.QueryDatabase("Select insuranceNum from insuranceTable;");
        if(rs!=null){
            try {
                while(rs.next()){
                    iList.add(rs.getString(1));
                }
            } catch (SQLException ex) {
                Logger.getLogger(InsuranceSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
      
      AutoCompletionBinding<String> auto = TextFields.bindAutoCompletion(iNumber,iList); 
      auto.setOnAutoCompleted(
      e -> {
          getClient();
      }
      );
      
        ObservableList<String> monthList = FXCollections.observableArrayList();
        monthList.add("Jan");
        monthList.add("Feb");
       monthList.add("Mar");
       monthList.add("Apr");
       monthList.add("May");
       monthList.add("Jun");
       monthList.add("Jul");
       monthList.add("Aug");
       monthList.add("Sep");
       monthList.add("oct");
       monthList.add("Nov");
       monthList.add("Dec");
       
       
       
       payMonth.setItems(monthList);
       
        ObservableList<String> YearList = FXCollections.observableArrayList();
        YearList.add("2015");
        YearList.add("2016");
       YearList.add("2017");
       YearList.add("2018");
       YearList.add("2019");
       YearList.add("2020");
       YearList.add("2021");
       YearList.add("2022");
      
       
       
       
       payYear.setItems(YearList);
       
//       rs = QueryDatabase.QueryDatabase("Select clientName from clientTable ;");
//        if(rs!=null){
//            try {
//                while(rs.next()){
//                    cList.add(rs.getString(1));
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(InsuranceSceneController.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        
//        }
//         
    //    AutoCompletionBinding<String> autoC = TextFields.bindAutoCompletion(cName,cList); 
     
        
        
        
        // TODO
    }    

String clName="";
String inNumber="";
String pMonth="";
String pYear="";
double pAmount=0;
LocalDateTime pDate;


   

    @FXML
    private void MakePayment(ActionEvent event) {
         Connection c;
        boolean val =  GetPayFields();
       if(!val){
       return;
       }
        try{
        ResultSet rs = QueryDatabase.QueryDatabase("Select * from paymentTable where insuranceNum='"+inNumber+"' "
                + " AND PayMonth='"+pMonth+"' "
                      + "AND PayYear='"+pYear+"' ;");
        if(rs!=null){
          if(rs.next()){
                      
        cName.setText("Already paid fot this month.");
        return;
         
                 }   
       
        
        }
       
    
           c = DBConnection.connect();
            String query = "INSERT INTO paymentTable (PayDate,client,Insurance,PayMonth,PayYear,PayAmount)VALUES("+
"'"+pDate+"',\n" +
"'"+clName+"',\n" +
"'"+inNumber+"',\n" + 
"'"+pMonth+"',\n" +
"'"+pYear+"',\n" +                    
"'"+pAmount+"');";            
         
            c.createStatement().execute(query);
             c.close();
       
       } catch (SQLException ex) {
            Logger.getLogger(ClientSceneController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       clearAllFields();
         payData.buildData(paymentTable, "Select * from paymentTable;");
        
        
    }
     @FXML
    private void deletePayment(ActionEvent event) {
        int index = paymentTable.getSelectionModel().getSelectedIndex();
         ObservableList data = payData.getData();
         ObservableList<String> items = (ObservableList) data.get(index);
         DeleteDatabase.deleteRecord(Integer.parseInt(items.get(0)), "paymentTable");
         
         payData.buildData(paymentTable, "Select * from paymentTable;");
        
        
    }

    private boolean GetPayFields() {
          pDate = LocalDateTime.of( payDate.getValue(),LocalTime.now());
          clName= cName.getText();
          inNumber= iNumber.getText();
          pMonth= (String) payMonth.getValue();
          pYear = (String) payYear.getValue();
          pAmount = Double.parseDouble(amountLable.getText());
          
          if(clName==null || clName.isEmpty()){
           cName.requestFocus();
            warnMsg.setText("Pls enter Clients Name.");
            return false;
        }
 if(inNumber==null || inNumber.isEmpty()){
           iNumber.requestFocus();
            warnMsg.setText("Pls enter Insurance Number.");
            return false;
        }
 if(pMonth==null || pMonth.isEmpty()){
           
            warnMsg.setText("Pls Select A Month.");
            return false;
        }
 
 
 if(pYear==null || pYear.isEmpty()){
           
            warnMsg.setText("Pls Select A Year.");
            return false;
        }
        
        return true;
       
    }

    private void clearAllFields() {
        payDate.setValue(LocalDate.now());
        cName.setText("");
        iNumber.clear();
        amountLable.setText("0.0");
    }

    @FXML
    private void getClient() {
        
        String iNum = iNumber.getText();
        if(iNum==null || iNum.isEmpty()){
       
        cName.setText("Invalid insurance Number.");
         return;
        }
        
        ResultSet rs = QueryDatabase.QueryDatabase("Select ClientsName,PayMonth from insuranceTable where insuranceNum='"+iNum+"';");
        if(rs==null){
        
        cName.setText("Client not found.");
        return;
        
        }else{
        
            try {
                if(rs.next()){
                    cName.setText(rs.getString(1));
                    amountLable.setText(rs.getString(2));
                }else{
                 cName.setText("Client not found.");
                     return;
                }
            } catch (SQLException ex) {
                Logger.getLogger(PaymentsSceneController.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
    }
}
