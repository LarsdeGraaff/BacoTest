package ldg.bacotest.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

import ldg.bacotest.R;

public class LogInActivity extends AppCompatActivity {
    private EditText loginUserNameTextField;
    private EditText loginWachtwoordTextField;
    private Button logInButton;
    private String usernametxt;
    private String passwordtxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginUserNameTextField = (EditText) findViewById(R.id.login_username_edittext);
        loginWachtwoordTextField = (EditText) findViewById(R.id.login_wachtwoord_edittext);
        logInButton = (Button) findViewById(R.id.login_button);

        //shared preferences for login
        final SharedPreferences stayLoggedIn = getBaseContext().getSharedPreferences("stay logged in", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = stayLoggedIn.edit();
        usernametxt = stayLoggedIn.getString("username", "");
        passwordtxt = stayLoggedIn.getString("password", "");

        //log in background with shared preferences
        if (!TextUtils.isEmpty(stayLoggedIn.getString("username", "")) && !TextUtils.isEmpty(stayLoggedIn.getString("password", ""))) {

            ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (parseUser != null) {
                                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                                startActivity(intent);
                                Toast.makeText(getBaseContext(), " Succesfully logged in.", Toast.LENGTH_SHORT).show();
                                editor.putString("username", usernametxt);
                                editor.putString("password", passwordtxt);
                                editor.commit();

                                //startActivity(intent);
                                finish();
                            }

                            else {
                                Toast.makeText(getBaseContext(), " The username and password don't seem to match.", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }

            );
        }


        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //gets text from username field en password field and put in string
                usernametxt = loginUserNameTextField.getText().toString();
                passwordtxt = loginWachtwoordTextField.getText().toString();

                //uses Strings usernamtxt en passwordtxt to log in background
                ParseUser.logInInBackground(usernametxt, passwordtxt, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        //checks if parseUser Object is not null and in Parse Database
                        if (parseUser != null) {

                            Intent intent = new Intent(getBaseContext(), HomeActivity.class);

                            editor.putString("username", usernametxt);
                            editor.putString("password", passwordtxt);
                            editor.commit();

                            startActivity(intent);
                            finish();

                        }
                        //if usernametxt and passwordtxt is empty or is'nt user , shows message
                        else {
                            Toast.makeText(getBaseContext(), " The username and password don't seem to match.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void goToRegistration(View view) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }

    public void goToForgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}
