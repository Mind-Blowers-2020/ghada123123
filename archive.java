/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author khaoula
 */
public class archive {

    private int id;

    private String nom;

    private String date;

    private int quantite;

    public archive(String nom, int quantite, String date) {
this.nom=nom;
this.quantite=quantite;
this.date=date;   
    }

    public archive() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "commande{" + "id=" + id + ", nom=" + nom + ", date=" + date + ", quantite=" + quantite + '}';
    }

    public archive(int id, String nom, String date, int quantite) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.quantite = quantite;
    }

   
    
}