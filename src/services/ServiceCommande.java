/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;
import DB.DB;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import static com.codename1.io.Log.p;
import static com.codename1.io.Log.p;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import models.panier;
import models.*;

/**
 *
 * @author rassa
 */
public class ServiceCommande {
    
    public  ArrayList<panier> paniers =new ArrayList();
    panier pan;
    int quantitee;
    float total;
      public static ServiceCommande instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
  

    public ServiceCommande() {
        req = new ConnectionRequest();
    }
//rendre l constructeur prive w tkhalik testaana 
    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }
    
    
    
    public boolean addcommande( int iduser , String nom,String prenom,String adresse) {

      //String description=t.getDescription();
        
       
         String url =DB.BASE_URL+"/addcommande?iduser="+iduser+"&nom="+nom+"&prenom="+prenom+"&adresse="+adresse;

        req.setUrl(url);
        req.setPost(false);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK type ya true ya false
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
