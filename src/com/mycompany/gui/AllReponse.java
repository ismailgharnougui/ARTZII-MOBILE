/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entities.Reclamation;
import com.mycompany.entities.Reponse;
import com.mycompany.services.ServiceReclamation;
import com.mycompany.services.ServiceReponse;
import java.util.ArrayList;

/**
 *
 * @author Obers
 */
public class AllReponse extends BaseForm {

    Form current;

    public AllReponse(Resources res) {

        super("Reponses", BoxLayout.y());
//        Boolean searchEtat=false;
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Reponses");
        getContentPane().setScrollVisible(false);

        super.addSideMenu(res);
//        tb.addSearchCommand(e -> {
//        });
        // Define the search field
        TextField searchField = new TextField("", "Search...");
        // Define the search command
        

//        tb.addComponentToSideMenu(searchField);
//        tb.addCommandToSideMenu(searchCommand);
//        tb.addSearchCommand(searchCommand);
        Tabs swipe = new Tabs();

        Label spacer1 = new Label();
        addTab(swipe, null , spacer1, "  ", "", " ");

        swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();

        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xFF0000);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xFF0000);
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

//        Button Ajouter = new Button("Ajouter");
//        Ajouter.getUnselectedStyle().setBgColor(0x0000FF); // Bleu foncé
//        Ajouter.getSelectedStyle().setBgColor(0xFF0000); // Bleu foncé (lorsque le bouton est sélectionné)
//        Ajouter.getPressedStyle().setBgColor(0xFF0000); // Bleu foncé (lorsque le bouton est enfoncé)
//        Ajouter.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                new AddReclamation(res, current).show();
//            }
//        });
//        add(Ajouter);

        ButtonGroup barGroup = new ButtonGroup();
        Container co = new Container(BoxLayout.xCenter());;
        ArrayList<Reponse> reponse = new ArrayList();
        reponse = ServiceReponse.getinstance().getAllReponses();

        for (Reponse fi : reponse) {
            Container ct = new Container(BoxLayout.y());
            Label l = new Label("ID : " + fi.getIdRep());
            Label l2 = new Label("Contenu de Reponse: " + fi.getContenuRep());
//            Label l3 = new Label("Description : " + fi.getDescription(), "SmallLabel");
            l2.getAllStyles().setFgColor(0xFF0000);
            ct.add(l);
            ct.add(l2);
//            ct.add(l3);

//            Button Modif = new Button("Modifier");
            Button Supprimer = new Button("Supprimer");
            Supprimer.getAllStyles().setFgColor(0x0000FF);
            Supprimer.getAllStyles().setBgColor(0x0000FF);
            Supprimer.getAllStyles().setBorder(Border.createLineBorder(1, 0x0000FF));

            Supprimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent evt) {
                    if (Dialog.show("Confirmation", "Voulez vous supprimer cett reponse ?", "Oui", "Annuler")) {

                        ServiceReponse.getinstance().deleteReponse(fi.getIdRep());
                        // Success message
                        Dialog.show("Success", "Reponse deleted successfully", "OK", null);
                        new AllReponse(res).show();

                    }

                }
            });
//            ct.add(Modif);
            ct.add(Supprimer);

            Label separator = new Label("", "Separator");
            ct.add(separator);
            add(ct);
        }
    }

   private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
    int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
    if (img != null && img.getHeight() < size) {
        img = img.scaledHeight(size);
    }
    Label likes = new Label(likesStr);
    Style heartStyle = new Style(likes.getUnselectedStyle());
    heartStyle.setFgColor(0xFF0000);
    FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
    likes.setIcon(heartImage);
    likes.setTextPosition(RIGHT);

    Label comments = new Label(commentsStr);
    FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
    if (img != null && img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
        img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
    }
    ScaleImageLabel image = img != null ? new ScaleImageLabel(img) : new ScaleImageLabel();
    image.setUIID("Container");
    image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
    Label overlay = new Label(" ", "ImageOverlay");

    Container page1 = LayeredLayout.encloseIn(
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
