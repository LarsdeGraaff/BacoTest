package ldg.bacotest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import ldg.bacotest.R;

/**
 * Created by Lars on 14/10/2015.
 */
public class BerichtAddActivity extends AppCompatActivity {
    private EditText editTextAddBerichtTitel;
    private EditText editTextAddBerichtContent;
    private EditText editTextAddBerichtInleiding;
    private Button buttonPostBericht;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bericht_add);

        editTextAddBerichtTitel = (EditText) findViewById(R.id.editText_bericht_add_titel);
        editTextAddBerichtContent = (EditText) findViewById(R.id.editText_bericht_add_content);
        editTextAddBerichtInleiding = (EditText) findViewById(R.id.editText_bericht_add_inleiding);
        buttonPostBericht = (Button) findViewById(R.id.button_add_bericht_post_bericht);

        buttonPostBericht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputBerichtTitel = editTextAddBerichtTitel.getText().toString();
                String inputBerichtContent = editTextAddBerichtContent.getText().toString();
                String inputBerichtInleiding = editTextAddBerichtInleiding.getText().toString();

                if (TextUtils.isEmpty(inputBerichtTitel) || TextUtils.isEmpty(inputBerichtInleiding) || TextUtils.isEmpty(inputBerichtContent)) {
                    if (TextUtils.isEmpty(inputBerichtTitel)) {
                        editTextAddBerichtTitel.setError("Title can't be empty");
                    }


                    if (TextUtils.isEmpty(inputBerichtInleiding)) {
                        editTextAddBerichtInleiding.setError("Content can't be empty");
                    }

                    if (TextUtils.isEmpty(inputBerichtContent)) {
                        editTextAddBerichtContent.setError("Content can't be empty");
                    }
                } else {
                    ParseObject parseObject = new ParseObject("Berichten");
                    parseObject.put("titel", editTextAddBerichtTitel.getText().toString());
                    parseObject.put("inleiding", editTextAddBerichtInleiding.getText().toString());
                    parseObject.put("bericht", editTextAddBerichtContent.getText().toString());


                    ParseUser user = ParseUser.getCurrentUser();
                    if (user != null) {
                        parseObject.put("userId", user);
                    } else {
                        ParseUser unknownUser = new ParseUser();
                        unknownUser.setUsername("Username is unknown");

                        parseObject.put("userId", unknownUser);
                    }


                    parseObject.saveInBackground();
                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), "Bericht has been added to the list", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
