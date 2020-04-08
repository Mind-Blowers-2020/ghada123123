/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import utils.connection;

/**
 * FXML Controller class
 *
 * @author khaoula
 */
public class commandecontroller implements Initializable {
    
  @FXML
    private Button btnbutton;

    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtquantite;

     @FXML
    private TextField txtdate;
   
@FXML
    private Label lblstatut;
    PreparedStatement preparedStatement;
    Connection connectionn;

    public commandecontroller() {
        connectionn = (Connection) connection.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       
    }

    @FXML
    private void HandleEvents(MouseEvent event) {
    
        if (txtnom.getText().isEmpty() || txtquantite.getText().isEmpty() || txtdate.getText().isEmpty()) {
            lblstatut.setTextFill(Color.TOMATO);
            lblstatut.setText("Veuillez entrer tous les details");
        } else {
            saveData();
        }

    }
  
   

    private String saveData() {

        try {
            String st = "INSERT INTO commande ( nom, quantite, date ) VALUES (?,?,STR_TO_DATE('07-25-2012','%m-%d-%y'))";
            preparedStatement = (PreparedStatement) connectionn.prepareStatement(st);
            preparedStatement.setString(1, txtnom.getText()); //hethi mte3 l username
        
            preparedStatement.setString(2, txtquantite.getText());
            preparedStatement.setString(3, txtdate.getText());
           

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

    private ObservableList<ObservableList> data;
    String SQL = "SELECT * from commande";

   
    private void fetColumnList() {

        try {
            ResultSet rs = connectionn.createStatement().executeQuery(SQL);

          
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                //tblData.getColumns().removeAll(col);
               // tblData.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }
}
