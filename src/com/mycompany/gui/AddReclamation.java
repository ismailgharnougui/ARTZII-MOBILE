/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
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
import com.mycompany.entities.Reclamation;
import com.mycompany.services.ServiceReclamation;
import com.mycompany.services.ServiceReponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author achra
 */
public class AddReclamation extends BaseForm {

                       Reclamation fi = new Reclamation();


    public AddReclamation(Resources res,Form previous) {
        super("Ajouter Reclamation", BoxLayout.y());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajouter Reclamation");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        
        tb.addSearchCommand(e -> {});
        
        
        Image img = res.getImage("profile-background.jpg");
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
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
                              
        
        TextComponent description= new TextComponent().label("Description :");
        add(description);
        
        
        
        TextComponent objet= new TextComponent().label("Objet :");
        add(objet);
        
        TextComponent typeR= new TextComponent().label("Type :");
        add(typeR);
        



                





         
        Button Ajouter = new Button("Ajouter");
        //Ajouter.setUIID("Button");
        //Ajouter.getAllStyles().setBgColor(0x2196F3);
        Ajouter.addActionListener((evt) -> {
                if (description.getText().equals("") || objet.getText().equals("")|| typeR.getText().equals(""))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
           

//            fi.setEtat(nom.getText());
            fi.setDescription(description.getText());
            fi.setObjet(objet.getText());
            fi.setTypeR(typeR.getText());


            if(ServiceReclamation.getinstance().addReclamations(fi)==true){
               Dialog.show("Ereur","Ereur",new Command("OK"));
                    }else {
                Dialog.show("Success","Reclamation Ajouter avec success",new Command("OK"));
                            new AllReclamation(res).show();
                try {
                          ServiceReclamation.getinstance().sendMail(fi, "mahdi.mokrani1@esprit.tn");
                      } catch (Exception ex) {
                          Logger.getLogger(RepondreReclamation.class.getName()).log(Level.SEVERE, null, ex); 
                      }

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
