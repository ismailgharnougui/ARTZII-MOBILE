/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Objects;

/**
 *
 * @author rassa
 */
public class produit {

    private int id_produit;
    private String nom_produit;
    private double prix;
    private String descritpion;
    private int id_user;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public produit(int id_produit, String nom_produit, double prix, String descritpion, int id_user, String image) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.descritpion = descritpion;
        this.id_user = id_user;
        this.image = image;
    }

    public produit(int id_produit, String nom_produit, double prix, String descritpion, String image) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.descritpion = descritpion;
        this.image = image;
    }

    public produit(String nom_produit, double prix, String descritpion, int id_user, String image) {
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.descritpion = descritpion;
        this.id_user = id_user;
        this.image = image;
    }

    public produit() {
    }

    public produit(int id_produit, String nom_produit, double prix, String descritpion, int id_user) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.descritpion = descritpion;
        this.id_user = id_user;
    }

    public produit(int id_produit, String nom_produit, double prix) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
    }

    public produit(int id_produit, String nom_produit, double prix, String image) {
        this.id_produit = id_produit;
        this.nom_produit = nom_produit;
        this.prix = prix;
        this.image = image;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getDescritpion() {
        return descritpion;
    }

    public void setDescritpion(String descritpion) {
        this.descritpion = descritpion;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "produit{" + "id_produit=" + id_produit + ", nom_produit=" + nom_produit + ", prix=" + prix + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id_produit;
        hash = 97 * hash + Objects.hashCode(this.nom_produit);
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.prix) ^ (Double.doubleToLongBits(this.prix) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final produit other = (produit) obj;
        if (this.id_produit != other.id_produit) {
            return false;
        }
        if (Double.doubleToLongBits(this.prix) != Double.doubleToLongBits(other.prix)) {
            return false;
        }
        if (!Objects.equals(this.nom_produit, other.nom_produit)) {
            return false;
        }
        return true;
    }

}
