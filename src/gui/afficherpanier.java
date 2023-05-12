/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import java.util.ArrayList;
import models.panier;
import models.produit;
import services.ServicePanier;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.FlowLayout;
import models.User;
import services.UserServices;

public class afficherpanier extends BaseForm {

    Form current;
    ServicePanier sp = new ServicePanier();

    public afficherpanier(Resources res , User u) {

        super("Panier", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Panier");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
        tb.addSearchCommand(e -> {
        });

        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        EncodedImage basketIconPlaceHolder = EncodedImage.createFromImage(Image.createImage(50, 50, 0x000000), true);
        URLImage basketIcon = URLImage.createToStorage(basketIconPlaceHolder, "lowerArrow.jpg", "https://cdn-icons-png.flaticon.com/512/3142/3142740.png");
        Label basketImage = new Label(basketIcon);

        Image shoppingCartIcon = res.getImage("timeline-background.jpg");
            
        addTab(swipe, shoppingCartIcon, spacer1, "  ", "", " ");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for (int iter = 0; iter < rbs.length; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }

        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if (!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });

        Component.setSameSize(radioContainer, spacer1);
        add(LayeredLayout.encloseIn(swipe, radioContainer));

        ButtonGroup barGroup = new ButtonGroup();
        Container co = new Container(BoxLayout.xCenter());
        // ArrayList <panier> panier = new ArrayList();

        //panier panier = ServicePanier.getInstance().getpanier(1);
        panier panier = sp.getpanier(UserServices.getConnectedUser());

        //ArrayList<produit> prod=panier.getProducts();
        ArrayList<produit> prod = panier.getProducts();
        //float total=ServicePanier.getInstance().gettotal(1);
        double total = 0;

        for (produit product : panier.getProducts()) {
            total += product.getPrix();
        }

        Button commande = new Button("commander");
        commande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new addcommande(res , u).show();
            }
        });

        add(commande);
        int count = 0;

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(this.getWidth(), this.getWidth() / 2, 0xffff0000), true);

        // Prepare placeholder images for the up and down images
        EncodedImage upPlaceholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0x000000), true);
        EncodedImage downPlaceholder = EncodedImage.createFromImage(Image.createImage(50, 50, 0x000000), true);

        // Load the up and down images from the URLs
        //
        //URLImage upImage = URLImage.createToStorage(upPlaceholder, "up" + timestamp + ".jpg", "https://www.pngkey.com/png/detail/350-3501027_sort-up-font-awesome-arrow-up-png-icon.png");
        URLImage modify = URLImage.createToStorage(downPlaceholder, "modify.png", "https://static.thenounproject.com/png/3082134-200.png");
        URLImage delete = URLImage.createToStorage(downPlaceholder, "delete.png", "https://cdn-icons-png.flaticon.com/512/3687/3687412.png");

        for (produit product : prod) {
            Container productContainer = new Container(new BorderLayout());
            productContainer.getAllStyles().setBgTransparency(255);
            productContainer.getAllStyles().setBgColor(count % 2 == 0 ? 0xf6f6f6 : 0xffffff); // alternating background color
            productContainer.getAllStyles().setPadding(20, 20, 20, 20);
            productContainer.getAllStyles().setMargin(10, 10, 10, 10);

            long timestamp = System.currentTimeMillis();
            URLImage urlImage = URLImage.createToStorage(placeholder, "imageUrl3" + timestamp, "http://127.0.0.1:8000/uploads/"+product.getImage());

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

            // Add the buttons
            Button deleteButton = new Button("delete ", FontImage.createMaterial(FontImage.MATERIAL_DELETE, "ButtonIcon", 5)); // use material icon

            deleteButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (Dialog.show("Confirmation", "Voulez vous supprimer cet article ?", "Oui", "Annuler")) {

                        ServicePanier.getInstance().deletepanier(panier.getId_panier());
                        // Success message
                        Dialog.show("Success", "Article deleted successfully", "OK", null);
                        new afficherpanier(res, u).show();

                    }

                }
            });

            Container southContainer = new Container(new BorderLayout());
            Container eastInSouthContainer = new Container(new FlowLayout(Component.RIGHT));
            eastInSouthContainer.add(deleteButton);
            southContainer.add(BorderLayout.EAST, eastInSouthContainer);
            productContainer.add(BorderLayout.SOUTH, southContainer);

            Container priceContainer = new Container(new BorderLayout());
            priceContainer.add(BorderLayout.EAST, descriptionAndPriceContainer);

            this.add(productContainer);
            this.add(priceContainer);

            count++;
        }

       
        Label tot = new Label("Total: " + total + " TND");
        tot.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM));
        tot.getAllStyles().setFgColor(0x00FF00); // set the foreground color to green
        tot.getAllStyles().setAlignment(Component.BOTTOM | Component.RIGHT); // align the label to the bottom right
        add(tot);

    }

    private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if (img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
        heartStyle.setFgColor(0xff2d55);
        FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");

        Container page1
                = LayeredLayout.encloseIn(
                        image,
                        overlay,
                        BorderLayout.south(
                                BoxLayout.encloseY(
                                        new SpanLabel(text, "LargeWhiteText"),
                                        spacer
                                )
                        )
                );

        swipe.addTab("", page1);
    }

}
