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
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    @FXML
    private TableColumn<archive ,Integer> txttotal;
    @FXML
    private TextField txtrecherche;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        connectionn = (Connection) connection.conDB();
      try {
          ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date,total from archive");
          
           while(rs.next())
   data.add(new archive(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"),rs.getInt("total"))) ; 
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
                       
txtnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
   txtquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
   txtdate.setCellValueFactory(new PropertyValueFactory<>("date"));
   txttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
table.setItems(data);
        ;
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
                  ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date,total from archive");
          
           while(rs.next())
   listRec.add(new archive(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"),rs.getInt("total"))) ; 
  
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

  @FXML
    private void recherche(javafx.event.ActionEvent event) {
        
                    List<archive> list = new ArrayList<archive>();

          //int total = 0 ; 
         connectionn = (Connection) connection.conDB();
      try {
          ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date,total from archive ");
          
                    //ResultSet rs1 = connectionn.createStatement().executeQuery("SELECT SUM(total) from commandes");
           while(rs.next()){
    list.add(new archive(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"),rs.getInt("total"))) ; 
     //    data.add(new commande(rs.getInt("totall")));
         //total += rs.getInt("total");
           }
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
        
      txtnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
      txtquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
      txtdate.setCellValueFactory(new PropertyValueFactory<>("date"));
      txttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
      //lbltotal.setText( String.valueOf(total));
      //tableview.setItems(observablelist);
      
      FilteredList<archive> filtredData= new FilteredList<>(data, b ->true);
      txtrecherche.textProperty().addListener((observable,oldValue,newValue) -> {
          Predicate<? super archive> Evenement;
          filtredData.setPredicate((archive evenement) -> {
              if (newValue == null || newValue.isEmpty()){
                  return true;
              }
              
              String lowerCaseFilter = newValue.toLowerCase();
              if(evenement.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ){
                  return true;
              }
              else if (evenement.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                  return true; // Filter matches last name.
              }
              
              else
                  return false;
          } );
      });
      // 3. Wrap the FilteredList in a SortedList.
      SortedList<archive> sortedData = new SortedList<>(filtredData);
      // 4. Bind the SortedList comparator to the TableView comparator.
      // 	  Otherwise, sorting the TableView would have no effect.
      sortedData.comparatorProperty().bind(table.comparatorProperty());
      // 5. Add sorted (and filtered) data to the table.
      table.setItems(sortedData);
      
  
    }
 
    
    
}
