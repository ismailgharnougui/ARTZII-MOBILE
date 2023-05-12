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

public class SignInForm extends BaseForm {

    UserServices us;

    public SignInForm(Resources res) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        Toolbar tb = new Toolbar(true);
        setToolbar(tb);
        tb.setUIID("Container");
        getTitleArea().setUIID("Container");
        Form previous = Display.getInstance().getCurrent();
        tb.setBackCommand("", e -> previous.showBack());
        this.us = UserServices.getInstance();
        if (!Display.getInstance().isTablet()) {
            BorderLayout bl = (BorderLayout) getLayout();
            bl.defineLandscapeSwap(BorderLayout.NORTH, BorderLayout.EAST);
            bl.defineLandscapeSwap(BorderLayout.SOUTH, BorderLayout.CENTER);
        }
        getTitleArea().setUIID("Container");
        setUIID("SignIn");

        add(BorderLayout.NORTH, new Label(res.getImage("Logo.png"), "LogoLabel"));

        TextField login = new TextField("", "E-mail", 20, TextField.EMAILADDR);
        TextField password = new TextField("", "Mot de passe", 20, TextField.PASSWORD);

        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Button signIn = new Button("Sign In");
        Button signUp = new Button("Sign Up");
        Button forgotpassword = new Button("reset password");
        signUp.addActionListener(e -> new SignUpForm(res).show());
        signUp.setUIID("Link");
        Label doneHaveAnAccount = new Label("Don't have an account?");
        Label resetpassword = new Label("Did you forgot your password?");
        forgotpassword.setUIID("Link");
        Container content = BoxLayout.encloseY(
                FlowLayout.encloseCenter(doneHaveAnAccount, signUp),
                new FloatingHint(login),
                createLineSeparator(),
                new FloatingHint(password),
                createLineSeparator(),
                signIn,
                FlowLayout.encloseCenter(resetpassword, forgotpassword)
        );

        add(BorderLayout.SOUTH, content);
        content.setScrollableY(false);
        content.setScrollVisible(false);
        signIn.requestFocus();
        signIn.addActionListener(e -> {
            if ((login.getText().isEmpty()) && (password.getText().isEmpty())) {
                Dialog.show("Alert", "Please fill all fields", new Command("OK"));
            } else {

                if (us.loginAction(login.getText(), password.getText())) {

                    new ProfileForm(res, UserSession.instance.getU()).show();

                } else {
                    Dialog.show("Alert", "wrong credentials", new Command("OK"));
                }
            }

        });
        forgotpassword.addActionListener(e -> {

            new ActivateForm(res).show();

        });

    }

}
