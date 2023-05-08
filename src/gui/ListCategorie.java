package gui;

import Service.ServiceCategories;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import entites.Categorie;

import java.util.ArrayList;
import java.util.List;

public class ListCategorie extends Form {

    public ListCategorie(Form previous) {

        ServiceCategories sp = new ServiceCategories();
        add(new InfiniteProgress());
        Display.getInstance().scheduleBackgroundTask(() -> {

            Display.getInstance().callSerially(() -> {

                removeAll();
                setLayout(BoxLayout.y());
                Button searchButton = new Button();
                FontImage searchIcon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, "Search Icon", 4);
                searchButton.setIcon(searchIcon);

                add(searchButton);
                Style s = UIManager.getInstance().getComponentStyle("Contenu");

                Form hi = new Form("Toolbar", new BoxLayout(BoxLayout.Y_AXIS));
                searchButton.addActionListener(e -> {
                    hi.show();
                });
                Button gui_Button_12 = new Button();
                gui_Button_12.setText("search");
                gui_Button_12.setUIID("Label");
                gui_Button_12.setName("Button_12");
                FontImage.setMaterialIcon(gui_Button_12, FontImage.MATERIAL_CIRCLE);
                TextField searchField = new TextField("", "Toolbar Search"); // <1>
                searchField.getHintLabel().setUIID("Title");
                searchField.setUIID("Title");
                searchField.getAllStyles().setAlignment(Component.LEFT);
                hi.getToolbar().setTitleComponent(searchField);

                ArrayList<Categorie> list1;
                list1 = ServiceCategories.getInstance().affichageCategorie();

                searchField.addDataChangeListener((i1, i2) -> { // <2>
                    String t = searchField.getText();

                    if (t.length() < 1) {
                        for (Component cmp : hi.getContentPane()) {
                            cmp.setHidden(false);
                            cmp.setVisible(true);
                        }
                    } else {
                        t = t.toLowerCase();
                        for (Component cmp : hi.getContentPane()) {
                            String val = null;

                            if (cmp instanceof Label) {
                                val = ((Label) cmp).getText();
                            } else {
                                if (cmp instanceof TextArea) {
                                    val = ((TextArea) cmp).getText();
                                } else {
                                    val = (String) cmp.getPropertyValue("text");
                                }
                            }
                            boolean show = val != null && val.toLowerCase().indexOf(t) > -1;
                            cmp.setHidden(!show); // <3>
                            cmp.setVisible(show);

                        }
                    }
                    hi.getContentPane().animateLayout(250);

                });
                hi.getToolbar().addCommandToRightBar("", searchIcon, (e) -> {
                    searchField.startEditingAsync(); // <4>

                });

                Button refreshButton = new Button();
                FontImage icon1 = FontImage.createMaterial(FontImage.MATERIAL_REFRESH, UIManager.getInstance().getComponentStyle("Button"));
                refreshButton.setIcon(icon1);

                refreshButton.addActionListener(e -> new ListCategorie(previous).show());
                add(refreshButton);

                List<Categorie> listecat = sp.affichageCategorie();
                for (Categorie p : listecat) {
                    MultiButton m = new MultiButton();
                    m.setTextLine1("Libelle: " + p.getCatlib());
                    m.setTextLine2("Catid: " + p.getCatid());

                    add(m);

                    Button btnModifier = new Button();

                    FontImage.setMaterialIcon(btnModifier, FontImage.MATERIAL_EDIT);
                    m.addComponent(BorderLayout.SOUTH, btnModifier);
                    btnModifier.addActionListener(e -> {
                        Categorie r = new Categorie();
                        new modifiercat(this, p).show();

                    });

                    Button btnSupprimer = new Button();
                    Image icon = FontImage.createMaterial(FontImage.MATERIAL_DELETE, "ButtonIcon", 5);
                    btnSupprimer.setIcon(icon);
                    m.addComponent(BorderLayout.WEST, btnSupprimer);

                    btnSupprimer.addActionListener(e -> {
                        Dialog dig = new Dialog("Suppression");
                        if (dig.show("Suppression", "Êtes-vous sûr de vouloir supprimer cet élément ?", "Annuler", "Oui")) {
                            dig.dispose();
                        } else {
                            dig.dispose();
                            int Catid = p.getCatid(); // récupérer l'ID de la réclamation à supprimer
                            System.out.println("ID de la réclamation à supprimer : " + Catid);
                            if (ServiceCategories.getInstance().deleteCategorie(Catid)) {
                                Dialog.show("Parfait ! ", "Catégorie supprimé avec succès", "OK", null);
                            } else {
                                Dialog.show("Parfait", "Catégorie supprimé avec succès", "OK", null);
                            }
                        }
                    });
                }
                revalidate();
            });
        });
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

        Style style = getStyle();
        style.setBgColor(0xCAECF8);
    }
    
}
