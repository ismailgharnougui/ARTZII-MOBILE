/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextComponent;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import models.User;
import models.commande;
import services.*;

/**
 *
 * @author achra
 */
public class addcommande extends BaseForm {

    commande c = new commande();

    public addcommande(Resources res , User u) {
        super("Ajouter commande", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter commande");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);

        tb.addSearchCommand(e -> {
        });

        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);

        Label facebook = new Label("786 followers", res.getImage("facebook-logo.png"), "BottomPad");
        Label twitter = new Label("486 followers", res.getImage("twitter-logo.png"), "BottomPad");
        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(2,
                                facebook, twitter
                        )
                )
        ));

//        TextComponent nom= new TextComponent().label("Nom Reclamation:");
//        add(nom);
        TextComponent nom = new TextComponent().label("nom :");
        add(nom);
        TextComponent prenom = new TextComponent().label("prenom :");
        add(prenom);
        TextComponent adresse = new TextComponent().label("adresse :");
        add(adresse);

        Button Ajouter = new Button("Ajouter");
        //Ajouter.setUIID("Button");
        //Ajouter.getAllStyles().setBgColor(0x2196F3);
        Ajouter.addActionListener((evt) -> {
            if (nom.getText().equals("") || (prenom.getText().equals("")) || (adresse.getText().equals(""))) {
                Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
            } else {

                if (ServiceCommande.getInstance().addcommande(1, nom.getText(), prenom.getText(), adresse.getText()) == false) {
                    Dialog.show("Ereur", "Ereur", new Command("OK"));
                } else {
                    Dialog.show("Success", "Commande Ajouter avec success", new Command("OK"));
                    new afficherpanier(res , u).show();

                }

            }
        });

        addStringValue("", FlowLayout.encloseRightMiddle(Ajouter));

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }
}
