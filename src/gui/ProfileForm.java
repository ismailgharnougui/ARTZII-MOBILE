/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.l10n.L10NManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;

import services.UserServices;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.URLImage;
import utils.UserSession;
import models.User;

/**
 * The user profile form
 *
 * @author Shai Almog
 */
public class ProfileForm extends BaseForm {

    UserServices us;
    String Imagecode;
    String filePath = "";

    public ProfileForm(Resources res, User user) {

        super("Newsfeed", BoxLayout.y());
        this.us = UserServices.getInstance();
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Profile");
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

        facebook.setTextPosition(BOTTOM);
        twitter.setTextPosition(BOTTOM);
        String photoUrl = user.getGravatar();

        // Create a URLImage instance to display the picture
        Image profilePicture = URLImage.createToStorage(
                EncodedImage.createFromImage(Image.createImage(200, 200), false),
                user.getMail(),
                photoUrl,
                URLImage.RESIZE_SCALE_TO_FILL
        );

        // Create the label to display the picture
        Label profilePictureLabel = new Label(profilePicture.scaled(200, -1), "PictureWhiteBackgrond");

        add(LayeredLayout.encloseIn(
                sl,
                BorderLayout.south(
                        GridLayout.encloseIn(3,
                                facebook,
                                FlowLayout.encloseCenter(profilePictureLabel),
                                twitter
                        )
                )
        ));

        EncodedImage enc = (EncodedImage) res.getImage("round-mask.png");
        if (enc != null) {
            Image roundMask = Image.createImage(enc.getWidth(), enc.getHeight(), 0xff000000);
            Graphics gr = roundMask.getGraphics();
            gr.setColor(0xffffff);
            gr.fillArc(0, 0, enc.getWidth(), enc.getWidth(), 0, 360);
            Object mask = roundMask.createMask();
        }

        if (user.getNom() == null || user.getCin() == null || user.getPrenom() == null || Long.toString(user.getNumero()) == null) {
            Dialog.show("Alert", "compléter ton inscription", new Command("OK"));

        }

        TextField prenom = new TextField(user.getPrenom());
        prenom.setUIID("TextFieldBlack");
        addStringValue("Prenom", prenom);

        TextField nom = new TextField(user.getNom());
        nom.setUIID("TextFieldBlack");
        addStringValue("Nom", nom);

        TextField email = new TextField(user.getMail(), "E-Mail", 20, TextField.EMAILADDR);
        email.setUIID("TextFieldBlack");
        addStringValue("E-Mail", email);

        TextField tel = new TextField(Long.toString(user.getNumero()), TextField.PHONENUMBER);
        tel.setUIID("TextFieldBlack");
        addStringValue("tel", tel);

        TextField cin = new TextField(user.getCin());
        cin.setUIID("TextFieldBlack");
        addStringValue("CIN", cin);

        TextField adresse = new TextField(user.getAdresse());
        adresse.setUIID("TextFieldBlack");
        addStringValue("Adresse", adresse);

        Button edit = new Button("Modifier");

        addStringValue("", edit);

        Button supprimer = new Button("Supprimer");

        addStringValue("", supprimer);

        supprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                if (Dialog.show("Confirmer", "Do you want to proceed?", "OK", "Cancel")) {

                    us.deleteUSER(UserSession.instance.getU().getId());
                    UserSession.instance.cleanUserSession();

                    new SignInForm(res).show();

                }
            }
        });
        edit.requestFocus();

        edit.addActionListener((ActionEvent e) -> {

            if (edit.getText().equals("Modifier")) {
                edit.setText("Sauvgarder");
                prenom.setEditable(true);
                nom.setEditable(true);
                email.setEditable(true);
                cin.setEditable(true);
                tel.setEditable(true);
                adresse.setEditable(true);

            } else {

                if (Dialog.show("Confirmer", "Do you want to proceed?", "OK", "Cancel")) {

                    if (!nom.getText().equals("")) {
                        user.setNom(nom.getText());
                    }
                    if (!prenom.getText().equals("")) {
                        user.setPrenom(prenom.getText());

                    }

                    L10NManager l10n = L10NManager.getInstance();

                    if (!tel.getText().equals("")) {
                        user.setNumero(Integer.parseInt(tel.getText()));
                    }

                    if (!email.getText().equals("")) {

                        user.setMail(email.getText());
                    }
                    if (!adresse.getText().equals("")) {
                        user.setAdresse(adresse.getText());
                    }
                    if (!cin.getText().equals("")) {
                        user.setCin(cin.getText());
                    }

                    if (us.updateUser(UserSession.instance.getU())) {

                        new ProfileForm(res, UserSession.instance.getU()).show();
                    }

                } else {

                    new ProfileForm(res, UserSession.instance.getU()).show();
                }
                //User clicks on ok to delete account

                edit.setText("Modifier");
                prenom.setEditable(false);
                nom.setEditable(false);
                email.setEditable(false);
                tel.setEditable(false);
                cin.setEditable(false);
                adresse.setEditable(false);

            }

        });

        prenom.setEditable(false);
        nom.setEditable(false);
        email.setEditable(false);
        tel.setEditable(false);

        cin.setEditable(false);
        adresse.setEditable(false);

    }

    private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s, "PaddedLabel")).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

    private void add2bo(Component k, Component v) {
        add(BorderLayout.west(k).
                add(BorderLayout.CENTER, v));
        add(createLineSeparator(0xeeeeee));
    }

}
