/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import utils.connection;

/**
 *
 * @author esprit
 */
public class FXMLDocumentController implements Initializable {
    
      
  @FXML
    private Button btnbutton;

    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtquantite;

     @FXML
    private DatePicker txtdatee;
   
@FXML
    private Label lblstatut;
    PreparedStatement preparedStatement;
    Connection connectionn;

    public FXMLDocumentController() {
        connectionn = (Connection) connection.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }
       @FXML
    private void handleButtonAction(MouseEvent event) {
          if (txtnom.getText().isEmpty() || txtquantite.getText().isEmpty() || txtdatee.getValue().equals(null)) {
            lblstatut.setTextFill(Color.TOMATO);
            lblstatut.setText("Veuillez entrer tous les details");
        } else {
            saveData();
              
                try {

                   
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/pi/afficher.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
        }

}
     private String saveData() {

        try {
            String st = "INSERT INTO commandes ( nom, quantite, date ) VALUES (?,?,?)";
            LocalDate date=txtdatee.getValue();
            preparedStatement = (PreparedStatement) connectionn.prepareStatement(st);
            preparedStatement.setString(1, txtnom.getText()); 
        
            preparedStatement.setString(2, txtquantite.getText());
            preparedStatement.setString(3, date.toString())    ;
           

            preparedStatement.executeUpdate();
            lblstatut.setTextFill(Color.GREEN);
            lblstatut.setText("Added Successfully");
  
             
         
           
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblstatut.setTextFill(Color.TOMATO);
            lblstatut.setText(ex.getMessage());
            return "Exception";
        }
    }
  
}

    
 
    

     
    
    
   

    