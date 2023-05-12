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
public class utilisateur {
    private int id_user;
    private String password;
    private String mail;
    private String nom ; 
    private String prenom;
    private String adresse;
    private String numero;

    public utilisateur(int id_user, String password, String mail, String nom, String prenom, String adresse, String role, String cin, String numero) {
        this.id_user = id_user;
        this.password = password;
        this.mail = mail;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.role = role;
        this.cin = cin;
        this.numero = numero;
    }

    public utilisateur() {
    }

    @Override
    public String toString() {
        return "utilisateur{" + "id_user=" + id_user + ", password=" + password + ", mail=" + mail + ", nom=" + nom + ", prenom=" + prenom + ", adresse=" + adresse + ", role=" + role + ", cin=" + cin + ", numero=" + numero + '}';
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    
    private String role;
    private String cin;
   

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + this.id_user;
        hash = 61 * hash + Objects.hashCode(this.password);
        hash = 61 * hash + Objects.hashCode(this.mail);
        hash = 61 * hash + Objects.hashCode(this.nom);
        hash = 61 * hash + Objects.hashCode(this.prenom);
        hash = 61 * hash + Objects.hashCode(this.adresse);
        hash = 61 * hash + Objects.hashCode(this.role);
        hash = 61 * hash + Objects.hashCode(this.cin);
        hash = 61 * hash + Objects.hashCode(this.numero);
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
        final utilisateur other = (utilisateur) obj;
        if (this.id_user != other.id_user) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.mail, other.mail)) {
            return false;
        }
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if (!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        if (!Objects.equals(this.adresse, other.adresse)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.cin, other.cin)) {
            return false;
        }
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        return true;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    
}
