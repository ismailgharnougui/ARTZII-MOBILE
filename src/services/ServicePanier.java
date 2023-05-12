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
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;
import models.panier;
import models.produit;
import org.json.JSONObject;

/**
 *
 * @author rassa
 */
public class ServicePanier {

    public ArrayList<panier> paniers = new ArrayList();
    panier pan;
    int quantitee;
    float total;
    public static ServicePanier instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    public ArrayList<panier> listCategorie = new ArrayList<>();

    public ServicePanier() {
        req = new ConnectionRequest();
    }
//rendre l constructeur prive w tkhalik testaana 

    public static ServicePanier getInstance() {
        if (instance == null) {
            instance = new ServicePanier();
        }
        return instance;
    }

    /*public boolean addpanier(panier panier, int id_produit) {

        //String description=t.getDescription();
        String url = DB.BASE_URL + "/addcart?iduser=" + panier.getId_user() + "&idprod=" + id_produit + "&qt=" + 1;

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
    }*/
    public String addpanier(
            int idArticle,
            int idClient
    ) {
        try {
            String url = "http://127.0.0.1:8000/AddArticlePanierJSON";

            // Manually create the JSON string
            String json = "{"
                    + "\"idArticle\":" + idArticle + ","
                    + "\"idClient\":" + idClient 
                    + "}";

            ConnectionRequest request = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream input) throws IOException {
                    JSONParser jp = new JSONParser();
                    Map<String, Object> jsonResponse = jp.parseJSON(new InputStreamReader(input, "UTF-8"));
                    String errorMessage = (String) jsonResponse.get("error");
                    if (errorMessage != null) {
                        // Handle error message
                        System.out.println(errorMessage);
                    }
                }

                @Override
                protected void postResponse() {
                    // This will be called after the response was read & parsed.
                }
            };

            request.setPost(true);
            request.setUrl(url);
            request.setHttpMethod("POST");
            System.out.println(json);
            request.setRequestBody(json);
            request.addRequestHeader("Content-Type", "application/json");

            NetworkManager.getInstance().addToQueueAndWait(request);

            return null; // Or return error message as needed
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    public panier parsepanier(String jsonText) {
        float totale = 0;
        panier t = new panier();

        System.out.println("+++++++++++");
        System.out.println(jsonText);
        System.out.println("++++++++++++");

        try {
            paniers = new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String, Object> ReclamationsListJson
                    = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            java.util.List<Map<String, Object>> list = (java.util.List<Map<String, Object>>) ReclamationsListJson.get("root");
            for (Map<String, Object> obj : list) {

                float id = Float.parseFloat(obj.get("id").toString());
                float iduser = Float.parseFloat(obj.get("id_user").toString());
                float quantite = 1;
                float prix = Float.parseFloat(obj.get("article_price").toString());
                float idprod = Float.parseFloat(obj.get("article_id").toString());

                String nomproduit = obj.get("article_name").toString();
                String image = obj.get("article_image").toString();
                String desc = obj.get("article_desc").toString();

                produit p = new produit((int) idprod, nomproduit, prix, image);

                t.addproduct(p);

                t.setQuantite((int) quantite);
                totale += prix * quantite;

                t.setId_panier((int) id);

                t.setId_user((int) iduser);

                paniers.add(t);
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        //  t.setTotal_panier(totale);
        //paniers.add(t);
        return t;
    }

    public boolean increment(int id, int idprod) {
        System.out.println("********");
        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = DB.BASE_URL + "/increment?iduser=" + id + "&idprod=" + idprod;
        req.setUrl(url);
        req.setPost(false);
        System.out.println(url);
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

    public boolean decrement(int id, int idprod) {
        System.out.println("********");
        //String url = Statics.BASE_URL + "create?name=" + t.getName() + "&status=" + t.getStatus();
        String url = DB.BASE_URL + "/decrement?iduser=" + id + "&idprod=" + idprod;
        req.setUrl(url);
        req.setPost(false);
        System.out.println(url);
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

    public panier getpanier(int idUser) {

        String url = DB.BASE_URL + "/getPanierByUser/" + idUser;
        req.setUrl(url);
        req.setPost(false);
        req.addRequestHeader("accept", "application/json");
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                byte[] responseData = req.getResponseData();
                if (responseData != null) {
                    pan = parsepanier(new String(responseData));
                    req.removeResponseListener(this);
                } else {
                    System.out.println("Response data is null");
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

        return pan;
    }

    public boolean deletepanier(int id) {
        System.out.println("********");
        String url = DB.BASE_URL + "/deletePanier/" + id;
        req.setUrl(url);
        req.setPost(false);
        System.out.println(url);
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
}
