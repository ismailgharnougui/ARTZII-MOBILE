package services;

import models.produit;
import java.io.ByteArrayInputStream;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.io.InputStream;
//import sqlite.UserSession;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ServiceProduit {

    //public ArrayList<Article> users;
    public List<produit> listArticles = new ArrayList<>();
    public static ServiceProduit instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceProduit() {
        req = new ConnectionRequest();
    }

    public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }

    public List getArticles() {

        ConnectionRequest req2 = new ConnectionRequest();
        String url = "http://127.0.0.1:8000/get_articles";
        req2.setUrl(url);
        req2.setPost(false);
        req2.addResponseListener(e -> {
            if (req2.getResponseCode() == 200) {
                String responseBody;
                try {
                    responseBody = new String(req2.getResponseData(), "UTF-8");
                    JSONParser j = new JSONParser();

                    Map<String, Object> responses = j.parseJSON(new InputStreamReader(new ByteArrayInputStream(responseBody.getBytes())));
                    System.out.println("articles here : " + responses.get("root"));
                    List<Map<String, Object>> articles = (List<Map<String, Object>>) responses.get("root");

                    for (Map<String, Object> articleData : articles) {
                        
                        /*
                        int id_produit, String nom_produit, double prix, String descritpion, int id_user, String image
                        */

                        produit product = new produit(
                                (int) (double) articleData.get("id"),
                                (String) articleData.get("name"),
                                (double) articleData.get("price"),
                                (String) articleData.get("description"),
                                "http://127.0.0.1:8000/uploads/" + (String) articleData.get("imageUrl")
                        );
                        listArticles.add(product);
                    }

                } catch (UnsupportedEncodingException ex) {

                } catch (IOException ex) {
                }
                // Parse the JSON response and handle it here
            } else {
                System.out.println("Error: ");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req2);
        return listArticles;

    }

    public ArrayList getArticle(int id) {
        listArticles.clear();
        ArrayList article = new ArrayList<>();
        ConnectionRequest req2 = new ConnectionRequest();
        String url = "http://127.0.0.1:8000/api/mobile/get_one/" + id;
        req2.setUrl(url);
        req2.setPost(false);
        req2.addResponseListener(e -> {
            if (req2.getResponseCode() == 200) {
                String responseBody;
                try {
                    responseBody = new String(req2.getResponseData(), "UTF-8");
                    JSONParser j = new JSONParser();
                    Map<String, Object> response = j.parseJSON(new InputStreamReader(new ByteArrayInputStream(responseBody.getBytes())));

                    String name = (String) response.get("articleName");
                    String surname = (String) response.get("description");
                    String averageRating = (String) response.get("averageRating");

                    double numDouble = (Double) response.get("num");
                    int num = (int) numDouble;
                    String email = (String) response.get("email");
                    String images = (String) response.get("images");
                    String profils = ((Map<String, Object>) response.get("profils")).get("login").toString();

                } catch (UnsupportedEncodingException ex) {

                } catch (IOException ex) {
                }
                // Parse the JSON response and handle it here
            } else {
                System.out.println("Error: ");
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req2);
        return null;
    }

    public String deleteArticle(int id) {
        try {
            String url = "http://127.0.0.1:8000/delete_article/" + id;

            // Manually create the JSON string
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
            request.addRequestHeader("Content-Type", "application/json");

            NetworkManager.getInstance().addToQueueAndWait(request);

            return null; // Or return error message as needed
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    public String addArticle(
            String article_name,
            String description,
            double price,
            double average_rating,
            String image_url,
            String date,
            int state,
            String tags,
            int category_id,
            int seller_id
    ) {
        try {
            String url = "http://127.0.0.1:8000/api/mobile/insert";

            // Manually create the JSON string
            String json = "{"
                    + "\"article_name\":\"" + article_name + "\","
                    + "\"description\":\"" + description + "\","
                    + "\"price\":" + price + ","
                    + "\"average_rating\":" + average_rating + ","
                    + "\"image_url\":\"" + image_url + "\","
                    + "\"date\":\"" + "2023-05-05T12:00:00Z" + "\","
                    + "\"state\":" + state + ","
                    + "\"tags\":\"" + tags + "\","
                    + "\"category_id\":" + category_id + ","
                    + "\"seller_id\":" + seller_id
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
            request.setRequestBody(json);
            request.addRequestHeader("Content-Type", "application/json");

            NetworkManager.getInstance().addToQueueAndWait(request);

            return null; // Or return error message as needed
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }

    public String share(int id) {
        try {
            String url = "http://127.0.0.1:8000/api/mobile/shareOfFacebook/" + id;

            // Manually create the JSON string
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
            request.addRequestHeader("Content-Type", "application/json");

            NetworkManager.getInstance().addToQueueAndWait(request);

            return null; // Or return error message as needed
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred";
        }
    }
}
