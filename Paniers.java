package GuiForm;


import GuiForm.Produits;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import Entity.Produit;
import GuiForm.BaseForm;
import GuiForm.Produits;
import Services.PanierService;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.nexmo.client.NexmoClient;
import com.nexmo.client.NexmoClientException;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.messages.TextMessage;
import java.io.IOException;
import java.util.ArrayList;

import com.nexmo.client.sms.messages.TextMessage;

import java.io.IOException;







/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASUS
 */
public class Paniers extends BaseForm {
    Form f ;
            float toatl = 0 ; 

    
    public Paniers(Resources res)   {
        Container table = new Container(BoxLayout.y()); //Logger.getLogger(Paniers.class.getName()).log(Level.SEVERE, null, ex);
        //Logger.getLogger(Paniers.class.getName()).log(Level.SEVERE, null, ex);
        ArrayList<Produit> seances = new ArrayList<>();
        seances = new PanierService().getPanier(1);
        for (Produit s : seances){
            toatl += s.getPrix();
            Container content = new Container(BoxLayout.x() );
            Container detail = new Container(BoxLayout.y());
            Container detail2 = new Container(BoxLayout.y());
            Button add = new Button("Supprimer");
            add.addActionListener((evt) -> {
                
                new PanierService().deletePanier(s.getId());
                
                new Paniers(res).getF().show(); // Logger.getLogger(Paniers.class.getName()).log(Level.SEVERE, null, ex);
                //  Logger.getLogger(Paniers.class.getName()).log(Level.SEVERE, null, ex);
                
                
            });
            
            
            Label nom = new Label("Nom :"+s.getNom());
            Label prix = new Label("Prix :"+s.getPrix());
            com.codename1.ui.Image Image = res.getImage(s.getImage()+".jpg");
            
            //com.codename1.ui.Image Image = res.getImage("3.png");
            com.codename1.ui.Image i =    Image.scaled(125, 125);
            
            
            
            
            
            detail.add(nom);
            detail.add(prix);
            content.add(i);
            content.add(detail);
            content.add(add);
            table.add(content);
            table.add(new Label("_______________________________________________________________________"));
            
        }
        f=new Form("Pnaier :  " +toatl + "$");
        f.add(new Label("Pnaier :  " +toatl + "$"));
        f.getStyle().setBgColor(0xFFFFFF);
        Button confirmer = new Button("Confirmer l'achat");
        confirmer.addActionListener((evt) -> {
            try {
                String str =      new PanierService().confimer(14,toatl);
                Dialog.show("Achat Conifmer ", "Votre code de suivi est "+str, "ok" , null);
               // Button commandec = new Button("valider la commande");
                
                //add(confirmer);
               confirmer.addActionListener(new ActionListener() {
                    NexmoClient client = new NexmoClient.Builder()
                            .apiKey("72d3a06e")
                            .apiSecret("24gNidKNwre7btbb")
                            .build();
                    
                    String messageText = "votre commande a ete validée ";
                    TextMessage message = new TextMessage("commande huntkingdom ", "21627183548", messageText);
                    
                    
                    
                    
                    SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);
                    
                    
                    
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                    
                    
                    
                    
                    
                });
                new Produits(res).getF().show();
            } catch (IOException ex) {
              //  Logger.getLogger(Paniers.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NexmoClientException ex) {
                //Logger.getLogger(Paniers.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        Toolbar tb=f.getToolbar();
        tb.addMaterialCommandToSideMenu("Produits ",FontImage.MATERIAL_SHOPPING_BASKET, (e)->{
            new Produits(res).getF().show();
            //   tb.openSideMenu();
        });
        tb.addMaterialCommandToSideMenu("Panier ",FontImage.MATERIAL_SHOPPING_BASKET, (e)->{
            
            
            new Paniers(res).getF().show();           
            //   tb.openSideMenu();
            
        });
        
        /*********************************sms code */
        /**************************************************/
        
        f.add(new Label(""));
        f.add(new Label("_______________________________________________________________________"));
        f.add(confirmer);
        f.add(table);
        f.show();

    }

    public Form getF() {
        return f;
    }

    public void setF(Form f) {
        this.f = f;
    }
   
           
}
