/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Reclamation;
import com.mycompany.entities.Reponse;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Obers
 */
public class ServiceReponse {

    ConnectionRequest req;
    public ArrayList<Reponse> reponses;

//    public ArrayList<Reclamation> reclamtions;
    public boolean resultOk;
    //2  creer un attribut de type de la classe en question (static)
    public static ServiceReponse instance = null;

    //Singleton => Design Pattern qui permet de creer une seule instance d'un objet 
    //1 rendre le constructeur private
    private ServiceReponse() {
        req = new ConnectionRequest();
    }

    //3 la methode qu'elle va ramplacer le constructeur 
    public static ServiceReponse getinstance() {
        if (instance == null) {
            instance = new ServiceReponse();
        }
        return instance;
    }

//        
//        
//        
//    }
    //methode d'ajout
    public boolean addReponses(Reponse e) {
        String contenu = e.getContenuRep();
        int idRec = e.getIdReclamation();

//        String url = Statics.URL+"reclamations/createReclamations/"+e.getEtat()+"/"+e.getDescription();
        String url = "http://127.0.0.1:8000/reponse/createReponse/" + idRec + "/" + contenu + "/";
      
        req.setUrl(url);
        //GET =>
        req.setPost(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200; //si le code return 200 
                //
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOk;

    }

    public ArrayList<Reponse> parseReponses(String jsonText) {
        try {
            reponses = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReponsesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) ReponsesListJson.get("root");
            for (Map<String, Object> obj : list) {
                Reponse t = new Reponse();
                float id = Float.parseFloat(obj.get("idReponse").toString());
                t.setIdRep((int) id);
                // t.setStatus(((int) Float.parseFloat(obj.get("status").toString())));
                if (obj.get("contenuRep") == null) {
                    t.setContenuRep("null");
                } else {
                    t.setContenuRep(obj.get("contenuRep").toString());
                }


                reponses.add(t);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return reponses;
    }

    public ArrayList<Reponse> getAllReponses() {
//          String url = Statics.BASE_URL+"/mobile/listReclamtion/";
        String url = "http://127.0.0.1:8000/reponse/mobile/AllReponse/";
        req.setUrl(url);
        req.setPost(false);
        req.addRequestHeader("accept", "application/json");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                byte[] responseData = req.getResponseData();
                if (responseData != null) {
                    reponses = parseReponses(new String(responseData));
                    req.removeResponseListener(this);
//            String response = new String(responseData);
//            System.out.println(response);
                } else {
                    System.out.println("Response data is null");
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return reponses;
    }

    public boolean deleteReponse(int id) {
        String url = Statics.URL + "/reponse/mobile/deleteReponse/" + id + "/";
        ConnectionRequest request = new ConnectionRequest(url);
        request.setHttpMethod("DELETE");

        request.addResponseListener(e -> {

            resultOk = request.getResponseCode() == 200;

        });

        NetworkManager.getInstance().addToQueue(request);
        return resultOk;

    }

    
   }
