package Service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entites.Categorie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceCategories {

    public static ServiceCategories instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<Categorie> listCategorie = new ArrayList<>();

    public ServiceCategories() {

        req = new ConnectionRequest();
    }

    public static ServiceCategories getInstance() {
        if (instance == null) {
            instance = new ServiceCategories();
        }
        return instance;
    }

    public boolean addcategorieForm(Categorie c) {

        String libellé = c.getCatlib();
        String url = "http://127.0.0.1:8000/" + "addCategorieJSON?Libelle=" + libellé;
                
                
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    
     public ArrayList<Categorie> affichageCategorie() {

        ArrayList<Categorie> result = new ArrayList<>();
        String url = "http://127.0.0.1:8000/" + "displayjsonCategorie";
        req.setUrl(url);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                JSONParser jsonp;
                jsonp = new JSONParser();

                try {
                    Map<String, Object> mapR = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String, Object>> ListOfMaps = (List<Map<String, Object>>) mapR.get("root");
                    System.out.println(mapR);
                    for (Map<String, Object> obj : ListOfMaps) {
                        System.out.println(obj);
                        Categorie c = new Categorie();
                        
                 float catid = Float.parseFloat(obj.get("catid").toString());
                String catlib = obj.get("catlib").toString();

                         c.setCatid((int)catid);
                         c.setCatlib(catlib);
                        
                        result.add(c);
                        System.out.println(c.getCatlib());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }
    
   
      public boolean modifierCatégorie(int Catid, Categorie t) {
    String url = "http://127.0.0.1:8000/updateCategoriejson/"+Catid+"?Libelle="+t.getCatlib();       

    ConnectionRequest req = new ConnectionRequest();
    req.setUrl(url);
   

    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            resultOK = req.getResponseCode() == 200;
            req.removeResponseListener(this);
        }
    });

    NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOK;
}
      
           public boolean deleteCategorie(int Catid) {
    String url = "http://127.0.0.1:8000/deletejsoncat/"+Catid;
    System.out.println(url);
    req.setUrl(url);
     req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                    
                    req.removeResponseCodeListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOK;
    }
    
    
}
