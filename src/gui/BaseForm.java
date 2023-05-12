package gui;

import com.codename1.components.ScaleImageLabel;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import models.User;
import utils.UserSession;

public class BaseForm extends Form {

    public BaseForm() {
    }

    public BaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public BaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public Component createLineSeparator() {
        Label separator = new Label("", "WhiteSeparator");
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    public Component createLineSeparator(int color) {
        Label separator = new Label("", "WhiteSeparator");
        separator.getUnselectedStyle().setBgColor(color);
        separator.getUnselectedStyle().setBgTransparency(255);
        separator.setShowEvenIfBlank(true);
        return separator;
    }

    protected void addSideMenu(Resources res) {
        Toolbar tb = getToolbar();
        Image img = res.getImage("profile-background.jpg");
        if (img.getHeight() > Display.getInstance().getDisplayHeight() / 3) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 3);
        }
        ScaleImageLabel sl = new ScaleImageLabel(img);
        sl.setUIID("BottomPad");
        sl.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        User user = UserSession.instance.getU();
        String photoUrl = user.getGravatar();
        //  Create a URLImage instance to display the picture
        Image profilePicture = URLImage.createToStorage(
                EncodedImage.createFromImage(Image.createImage(300, 300), false),
                user.getMail(),
                photoUrl,
                URLImage.RESIZE_SCALE_TO_FILL
        );
        // Create the label to display the picture
        Label profilePictureLabel = new Label(profilePicture.scaled(200, -1), "PictureWhiteBackgrond");
        tb.addComponentToSideMenu(LayeredLayout.encloseIn(
                sl,
                FlowLayout.encloseCenterBottom(profilePictureLabel)
        ));

        if (UserSession.instance != null) {

            tb.addMaterialCommandToSideMenu("Profile", FontImage.MATERIAL_SETTINGS, e -> new ProfileForm(res, UserSession.instance.getU()).show());
            tb.addMaterialCommandToSideMenu("Naviguer", FontImage.MATERIAL_HOME, e -> new AfficherArticles(res, UserSession.instance.getU()).show());
            tb.addMaterialCommandToSideMenu("Panier", FontImage.MATERIAL_SHOPPING_CART, e -> new afficherpanier(res, UserSession.instance.getU()).show());
            tb.addMaterialCommandToSideMenu("Reclamation", FontImage.MATERIAL_DANGEROUS, e -> new ProfileForm(res, UserSession.instance.getU()).show());

        }

        if (UserSession.instance == null) {
            tb.addMaterialCommandToSideMenu("Sign In", FontImage.MATERIAL_LOGIN, e -> new SignInForm(res).show());
        } else {
            tb.addMaterialCommandToSideMenu("Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> {
                new SignInForm(res).show();
                UserSession.instance.cleanUserSession();
            });
        }

    }
}
