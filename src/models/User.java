/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.Random;

//import org.controlsfx.control.Notifications;
//import services.Mailling;
//import services.UserService;
/**
 *
 * @author mega pc
 */
public class User {

    private int id;
    private String nom,prenom,mail,cin,adresse,password , gravatar;
    private int numero;
    public User(int id, String nom, String prenom, String mail, String cin, String adresse, int numero) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.cin = cin;
        this.adresse = adresse;
        this.numero = numero;
    }

    public String getGravatar() {
        return gravatar;
    }

    public void setGravatar(String gravatar) {
        this.gravatar = gravatar;
    }
    
    

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    

    public User() {
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getCin() {
        return cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public int getNumero() {
        return numero;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", mail=" + mail + ", cin=" + cin + ", adresse=" + adresse + ", password=" + password + ", gravatar=" + gravatar + ", numero=" + numero + '}';
    }

    

    public Integer rondomcode() {
        Random rand = new Random();
        int randomcode = rand.nextInt(999999);
        return randomcode;
    }

}
