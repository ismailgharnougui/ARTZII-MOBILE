/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;
import java.sql.Timestamp;
import java.util.Objects;
/**
 *
 * @author bersellou
 */
public class Reclamation {
    private int id;
    private String typeR;
    private Timestamp dateR;
    private String etat;
    private String description;
    private String objet;
    private int idUser;

    public Reclamation() {
    }

    public Reclamation(int id, String typeR, Timestamp dateR, String etat, String description, String objet, int idUser) {
        this.id = id;
        this.typeR = typeR;
        this.dateR = dateR;
        this.etat = etat;
        this.description = description;
        this.objet = objet;
        this.idUser = idUser;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeR() {
        return typeR;
    }

    public void setTypeR(String typeR) {
        this.typeR = typeR;
    }

    public Timestamp getDateR() {
        return dateR;
    }

    public void setDateR(Timestamp dateR) {
        this.dateR = dateR;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.id;
        hash = 53 * hash + this.idUser;
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
        final Reclamation other = (Reclamation) obj;
        if (this.idUser != other.idUser) {
            return false;
        }
        if (!Objects.equals(this.typeR, other.typeR)) {
            return false;
        }
        if (!Objects.equals(this.etat, other.etat)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.objet, other.objet)) {
            return false;
        }
        return Objects.equals(this.dateR, other.dateR);
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", typeR=" + typeR + ", dateR=" + dateR + ", etat=" + etat + ", description=" + description + ", objet=" + objet + ", idUser=" + idUser + '}';
    }
    
}
