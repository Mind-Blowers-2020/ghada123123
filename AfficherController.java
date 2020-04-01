package pi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.date;
import entities.commande;
import java.awt.HeadlessException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.URI ; 
import utils.connection;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import javafx.scene.Parent;
/**
 *
 * @author esprit
 */
public class AfficherController implements Initializable {
    
      
  @FXML
    private TableView<commande> table ;
    @FXML
    private TableColumn<commande ,Integer> txtquantite ; 
    @FXML
    private TableColumn<commande ,String> txtnom ; 
   
      @FXML
    private TableColumn<commande ,String> txtdate ; 
        @FXML
        private Button btndelete ;  
        
   public ObservableList<commande>  data=FXCollections.observableArrayList();
    PreparedStatement preparedStatement;
    Connection connectionn;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btnretour;
    @FXML
    public void afficher(){
    
    
    };
    @FXML
   public void afficherr(MouseEvent event)  {

   

   }
   

    

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         connectionn = (Connection) connection.conDB();
      try {
          ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date from commandes");
          
           while(rs.next())
   data.add(new commande(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"))) ; 
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
                       
txtnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
   txtquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
   txtdate.setCellValueFactory(new PropertyValueFactory<>("date"));
table.setItems(data);
        
      }

 
        private void Suppression(ActionEvent event) throws SQLException {
            
    ;
                  
                       /*     
                            root = FXMLLoader.load(getClass().getResource("/gui/AfficherToutProduit.fxml"));
                            Stage myWindow = (Stage) btndelete.getScene().getWindow();
                            Scene sc = new Scene(root);
                            myWindow.setScene(sc);
                            myWindow.setTitle("Welcome");
                            //myWindow.setFullScreen(true);
                            myWindow.show();
                        } catch (IOException ex) {
                            
                        }*/
        
    }
   
    public void suprimercmd(int id) throws  SQLException
    { 
        connectionn = (Connection) connection.conDB();
      
        String req= "DELETE FROM commandes where id ="+id;  
         preparedStatement = (PreparedStatement) connectionn.prepareStatement(req);


      preparedStatement.executeUpdate(req);
      
    }
    
       @FXML
    private void supprimer(javafx.event.ActionEvent event) throws SQLException {
                        commande userSelec = (commande) table.getSelectionModel().getSelectedItem();
                    System.out.println("hahahahahahah"+userSelec);

                       suprimercmd(userSelec.getId());
                         resetTableData();
    }

    
    public void resetTableData() throws SQLException
    {
        List<commande> listRec = new ArrayList<>();
                  ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date from commandes");
          
           while(rs.next())
   listRec.add(new commande(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"))) ; 
  
        ObservableList<commande> data = FXCollections.observableArrayList(listRec);
        table.setItems(data);
    }
        
        // TODO

    @FXML
    private void Modifier(javafx.event.ActionEvent event) {
        
        
                         UpdateController.idmof =table.getSelectionModel().getSelectedItem().getId();
                   System.out.println("cxxxxxxxxxxxxxxxxxxxxxxxxx"+UpdateController.idmof);

                              Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getResource("update.fxml"));
                            Stage myWindow = (Stage)btnupdate.getScene().getWindow();
                            Scene sc = new Scene(root);
                            myWindow.setScene(sc);
                            myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
                            myWindow.show();
                        } catch (IOException ex) {
                            Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
                        } 
        
        
    }

    @FXML
    private void retour(javafx.event.ActionEvent event) {
        
           try {

                   
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLDocument.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
           
           
    }

  
 

 
        
 
        
        
        
    
    }   
    
     
    
  


