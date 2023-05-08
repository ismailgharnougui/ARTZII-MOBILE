package gui;

import Service.ServiceCategories;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import entites.Categorie;


public class modifiercat  extends Form {
    
    public modifiercat(Form previous, Categorie t) {
        setTitle("Modifier Catégorie");
        setLayout(BoxLayout.y());


        TextField tlib= new TextField(t.getCatlib(), "libelle");
 

        Button btnValider = new Button("Modifier");

        btnValider.addActionListener(e -> {
            
                if ((tlib.getText().length() == 0)  ) {
               Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
          } 
            


else {
                InfiniteProgress ip = new InfiniteProgress();
                final Dialog iDialog = ip.showInfiniteBlocking();
                try {
                    t.getCatid();
                t.setCatlib(tlib.getText());
                  
            
                if (ServiceCategories.getInstance().modifierCatégorie(t.getCatid(), t)) {
    Dialog.show("Success", "categorie a été mise à jour avec succès", new Command("OK"));
} else {
    Dialog.show("ERROR", "Server error", new Command("OK"));
}

                } catch (NumberFormatException ex) {
                    Dialog.show("ERROR", "Le champ Téléphone doit contenir uniquement des chiffres", new Command("OK"));
                } finally {
                    iDialog.dispose();
                }
            }
        });

        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
       // addAll(tnom, tprenom, tfTel, temail, tfdate, tfobjet, tfdescription, tftype, btnValider);
       addAll( tlib,  btnValider);
    }
}


