/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import Entity.Produit;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ASUS
 */
public class PanierService {
            ArrayList<Produit> ListSeance = new ArrayList<>();
    private ArrayList<Produit> seances;

     public   String str ;
    public ArrayList<Produit> parseListTaskJson(String json) {


        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            for (Map<String, Object> obj : list) {
                Produit e = new Produit();
                float id = Float.parseFloat(obj.get("idP").toString());
                e.setId((int) id);
              e.setImage(obj.get("image").toString());
              e.setNom(obj.get("nomprod").toString());
              e.setPrix(Float.parseFloat(obj.get("prix").toString()));
                System.out.println(e.getImage());
                ListSeance.add(e);
            }
        } catch (IOException ex) {
        }
        System.out.println(ListSeance);
        return ListSeance;

    }
        public ArrayList<Produit> parsePnaier(String json) {


        try {
            JSONParser j = new JSONParser();
            Map<String, Object> tasks = j.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasks.get("root");
            for (Map<String, Object> obj : list) {
                Produit e = new Produit();
                float id = Float.parseFloat(obj.get("id").toString());
                e.setId((int) id);
              e.setNom(obj.get("nom").toString());
              e.setPrix(Float.parseFloat(obj.get("prix").toString()));
                ListSeance.add(e);
            }
        } catch (IOException ex) {
        }
        System.out.println(ListSeance);
        return ListSeance;

    }

        public ArrayList<Produit> getProduit() {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/backt/web/app_dev.php/produit");
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                PanierService ser = new PanierService();
                seances = ser.parseListTaskJson(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return seances;
    }
   public void addPanier(String nom , String prix , int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/backt/web/app_dev.php/panier/"+nom+"/"+prix+"/"+id);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
   public ArrayList<Produit> getPanier(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/backt/web/app_dev.php/list/"+id);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                PanierService ser = new PanierService();
                seances = ser.parsePnaier(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return seances;
           }
      public void deletePanier(int id) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/backt/web/app_dev.php/delete/"+id);
        NetworkManager.getInstance().addToQueueAndWait(con);
    }
          public String confimer(int id , float total) {
           
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/backt/web/app_dev.php/confirmer/" + id + "/" + total;// création de l'URL
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
          str  = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
        return str ;
    }


}
