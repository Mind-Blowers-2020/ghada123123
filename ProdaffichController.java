/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import entities.commande;
import entities.produit;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static pi.FXMLDocumentController.idproduit;
import utils.connection;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class ProdaffichController implements Initializable {

    @FXML
    private TableView<produit> table;
    @FXML
    private TableColumn<produit, String> txtnom;
    @FXML
    private TableColumn<produit, Double> txtprix;
    @FXML
    private TableColumn<produit, Integer> txtquantite;
public ObservableList<produit>  data=FXCollections.observableArrayList();
    PreparedStatement preparedStatement;
    Connection connectionn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODOint total = 0 ; 
         connectionn = (Connection) connection.conDB();
      try {
          ResultSet rs = connectionn.createStatement().executeQuery("SELECT idP,nomprod,prix,qt from produit ");
          
                    //ResultSet rs1 = connectionn.createStatement().executeQuery("SELECT SUM(total) from commandes");
           while(rs.next()){
    data.add(new produit(rs.getInt("idP"),rs.getString("nomprod"),rs.getDouble("prix"),rs.getInt("qt"))) ; 
     //    data.add(new commande(rs.getInt("totall")));
        // total += rs.getInt("total");
           }
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
   txtnom.setCellValueFactory(new PropertyValueFactory<>("nomprod"));
   txtquantite.setCellValueFactory(new PropertyValueFactory<>("qt"));
   txtprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
   //txttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
   //lbltotal.setText( String.valueOf(total));
    // txttotal1.setCellValueFactory(new PropertyValueFactory<>("totall"));

table.setItems(data);
        
    }    

    @FXML
    private void AjouterCommande(ActionEvent event) {
        
        idproduit =table.getSelectionModel().getSelectedItem().getIdP();
                   System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxxx"+idproduit);

                              Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                            Stage myWindow = (Stage)table.getScene().getWindow();
                            Scene sc = new Scene(root);
                            myWindow.setScene(sc);
                            myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
                            myWindow.show();
                        } catch (IOException ex) {
                            Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
                        } 
        
        
    }
    
}
