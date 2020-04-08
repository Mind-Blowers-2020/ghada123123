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
import java.sql.SQLDataException;
import java.sql.Statement;
import java.util.function.Predicate;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import static pi.UpdateController.idmof;
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
    private TableColumn<commande,Integer> txttotal;
    @FXML
    private TextField txtrecherche;
    @FXML
    private Label lbltotal;
    //public static String date ;
   // private TableColumn<commande, Integer> txttotal;
    @FXML
    public void afficher(){
    
    
    };
    @FXML
   public void afficherr(MouseEvent event)  {

   

   }
   

    

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       int total = 0 ; 
         connectionn = (Connection) connection.conDB();
      try {
          ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date,total from commandes ORDER BY date ASC");
          
                    //ResultSet rs1 = connectionn.createStatement().executeQuery("SELECT SUM(total) from commandes");
           while(rs.next()){
    data.add(new commande(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"),rs.getInt("total"))) ; 
     //    data.add(new commande(rs.getInt("totall")));
         total += rs.getInt("total");
           }
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
      
      
   txtnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
   txtquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
   txtdate.setCellValueFactory(new PropertyValueFactory<>("date"));
   txttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
   lbltotal.setText( String.valueOf(total));
    // txttotal1.setCellValueFactory(new PropertyValueFactory<>("totall"));

table.setItems(data);
        
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
                   int oldtotal = Integer.parseInt( lbltotal.getText());
                     lbltotal.setText(String.valueOf(oldtotal - userSelec.getTotal()));
                       suprimercmd(userSelec.getId());
                         resetTableData();
    }

    
    public void resetTableData() throws SQLException
    { // int total = 0 ; 
        List<commande> listRec = new ArrayList<>();
                  ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date,total from commandes");
          
           while(rs.next())
   listRec.add(new commande(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"),rs.getInt("total"))) ; 
          

        ObservableList<commande> data = FXCollections.observableArrayList(listRec);
         //total += rs.getInt("total");
           
        table.setItems(data);

    }
        
        // TODO

    @FXML
    private void Modifier(javafx.event.ActionEvent event) {
        
        
                     idmof =table.getSelectionModel().getSelectedItem().getId();
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
public commande findCommandeBydate(String date) {
        
       try {
             commande c=new commande();
             int i=0;
             String req="select * from commandes where date="+date;
             Statement st=connectionn.createStatement();
             ResultSet rs=st.executeQuery(req);
             while(rs.next())
             {
                 c.setId(rs.getInt(1));
                 c.setNom(rs.getString(2));
                 c.setQuantite(rs.getInt(4));
                 c.setDate(rs.getString(5));
                 c.setTotal(rs.getInt(6));
 
                 i++;
                         }
             if(i==0)
             {
                 return null;
             }else {
                 return c;
             }
         } catch (SQLException ex) {
             Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
             return null;

         }
       } 

    @FXML
    private void recherche(javafx.event.ActionEvent event) {
        
                    List<commande> list = new ArrayList<commande>();

          int total = 0 ; 
         connectionn = (Connection) connection.conDB();
      try {
          ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date,total from commandes ORDER BY date ASC");
          
                    //ResultSet rs1 = connectionn.createStatement().executeQuery("SELECT SUM(total) from commandes");
           while(rs.next()){
    list.add(new commande(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"),rs.getInt("total"))) ; 
     //    data.add(new commande(rs.getInt("totall")));
         total += rs.getInt("total");
           }
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
        
      txtnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
      txtquantite.setCellValueFactory(new PropertyValueFactory<>("quantite"));
      txtdate.setCellValueFactory(new PropertyValueFactory<>("date"));
      txttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
      lbltotal.setText( String.valueOf(total));
      //tableview.setItems(observablelist);
      
      FilteredList<commande> filtredData= new FilteredList<>(data, b ->true);
      txtrecherche.textProperty().addListener((observable,oldValue,newValue) -> {
          Predicate<? super commande> Evenement;
          filtredData.setPredicate((commande evenement) -> {
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
      SortedList<commande> sortedData = new SortedList<>(filtredData);
      // 4. Bind the SortedList comparator to the TableView comparator.
      // 	  Otherwise, sorting the TableView would have no effect.
      sortedData.comparatorProperty().bind(table.comparatorProperty());
      // 5. Add sorted (and filtered) data to the table.
      table.setItems(sortedData);
      
  
    }
 
        
        
    }



     
    
     
    
  


