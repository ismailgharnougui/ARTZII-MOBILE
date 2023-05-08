package gui;

import Service.ServiceCategories;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BoxLayout;
import entites.Categorie;



public class addCategorie extends Form {

    public addCategorie(Form previous) {
           getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        setTitle("Ajouter une nouvelle catégorie");
        setLayout(BoxLayout.y());

        TextField tfcat = new TextField("", "Libellé");

        Button btnAdd = new Button("Ajouter");
        // Add action listener to button
        btnAdd.addActionListener(evt -> {
            if (tfcat.getText().length() == 0) {
                Dialog.show("Alerte !", "-- Veuillez entrer une catégorie --", new Command("OK"));

            } else {
                try {
                    Categorie cat = new Categorie(
                            tfcat.getText()
                    );

                    if (ServiceCategories.getInstance().addcategorieForm(cat)) {
                        Dialog.show("Parfait !", "Catégorie ajoutée avec succès", new Command("OK"));
                    } else {
                        Dialog.show("Erreur !", "Une erreur s'est produite lors de l'ajout de la catégorie", new Command("OK"));
                    }
                } catch (NumberFormatException e) {
                    Dialog.show("Erreur !", "Entrée Invalide", new Command("OK"));
                }
            }
        });

        // Add input fields and button to the form
        addAll(tfcat, btnAdd);
    }
}
