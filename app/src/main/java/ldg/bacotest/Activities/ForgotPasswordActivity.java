package ldg.bacotest.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import ldg.bacotest.R;

/**
 * Created by Lars on 8/10/2015.
 */
public class ForgotPasswordActivity extends AppCompatActivity {
    private EditText emailfield;
    private Button resetPassworButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailfield = (EditText) findViewById(R.id.editText_forgot_password_emailadres);
        resetPassworButton = (Button) findViewById(R.id.button_send_mail_for_new_password);

        resetPassworButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailfield.getText().toString();
                ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getBaseContext(), "an email has been sent", Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
        });
    }
}
