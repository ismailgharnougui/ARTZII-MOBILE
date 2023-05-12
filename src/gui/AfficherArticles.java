package gui;

import com.codename1.components.FloatingActionButton;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.plaf.Style;

import java.util.ArrayList;
import models.produit;
import java.util.List;
import models.User;
import services.ServicePanier;
import services.ServiceProduit;
import services.UserServices;

public class AfficherArticles extends BaseForm {

    private List<produit> products = new ArrayList<>();
    private Resources theme;
    private Image test;
    private Form home;

    public AfficherArticles(Resources theme) {

        this.theme = theme;
        this.createHomeForm();

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Panier");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(theme);
        tb.addSearchCommand(e -> {
        });
    }
    
       public AfficherArticles(Resources theme , User u) {

        this.theme = theme;
        this.createHomeForm();

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Panier");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(theme);
        tb.addSearchCommand(e -> {
        });
    }

    public AfficherArticles(List<produit> products, Resources theme) {
        this.products = products;
        this.theme = theme;

        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Panier");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(theme);
        tb.addSearchCommand(e -> {
        });
    }

    public void createHomeForm() {
        ServiceProduit serviceProduit = ServiceProduit.getInstance();
        products = serviceProduit.getArticles();

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getWidth() / 2, 0xffff0000), true);

        // Prepare placeholder images for the up and down images
        EncodedImage upPlaceholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0x000000), true);
        EncodedImage downPlaceholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0x000000), true);
        EncodedImage productPlaceHolder = EncodedImage.createFromImage(Image.createImage(50, 50, 0x000000), true);

        // Load the up and down images from the URLs
        //
        //URLImage upImage = URLImage.createToStorage(upPlaceholder, "up" + timestamp + ".jpg", "https://www.pngkey.com/png/detail/350-3501027_sort-up-font-awesome-arrow-up-png-icon.png");
        URLImage modify = URLImage.createToStorage(downPlaceholder, "modify.png", "https://static.thenounproject.com/png/3082134-200.png");
        URLImage delete = URLImage.createToStorage(downPlaceholder, "delete.png", "https://cdn-icons-png.flaticon.com/512/3687/3687412.png");

        int count = 0; // for alternating background color
        for (produit product : products) {
            Container productContainer = new Container(new BorderLayout());
            productContainer.getAllStyles().setBgTransparency(255);
            productContainer.getAllStyles().setBgColor(count % 2 == 0 ? 0xf6f6f6 : 0xffffff); // alternating background color
            productContainer.getAllStyles().setPadding(20, 20, 20, 20);
            productContainer.getAllStyles().setMargin(10, 10, 10, 10);

            long timestamp = System.currentTimeMillis();
            URLImage urlImage = URLImage.createToStorage(placeholder, product.getImage() + timestamp, product.getImage());
            System.out.println("http://127.0.0.1:8000/uploads/" + product.getImage());
            // IMAGE PRODUCT
            Label productImage = new Label(urlImage);
            productImage.getAllStyles().setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

            Label productName = new Label(product.getNom_produit());
            productName.getAllStyles().setFgColor(0x000000); // black text
            productName.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM)); // adjust font size
            productContainer.add(BorderLayout.NORTH, FlowLayout.encloseCenterMiddle(productName));

            productContainer.add(BorderLayout.CENTER, FlowLayout.encloseCenterMiddle(productImage));

            // Create a container for description and price, with a BoxLayout along the Y axis (vertical)
            Container descriptionAndPriceContainer = new Container(BoxLayout.y());
            descriptionAndPriceContainer.getAllStyles().setPadding(10, 10, 10, 10);

            Label productDescription = new Label(product.getDescritpion());
            productDescription.getAllStyles().setFgColor(0x000000); // black text
            productDescription.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_SMALL)); // adjust font size
            productDescription.getAllStyles().setAlignment(Label.CENTER); // Center align text
            descriptionAndPriceContainer.add(productDescription);

            // Add label for the price
            Label productPrice = new Label(String.valueOf(product.getPrix() + " TND"));
            productPrice.getAllStyles().setFgColor(0x2E8B57); // green text for price
            productPrice.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM)); // adjust font size
            productPrice.getAllStyles().setAlignment(Label.RIGHT);

            descriptionAndPriceContainer.add(productPrice);

            productContainer.add(BorderLayout.SOUTH, descriptionAndPriceContainer);

            // Add the buttons
            Button basketButton = new Button(FontImage.createMaterial(FontImage.MATERIAL_SHOPPING_CART, "ButtonIcon", 5)); // use material icon
            Button deleteButton = new Button(FontImage.createMaterial(FontImage.MATERIAL_DELETE, "ButtonIcon", 5)); // use material icon
            Button shareButton = new Button(FontImage.createMaterial(FontImage.MATERIAL_SHARE, "ButtonIcon", 5));
            shareButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    // your action here
                }
            });

            deleteButton.addActionListener(e -> {
                if (Dialog.show("Confirm Deletion", "Are you sure you want to delete this article?", "Yes", "No")) {
                    int articleId = product.getId_produit();

                    if (Dialog.show("Confirm Deletion", "Are you sure you want to delete this article?", "Yes", "No")) {
                        ServiceProduit articleService = new ServiceProduit();
                        String response = articleService.deleteArticle(articleId);
                        if (response == null) {

                            Dialog.show("Success", "Article deleted successfully", "OK", null);
                            products.clear();
                            new AfficherArticles(theme).show();
                        } else {

                            Dialog.show("Error", "Failed to delete article: " + response, "OK", null);
                        }
                    }

                }
            });

            basketButton.addActionListener(e -> {

                int articleId = product.getId_produit();
                int userId = UserServices.getConnectedUser();

                ServicePanier sp = new ServicePanier();
                sp.addpanier(articleId, userId);

                Dialog.show("Success", "Article added to basket", "OK", null);
            });

            basketButton.getAllStyles().setAlignment(Label.RIGHT);

            Container buttonContainer = BoxLayout.encloseY(basketButton, deleteButton, shareButton);
            productContainer.add(BorderLayout.EAST, buttonContainer);

            this.add(productContainer);

            count++;
        }

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.bindFabToContainer(this.getContentPane(), Component.BOTTOM | Component.RIGHT, 5);

        fab.addActionListener(e -> {
            // your action here
        });
    }

    public void refreshForm() {
        // Clear the current content of the form
        this.removeAll();

        // Load and display the articles again
    }
}
