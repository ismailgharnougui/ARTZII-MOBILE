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
import utils.UserSession;
import utils.Mailling;
import java.util.Random;

/**
 * Account activation UI
 *
 * @author Shai Almog
 */
public class ActivateForm extends BaseForm {

    Mailling m = new Mailling();
    UserServices us;

    public Integer rondomcode() {
        Random rand = new Random();
        int randomcode = rand.nextInt(999999);
        return randomcode;
    }
    private int code = rondomcode();

    public int getCode() {
        return code;
    }

    public void setCode() {
        this.code = rondomcode();
    }
    private int codesaved;

    public ActivateForm(Resources res) {
        super(new BorderLayout());
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        setUIID("Activate");
        this.us = UserServices.getInstance();
        add(BorderLayout.NORTH,
                BoxLayout.encloseY(
                        new Label(res.getImage("smily.png"), "LogoLabel"),
                        new Label("Awsome Thanks!", "LogoLabel")
                )
        );
        TextField email = new TextField("", "Enter E-mail", 20, TextField.EMAILADDR);
        TextField code = new TextField("", "Enter Code", 20, TextField.ANY);
        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD);
        TextField passwordconfirm = new TextField("", "Confirmer mot de passe", 20, TextField.PASSWORD);

        Button signUp = new Button("Reset password");
        Button resend = new Button("Get Code");
        resend.setUIID("CenterLink");
        Label alreadHaveAnAccount = new Label("Already have an account?");
        Button signIn = new Button("Sign In");
        signIn.addActionListener(e -> previous.showBack());
        signIn.setUIID("CenterLink");

        Container content = BoxLayout.encloseY(
                new FloatingHint(email),
                createLineSeparator(),
                new FloatingHint(code),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                new FloatingHint(passwordconfirm),
                createLineSeparator(),
                resend,
                signUp,
                FlowLayout.encloseCenter(alreadHaveAnAccount, signIn)
        );
        content.setScrollableY(true);
        add(BorderLayout.SOUTH, content);
        resend.requestFocus();

        resend.addActionListener(e -> {
            if (email.getText().isEmpty()) {
                Dialog.show("Alert", "insert your e-mail", new Command("OK"));
            } else {
                if (us.checkemail(email.getText())) {
                    if (m.sendemail(email.getText(), getCode())) {
                        Dialog.show("notification", "Check inbox", new Command("OK"));
                    }
                } else {
                    Dialog.show("notification", "email incorrecte", new Command("OK"));
                }

            }

        });
        signUp.addActionListener(e -> {
            if (email.getText().isEmpty()) {
                Dialog.show("Alert", "insert your e-mail to get code", new Command("OK"));
            } else if (code.getText().isEmpty()) {
                Dialog.show("Alert", "insert your code", new Command("OK"));
            } else if (password.getText().isEmpty() && passwordconfirm.getText().isEmpty()) {
                Dialog.show("Alert", "inserer mot de passe", new Command("OK"));
            } else if (getCode() == Integer.parseInt(code.getText())) {
                if (password.getText().equals(passwordconfirm.getText())) {
                    if (us.updatepassword(email.getText(), passwordconfirm.getText())) {
                        Dialog.show("Alert", "Mot de passe a été modifier", new Command("OK"));
                        new SignInForm(res).show();
                    }

                } else {

                    Dialog.show("Alert", "Mot de passe n'a pas été modifier", new Command("OK"));
                }

            } else {
                Dialog.show("Alert", "code incorrecte", new Command("OK"));
            }

        });

    }
}
