package gui;

import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;



public class home extends Form {

    private static final char ADD_ICON_NAME = FontImage.MATERIAL_ADD_CIRCLE_OUTLINE;
    private static final char LIST_ICON_NAME = FontImage.MATERIAL_LIST_ALT;
    
    private static final String ADD_BUTTON_TEXTcat = "Ajouter une Catégorie";
    private static final String LIST_BUTTON_TEXTcat = "Afficher Les Catégories";

    public home () {
        setTitle("Menu");
        setLayout(BoxLayout.y());

        add(new Label("Choisir une  option : "));


    
     
        Button addButtonCatg = new Button("");
        addButtonCatg.setIcon(FontImage.createMaterial(ADD_ICON_NAME, UIManager.getInstance().getComponentStyle("Button")));
        addButtonCatg.setText(ADD_BUTTON_TEXTcat);
        addButtonCatg.addActionListener(e -> new addCategorie(this).show());
        
          Button listButtonCat = new Button("");
        listButtonCat.setIcon(FontImage.createMaterial(LIST_ICON_NAME, UIManager.getInstance().getComponentStyle("Button")));
        listButtonCat.setText(LIST_BUTTON_TEXTcat);
     listButtonCat.addActionListener(e -> new ListCategorie(this).show());
     

    
        addAll(addButtonCatg,listButtonCat);
    }
}