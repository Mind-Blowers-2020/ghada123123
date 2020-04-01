/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import entities.commande;
import entities.archive;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.connection;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class ArchiverController implements Initializable {

    @FXML
    private TableColumn<archive ,String> txtnom;
    @FXML
    private TableColumn<archive ,Integer> txtquantite;
    @FXML
    private TableColumn<archive ,String> txtdate;
    @FXML
    private Button btndelete;
 PreparedStatement preparedStatement;
    Connection connectionn =  (Connection) connection.conDB();  
    public ObservableList<archive>  data=FXCollections.observableArrayList();
    @FXML
    private TableView<archive> table;
    @FXML
    private Button btnretour;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
             connectionn = (Connection) connection.conDB();
      try {
          ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date from archive");
          
           while(rs.next())
   data.add(new archive(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"))) ; 
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
                       
txtnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
   txtquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
   txtdate.setCellValueFactory(new PropertyValueFactory<>("date"));
table.setItems(data);
        // TODO
    }    
    public void suprimercmd(int id) throws  SQLException
    { 
        connectionn = (Connection) connection.conDB();
      
        String req= "DELETE FROM archive where id ="+id;  
         preparedStatement = (PreparedStatement) connectionn.prepareStatement(req);


      preparedStatement.executeUpdate(req);
      
    }
    
       @FXML
    private void supprimer(javafx.event.ActionEvent event) throws SQLException {
                        archive userSelec = (archive) table.getSelectionModel().getSelectedItem();
                    System.out.println("hahahahahahah"+userSelec);

                       suprimercmd(userSelec.getId());
                         resetTableData();
    }

    
    public void resetTableData() throws SQLException
    {
        List<archive> listRec = new ArrayList<>();
                  ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date from archive");
          
           while(rs.next())
   listRec.add(new archive(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"))) ; 
  
        ObservableList<archive> data = FXCollections.observableArrayList(listRec);
        table.setItems(data);
    }

    @FXML
    private void retour(ActionEvent event) {
        
           try {

                   
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("archive.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
    }

    
    
}
