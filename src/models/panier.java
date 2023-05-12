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
public class panier {
   
    private int id_user;
    private int id_panier;

    
    private ArrayList<produit> products;
    private double total_panier;
    
    private int quantite=1;
    
    public int getId_panier() {
        return id_panier;
    }

    public void setId_panier(int id_panier) {
        this.id_panier = id_panier;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public double getTotal_panier() {
        return total_panier;
    }

    public void setTotal_panier(double total_panier) {
        this.total_panier = total_panier;
    }
   

    public panier(int id_user) {
        
        this.id_user = id_user;
        this.products = new ArrayList<>();
        
    }
    
     public void addproduct(produit product) {
        products.add(product);
        total_panier += product.getPrix();
    }
    public panier() {
        products = new ArrayList<>();
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    


  

    public ArrayList<produit> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<produit> products) {
        this.products = products;
    }

   /* @Override
    public String toString() {
        return "panier{" +  "id_client=" + id_user + ", products=" + products + ", total_panier=" + total_panier + '}';
    }*/

    @Override
    public String toString() {
        return "panier{" + "id_user=" + id_user + ", id_panier=" + id_panier + ", products=" + products + ", total_panier=" + total_panier + ", quantite=" + quantite + '}';
    }

   

    
    @Override
    public int hashCode() {
        int hash = 3;
        
        hash = 71 * hash + this.id_user;
        hash = 71 * hash + Objects.hashCode(this.products);
       
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
        final panier other = (panier) obj;
       
        if (this.id_user != other.id_user) {
            return false;
        }
        
        if (!Objects.equals(this.products, other.products)) {
            return false;
        }
        return true;
    }
    
}
