/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package insurancemanagement;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author tanzeem
 */
public class InsuranceMain implements Initializable {
    
    @FXML
    private BorderPane rootLayout;
    @FXML
    private ToggleGroup g1;
    @FXML
    private ToggleButton paymentBtn;
    @FXML
    private ToggleButton clientBtn;
    @FXML
    private ToggleButton insuranceBtn;
    @FXML
    private ToggleButton branchBtn;
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeScene("PaymentsScene.fxml");
        // TODO
    }    
    
    
     

    @FXML
    private void setPaymentScene(ActionEvent event) {
        changeScene("PaymentsScene.fxml");
    } 

    @FXML
    private void setClientScene(ActionEvent event) {
        changeScene("ClientScene.fxml");
    }
    

    @FXML
    private void setInuranceScene(ActionEvent event) {
        changeScene("InsuranceScene.fxml");
    }

    @FXML
    private void setBranchScene(ActionEvent event) {
        changeScene("BranchScene.fxml");
    }
    
 public  void changeScene(String scenePath){
        
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource(scenePath));
        AnchorPane pane = new AnchorPane();
    try{
            pane = (AnchorPane) loader.load();
            rootLayout.setCenter(pane);
        }
        catch(Exception e){
        }
}
}