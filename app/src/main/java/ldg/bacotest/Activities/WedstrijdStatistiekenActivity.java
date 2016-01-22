package ldg.bacotest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseObject;

import ldg.bacotest.R;

/**
 * Created by Lars on 6/01/2016.
 */
public class WedstrijdStatistiekenActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String kalenderObjectId;
    /**
     * match stats form
     */
    private CheckBox checkboxLars;

    private EditText editTextMatchStatsLarsGoals;
    private EditText editTextMatchStatsNielGoals;
    private EditText editTextMatchStatsSamGoals;
    private EditText editTextMatchStatsNicoGoals;
    private EditText editTextMatchStatsHennyGoals;
    private EditText editTextMatchStatsRakkeGoals;
    private EditText editTextMatchStatsNathanGoals;
    private EditText editTextMatchStatsCsanyiGoals;
    private EditText editTextMatchStatsPiejeGoals;
    private EditText editTextMatchStatsWietseGoals;

    private EditText editTextMatchStatsLarsAssists;
    private EditText editTextMatchStatsNielAssists;
    private EditText editTextMatchStatsSamAssists;
    private EditText editTextMatchStatsNicoAssists;
    private EditText editTextMatchStatsHennyAssists;
    private EditText editTextMatchStatsRakkeAssists;
    private EditText editTextMatchStatsNathanAssists;
    private EditText editTextMatchStatsCsanyiAssists;
    private EditText editTextMatchStatsPiejeAssists;
    private EditText editTextMatchStatsWietseAssists;

    private Button buttonConfirmStats;

    private String kalenderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wedstrijd_statistieken_activity);



         /* toolbar */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        /*form matchstats */


        editTextMatchStatsLarsGoals = (EditText) findViewById(R.id.editText_matchtstats_lars_goals);
        editTextMatchStatsNielGoals = (EditText) findViewById(R.id.editText_matchtstats_niel_goals);
        editTextMatchStatsSamGoals = (EditText) findViewById(R.id.editText_matchtstats_sam_goals);
        editTextMatchStatsNicoGoals = (EditText) findViewById(R.id.editText_matchtstats_nico_goals);
        editTextMatchStatsHennyGoals = (EditText) findViewById(R.id.editText_matchtstats_henny_goals);
        editTextMatchStatsRakkeGoals = (EditText) findViewById(R.id.editText_matchtstats_rakke_goals);
        editTextMatchStatsNathanGoals = (EditText) findViewById(R.id.editText_matchtstats_nathan_goals);
        editTextMatchStatsCsanyiGoals = (EditText) findViewById(R.id.editText_matchtstats_csanyi_goals);
        editTextMatchStatsPiejeGoals = (EditText) findViewById(R.id.editText_matchtstats_pieje_goals);
        editTextMatchStatsWietseGoals = (EditText) findViewById(R.id.editText_matchtstats_wietse_goals);

        editTextMatchStatsLarsAssists = (EditText) findViewById(R.id.editText_matchtstats_lars_assists);
        editTextMatchStatsNielAssists = (EditText) findViewById(R.id.editText_matchtstats_niel_assists);
        editTextMatchStatsSamAssists = (EditText) findViewById(R.id.editText_matchtstats_sam_assists);
        editTextMatchStatsNicoAssists = (EditText) findViewById(R.id.editText_matchtstats_nico_assists);
        editTextMatchStatsHennyAssists = (EditText) findViewById(R.id.editText_matchtstats_henny_assists);
        editTextMatchStatsRakkeAssists = (EditText) findViewById(R.id.editText_matchtstats_rakke_assists);
        editTextMatchStatsNathanAssists = (EditText) findViewById(R.id.editText_matchtstats_nathan_assists);
        editTextMatchStatsCsanyiAssists = (EditText) findViewById(R.id.editText_matchtstats_csanyi_assists);
        editTextMatchStatsPiejeAssists = (EditText) findViewById(R.id.editText_matchtstats_pieje_assists);
        editTextMatchStatsWietseAssists = (EditText) findViewById(R.id.editText_matchtstats_wietse_assists);
        buttonConfirmStats = (Button) findViewById(R.id.button_confirm_game_stats);

        final Intent intent = getIntent();
        kalenderObjectId = intent.getStringExtra("objectId");


        buttonConfirmStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onCheckboxClicked(v);


                String inputLarsGoals = editTextMatchStatsLarsGoals.getText().toString();
                String inputNielGoals = editTextMatchStatsNielGoals.getText().toString();
                /**
                 String inputSamGoals=editTextMatchStatsSamGoals.getText().toString();
                 String inputNicoGoals=editTextMatchStatsNicoGoals.getText().toString();
                 String inputhennyGoals=editTextMatchStatsHennyGoals.getText().toString();
                 String inputRakkeGoals=editTextMatchStatsRakkeGoals.getText().toString();
                 String inputNathanGoals=editTextMatchStatsNathanGoals.getText().toString();
                 String inputCsanyiGoals=editTextMatchStatsCsanyiGoals.getText().toString();
                 String inputPiejeGoals=editTextMatchStatsPiejeGoals.getText().toString();
                 String inputWietseGoals=editTextMatchStatsWietseGoals.getText().toString();
                 */


                String inputLarsAssists = editTextMatchStatsLarsAssists.getText().toString();
                String inputNielAssists = editTextMatchStatsNielAssists.getText().toString();

                /**
                 String inputSamAssists=editTextMatchStatsSamAssists.getText().toString();
                 String inputNicoAssists=editTextMatchStatsNicoAssists.getText().toString();
                 String inputhennyAssists=editTextMatchStatsHennyAssists.getText().toString();
                 String inputRakkeAssists=editTextMatchStatsRakkeAssists.getText().toString();
                 String inputNathanAssists=editTextMatchStatsNathanAssists.getText().toString();
                 String inputCsanyiAssists=editTextMatchStatsCsanyiAssists.getText().toString();
                 String inputPiejeAssists=editTextMatchStatsPiejeAssists.getText().toString();
                 String inputWietseAssists=editTextMatchStatsWietseAssists.getText().toString();
                 */


                /////////////////////////////////////////////////

                if (inputLarsGoals != null) {
                    ParseObject parseObject = new ParseObject("MatchStats");
                    //spelerId van Lars
                    String spelerid = "JBHYK1ztbD";
                    parseObject.put("goals", inputLarsGoals);
                    parseObject.put("assists", inputLarsAssists);
                    ParseObject kalender = ParseObject.createWithoutData("Kalender", kalenderObjectId);
                    parseObject.put("kalenderId", kalender);
                    ParseObject speler = ParseObject.createWithoutData("Speler", spelerid);
                    parseObject.put("spelerId", speler);
                    parseObject.saveInBackground();
                }

                if (inputNielGoals != null)

                {
                    ParseObject parseObject = new ParseObject("MatchStats");
                    //spelerId van Niel
                    String spelerid = "OwcCGs4Yn9";
                    parseObject.put("goals", inputNielGoals);
                    parseObject.put("assists", inputNielAssists);
                    ParseObject kalender = ParseObject.createWithoutData("Kalender", kalenderObjectId);
                    parseObject.put("kalenderId", kalender);
                    ParseObject speler = ParseObject.createWithoutData("Speler", spelerid);
                    parseObject.put("spelerId", speler);
                    parseObject.saveInBackground();

                }


                Intent intent = new Intent(getBaseContext(), KalenderActivity.class);

                startActivity(intent);

                Toast.makeText(getBaseContext(), "stats has been added", Toast.LENGTH_LONG).show();




            }


        });

    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?


        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_lars:
                if (checked){
                    editTextMatchStatsLarsGoals = (EditText) findViewById(R.id.editText_matchtstats_lars_goals);
                    editTextMatchStatsLarsAssists = (EditText) findViewById(R.id.editText_matchtstats_lars_assists);


                }

                else

                    break;

        }

    }
}
