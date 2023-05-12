/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;


import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author rassa
 */
public class commande {
     private int id_commande;
    private utilisateur cl;
    private ArrayList<produit> products;
    private double total_commande;
    private String status;

    public commande(int id_commande, String status) {
        this.id_commande = id_commande;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
   
    

     public commande() {
        products = new ArrayList<>();
    }

    public commande(int id_commande, utilisateur cl, double total_commande) {
        this.id_commande = id_commande;
        this.cl = cl;
       this.products = new ArrayList<>();
       
        this.total_commande = total_commande;
    }

    public commande(int id_commande, int id_user, String nom, String prenom, String adresse, float total_commande,String status) {
        this.id_commande=id_commande;
        this.cl = new utilisateur();
        this.cl.setId_user(id_user);
        this.cl.setNom(nom);
        this.cl.setPrenom(prenom);
        this.cl.setAdresse(adresse);
        this.total_commande=total_commande;
        this.status=status;
        
        
     
    }
    public commande(int id_commande, int id_user, String nom, String prenom, String adresse, float total_commande) {
        this.id_commande=id_commande;
        this.cl = new utilisateur();
        this.cl.setId_user(id_user);
        this.cl.setNom(nom);
        this.cl.setPrenom(prenom);
        this.cl.setAdresse(adresse);
        this.total_commande=total_commande;
        
        
        
     
    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public utilisateur getCl() {
        return cl;
    }

    public void setCl(utilisateur cl) {
        this.cl = cl;
    }

    public ArrayList<produit> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<produit> products) {
        this.products = products;
    }


    public double getTotal_commande() {
        return total_commande;
    }

    public void setTotal_commande(double total_commande) {
        this.total_commande = total_commande;
    }

    @Override
    public String toString() {
        return "commande{" + "id_commande=" + id_commande + ", cl=" + cl + ", total_commande=" + total_commande + '}';
    }

    public commande(int id_commande) {
        this.id_commande = id_commande;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id_commande;
        hash = 37 * hash + Objects.hashCode(this.cl);
        hash = 37 * hash + Objects.hashCode(this.products);
       
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.total_commande) ^ (Double.doubleToLongBits(this.total_commande) >>> 32));
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
        final commande other = (commande) obj;
        if (this.id_commande != other.id_commande) {
            return false;
        }
        if (Double.doubleToLongBits(this.total_commande) != Double.doubleToLongBits(other.total_commande)) {
            return false;
        }
       
        if (!Objects.equals(this.cl, other.cl)) {
            return false;
        }
        if (!Objects.equals(this.products, other.products)) {
            return false;
        }
        return true;
    }
    
     public void addproduct(produit product) {
        products.add(product);
        total_commande += product.getPrix();
    }
   
    
}
