package ldg.bacotest.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import ldg.bacotest.R;

/**
 * Created by Lars on 8/10/2015.
 */
public class RegistrationActivity extends AppCompatActivity {
    /* initialize EditTextFields for the confirm onclicklistener and Strings */
    private EditText userNameField;
    private EditText passWordField;
    private EditText emailField;
    private EditText passWordConfirmationField;
    private Button registrationButton;
    private String userName;
    private String passWord;
    private String emailAddress;
    private String passwordConfirmation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        /** Set registrationFields */
        userNameField = (EditText) findViewById(R.id.editText_username);
        passWordField = (EditText) findViewById(R.id.editText_password);
        passWordConfirmationField = (EditText) findViewById(R.id.editText_password_confirm);
        emailField = (EditText) findViewById(R.id.editText_email);
        registrationButton = (Button) findViewById(R.id.registration_button_confirm);
        /** registrationButton onClicklistener */
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Create new User*/
                ParseUser newUser = new ParseUser();

                /* set registrationFields to Strings for new user */
                userName = userNameField.getText().toString();
                passWord = passWordField.getText().toString();
                passwordConfirmation = passWordConfirmationField.getText().toString();
                emailAddress = emailField.getText().toString();

                /** checking fields*/
                /* checking for empty fields*/
                if (userName.equals("") || passWord.equals("") || emailAddress.equals("")) {
                    if (userName.equals("")) {
                        userNameField.setError("Username can not be empty.");
                        return;
                    }
                    if (passWord.equals("")) {
                        passWordField.setError("Password can not be empty.");
                        return;
                    }
                    if (emailAddress.equals("")) {
                        emailField.setError("E-mail address can not be empty.");
                        return;
                    }
                }
                /* checking if emailfield contains @ */
                if (emailAddress.contains("@")) {
                } else {
                    emailField.setError("Please enter a valid e-mailaddress. We know when you're screwing around...");
                    return;
                }

                /* checking password and confirmation match*/
                if (passWord.equals(passwordConfirmation)) {

                } else {
                    passWordConfirmationField.setError("password does not match");
                    return;
                }



                /* add string registration inputfields to new User*/
                newUser.setUsername(userName);
                newUser.setPassword(passWord);
                newUser.setEmail(emailAddress);

                /** set up the signUp in background in Parse*/
                newUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getBaseContext(), "Registration SUCCES", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getBaseContext(), "Registration FAIL", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

                finish();
            }
        });
    }
}
