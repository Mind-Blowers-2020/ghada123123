/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pi;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import entities.commande;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.connection;

/**
 * FXML Controller class
 *
 * @author esprit
 */
public class UpdateController implements Initializable {

 @FXML
    private Button btnupdate;

    @FXML
    private TextField txtnom;
    @FXML
    private TextField txtquantite;

     public static int idmof ;
   
    PreparedStatement preparedStatement;
    Connection connectionn =  (Connection) connection.conDB();
    @FXML
    private TextField txtdate;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        commande c = new commande();
        System.out.println("controller.azzzzzzzzzzz"+idmof+"zzzzzzz.initialize()");
        c= findCommandeById(idmof);
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //convert String to LocalDate
                //System.out.println("pi.UpdateController.initialize()"+c.getDate());

        //LocalDate date1 = LocalDate.parse(c.getDate(), formatter);
        //System.out.println("pi.UpdateController.initialize()"+date1);
        //LocalDate date=txtdatee.getValue();
        txtnom.setText(c.getNom());
       txtdate.setText(c.getDate());
        txtquantite.setText(String.valueOf(c.getQuantite()));
        
        
     
        
        
        // TODO
    }  
    public boolean Updateeventcours(String nom, String quantite, String date, int id) {

        String req = "UPDATE commandes SET  nom= ? , quantite = ? , date = ?  where id=?;";
        try {
         preparedStatement = (PreparedStatement) connectionn.prepareStatement(req);

            preparedStatement.setString(1, nom);
            preparedStatement.setString(2, quantite);
            preparedStatement.setString(3, date);
            preparedStatement.setInt(4, id);

            if (preparedStatement.executeUpdate() != 0) {
                System.out.println("comande Updated");
            } else {
                System.out.println("non");
            }
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return false;
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
                 c.setQuantite(rs.getInt(4));
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

    @FXML
    private void update(ActionEvent event) {
        
        
        
                String n= txtnom.getText();
                String q= txtquantite.getText();
                String d = txtdate.getText();
                
                Updateeventcours(n,q,d,idmof);
                
                
                Parent root;
                        try {
                            root = FXMLLoader.load(getClass().getResource("afficher.fxml"));
                            Stage myWindow = (Stage)txtdate.getScene().getWindow();
                            Scene sc = new Scene(root);
                            myWindow.setScene(sc);
                            myWindow.setTitle("page name");
                            //myWindow.setFullScreen(true);
                            myWindow.show();
                        } catch (IOException ex) {
                            Logger.getLogger(UpdateController.class.getName()).log(Level.SEVERE, null, ex);
                        } 
    }
    


}
