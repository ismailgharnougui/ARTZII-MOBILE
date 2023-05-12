package gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import services.UserServices;


public class SignUpForm extends BaseForm {

    UserServices us;

    public SignUpForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("SignIn");
        this.us = UserServices.getInstance();
        TextField email = new TextField("", "E-Mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD);
        TextField confirm_password = new TextField("", "Confirm Password", 20, TextField.PASSWORD);
        TextField nom = new TextField("", "Nom", 20, TextField.ANY);
        TextField prenom = new TextField("", "Prenom", 20, TextField.ANY);
        TextField tel = new TextField("", "GSM", 20, TextField.ANY);
        TextField cin = new TextField("", "CIN", 20, TextField.ANY);
        TextField adresse = new TextField("", "Adresse", 20, TextField.ANY);

        Button next = new Button("Next");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("Link");
        Label alreadHaveAnAccount = new Label("Already have an account?");

        Container content = BoxLayout.encloseY(
                new FloatingHint(nom),
                createLineSeparator(),
                 new FloatingHint(prenom),
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(password),
                
                createLineSeparator(),
                new FloatingHint(tel),
                createLineSeparator(),
                new FloatingHint(cin),
                createLineSeparator(),
                new FloatingHint(adresse),
                createLineSeparator()
        );

        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, BoxLayout.encloseY(
                next,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        ));

        next.addActionListener(e -> {
            if ((password.getText().length() == 0) || (email.getText().length() == 0) || (cin.getText().length() == 0) || (nom.getText().length() == 0) || (prenom.getText().length() == 0) || (tel.getText().length() == 0) || (email.getText().length() == 0)) {
                Dialog.show("Alert", "Please fill all fields", new Command("OK"));
            } else {
                System.err.println(email.getText() + password.getText());
                us.RegisterAction(email.getText(), password.getText(),nom.getText(), prenom.getText(), tel.getText(),cin.getText(), adresse.getText());
                new SignInForm(res).show();

            }

        });
    }

}
