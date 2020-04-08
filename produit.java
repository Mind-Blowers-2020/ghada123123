/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author esprit
 */
public class produit {
     private int idP;

    private String nomprod;


private Double  prix;
    private int qt;

    public produit() {
    }

    public int getIdP() {
        return idP;
    }

    public void setIdP(int idP) {
        this.idP = idP;
    }

    public String getNomprod() {
        return nomprod;
    }

    public void setNomprod(String nomprod) {
        this.nomprod = nomprod;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }
    @Override
    public String toString() {
        return "commande{" + "id=" + idP + ", nom=" + nomprod + ", prix=" + prix + ", quantite=" + qt + '}';
    }

    public produit(int id, String nom, Double prix, int quantite) {
        this.idP = id;
        this.nomprod = nom;
         this.prix = prix;
       
        this.qt = quantite;
    }

}
