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
import java.sql.Statement;
import javafx.scene.Parent;
import static pi.UpdateController.idmof;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class ArchiveController implements Initializable {

    @FXML
    private TableView<commande> table ;
    @FXML
    private TableColumn<commande ,Integer> txtquantitee ; 
    @FXML
    private TableColumn<commande ,String> txtnomm ; 
     public static int idmof ;
      @FXML
    private TableColumn<commande ,String> txtdatee ; 
        @FXML
        private Button btnarchive ;  
   public ObservableList<commande>  data=FXCollections.observableArrayList();
    PreparedStatement preparedStatement;
    Connection connectionn =  (Connection) connection.conDB();
    @FXML
    private Label lblstatut;
    @FXML
    private Button nexttab;
       //public ObservableList<archive>  dataa=FXCollections.observableArrayList();
   public void aficher(MouseEvent event)  {
                     


   }
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {

          ResultSet rs = connectionn.createStatement().executeQuery("SELECT id,nom,quantite,date from commandes");
          
           while(rs.next())
   data.add(new commande(rs.getInt("id"),rs.getString("nom"),rs.getString("date"),rs.getInt("quantite"))) ; 
      } catch (SQLException ex) {
          Logger.getLogger(AfficherController.class.getName()).log(Level.SEVERE, null, ex);
      }
                       
txtnomm.setCellValueFactory(new PropertyValueFactory<>("nom"));
   txtquantitee.setCellValueFactory(new PropertyValueFactory<>("quantite"));
   txtdatee.setCellValueFactory(new PropertyValueFactory<>("date"));

table.setItems(data);


    }


    @FXML
    private void archive(MouseEvent event) {
         
         
        


saveData();        
        
       
        
    }
     public commande findCommandeById(int id) {
        
       try {
             commande c=new commande();
             int i=0;
             String req="select * from commandes where id="+id;
             Statement st=connectionn.createStatement();
             ResultSet rs=st.executeQuery(req);
             while(rs.next())
             {
                 c.setId(rs.getInt(1));
                 c.setNom(rs.getString(2));
                 c.setDate(rs.getString(5));

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


   private String saveData() {
       //System.out.println("malakaw");
       try {
           commande c = new commande();
         archive e=new archive();
             commande userSelec = (commande) table.getSelectionModel().getSelectedItem();
                    System.out.println("hahahahahahah"+userSelec);
                            c= findCommandeById(userSelec.getId());
            String st = "INSERT INTO archive ( nom, quantite, date ) VALUES (?,?,?)";
            //LocalDate date=txtdatee.getValue();
            preparedStatement = (PreparedStatement) connectionn.prepareStatement(st);
            preparedStatement.setString(1, c.getNom()); 
        
            preparedStatement.setInt(2, c.getQuantite());
            preparedStatement.setString(3, c.getDate())    ;
           

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

   

    @FXML
    private void nexttableau(javafx.event.ActionEvent event) {
              try {

                   
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("archiver.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
    }

}

   
   

