/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GuiForm;

import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import Entity.Produit;
import Services.PanierService;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class Produits {
    Form f ; 
    
    public Produits ( Resources res){
        
        f=new Form("Produit :  "); 
         f.add(new Label("Produit :  "));
        f.getStyle().setBgColor(0xFFFFFF);
              Container table  = new Container(BoxLayout.y());
        ArrayList<Produit> seances = new ArrayList<>(); 
              seances = new PanierService().getProduit();
      for (Produit s : seances){
      Container content = new Container(BoxLayout.x() );
      Container detail = new Container(BoxLayout.y());
     Container detail2 = new Container(BoxLayout.y());
        Button add = new Button("Ajouter au pnaier");
              add.addActionListener((evt) -> {
                 new PanierService().addPanier(s.getNom(), s.getPrix().toString() , 1);
             Dialog.show("Produit ajouté ", s.getNom()+" est ajouté au panier", "ok" , null);

          });

      
          Label nom = new Label("Nom :"+s.getNom()); 
          Label prix = new Label("Prix :"+s.getPrix()); 
                                com.codename1.ui.Image Image = res.getImage(s.getNom()+".jpg");

           // com.codename1.ui.Image Image = res.getImage("3.png");
            com.codename1.ui.Image i =    Image.scaled(125, 125);

          
            
 

          detail.add(nom);
                   detail.add(prix);
           content.add(i);
           content.add(detail);
           content.add(add);
           table.add(content);
            table.add(new Label("_______________________________________________________________________"));

      }  
      Toolbar tb=f.getToolbar();
            tb.addMaterialCommandToSideMenu("Produits ",FontImage.MATERIAL_SHOPPING_BASKET, (e)->{
                new Produits(res).getF().show();
        //   tb.openSideMenu();
        });
             tb.addMaterialCommandToSideMenu("Panier ",FontImage.MATERIAL_SHOPPING_BASKET, (e)->{
                new Paniers(res).getF().show();
        //   tb.openSideMenu();
        });

f.add(new Label(""));
 f.add(new Label("_______________________________________________________________________"));
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
