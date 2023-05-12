/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;

/**
 *
 * @author bersellou
 */
public class Reponse {
    private int idRep;
    private long dateRep;
    private String contenuRep;
    private int idReclamation;

    // Constructor
    public Reponse(int idRep, long dateRep, String contenuRep, int idReclamation) {
        this.idRep = idRep;
        this.dateRep = dateRep;
        this.contenuRep = contenuRep;
        this.idReclamation = idReclamation;
    }

    // Empty constructor
    public Reponse() {}

    public int getIdRep() {
        return idRep;
    }

    public void setIdRep(int idRep) {
        this.idRep = idRep;
    }

    public long getDateRep() {
        return dateRep;
    }

    public void setDateRep(long dateRep) {
        this.dateRep = dateRep;
    }

    public String getContenuRep() {
        return contenuRep;
    }

    public void setContenuRep(String contenuRep) {
        this.contenuRep = contenuRep;
    }

    public int getIdReclamation() {
        return idReclamation;
    }

    public void setIdReclamation(int idReclamation) {
        this.idReclamation = idReclamation;
    }
    
}
