/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;
import entities.commande;
import entities.produit;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import java.time.chrono.ChronoLocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;

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
     
   public static int idproduit ;

   
   @FXML
    private Label lblstatu;
    PreparedStatement preparedStatement;
    Connection connectionn;
    @FXML
    private Label label;
    @FXML
    private Label lblstatut;

    public FXMLDocumentController() {
        connectionn = (Connection) connection.conDB();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
       
    }
       @FXML
    private void handleButtonAction(MouseEvent event) throws IOException, NexmoClientException {
         ChronoLocalDate date=java.time.LocalDate.now();
          if (txtnom.getText().isEmpty() || txtquantite.getText().isEmpty() || txtdatee.getValue().equals(null) ) {
            lblstatu.setTextFill(Color.TOMATO);
            lblstatu.setText("Veuillez entrer tous les details specifiées");
            
          }
          else if  ( Integer.parseInt(txtquantite.getText())> findProduitById(idproduit).getQt() ){
                    
lblstatu.setTextFill(Color.TOMATO);
            lblstatu.setText("stock insuffisant");
                    
                    }   
          else if(txtdatee.getValue().isEqual(date)==false){
          
          
          lblstatu.setTextFill(Color.TOMATO);
            lblstatu.setText("Veuillez la date");
          
          }//LocalDate s=txtdatee.getValue();
             // System.out.println(date);
              //System.out.println(s);
              
        
        
          
          else {
              
                           //if(findProduitById(idproduit).getPrix().compareTo(Double.parseDouble(txtquantite.getText()))) 
                           
                 Updateqtproduit(findProduitById(idproduit).getQt()-Integer.parseInt(txtquantite.getText()), idproduit);
            saveData();
          NexmoClient client = new NexmoClient.Builder()
  .apiKey("72d3a06e")
  .apiSecret("24gNidKNwre7btbb")
  .build();

String messageText = "commande ok ";
TextMessage message = new TextMessage("Vonage SMS API", "21627183548", messageText);

SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
    System.out.println(responseMessage);
}
   
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
    
         public produit findProduitById(int id) {
        
       try {
             produit c=new produit();
             int i=0;
             String req="select * from produit where idP="+id;
             Statement st=connectionn.createStatement();
             ResultSet rs=st.executeQuery(req);
             while(rs.next())
             {
                 c.setIdP(rs.getInt(6));
                 c.setNomprod(rs.getString(1));
                 c.setPrix(rs.getDouble(3));
                 c.setQt(rs.getInt(7));
                

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

        try {
            String st = "INSERT INTO commandes ( nom, quantite, date,id_produit,total ) VALUES (?,?,?,?,?)";
            LocalDate date=txtdatee.getValue();
            preparedStatement = (PreparedStatement) connectionn.prepareStatement(st);
            preparedStatement.setString(1, txtnom.getText()); 
        
            preparedStatement.setString(2, txtquantite.getText());
            preparedStatement.setString(3, date.toString())    ;
            System.out.println("pi.FXMLDocumentController.saveData()"+idproduit);
            preparedStatement.setInt(4,idproduit);
            preparedStatement.setInt(5, (int) (findProduitById(idproduit).getPrix()*(Integer.parseInt(txtquantite.getText()))));

            

           

            preparedStatement.executeUpdate();
            lblstatu.setTextFill(Color.GREEN);
            lblstatu.setText("Added Successfully");
  
             
         
           
            return "Success";

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            lblstatu.setTextFill(Color.TOMATO);
            lblstatu.setText(ex.getMessage());
            return "Exception";
        }
    }

  public boolean Updateqtproduit( int quantite,  int id) {

        String req;
     req = "UPDATE produit SET  qt= ?  where idP=?;";
        try {
         preparedStatement = (PreparedStatement) connectionn.prepareStatement(req);

            //preparedStatement.setString(1, nom);
            preparedStatement.setInt(1, quantite);
            //preparedStatement.setString(3, date);
            preparedStatement.setInt(2, id);
            //preparedStatement.setInt(4,50*(Integer.parseInt(txtquantite.getText())));
            if (preparedStatement.executeUpdate() != 0) {
                System.out.println("produit Updated");
            } else {
                System.out.println("non");
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return false;
    }
}

    
 
    

     
    
    
   

    