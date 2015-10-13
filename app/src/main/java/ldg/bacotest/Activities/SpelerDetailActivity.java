package ldg.bacotest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ldg.bacotest.R;
import ldg.bacotest.entities.Speler;

/**
 * Created by Lars on 6/10/2015.
 */
public class SpelerDetailActivity extends AppCompatActivity {
    private String playerObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_speler_detail);


        /** get info from card from SpelerActivity */
        final Intent intent = getIntent();
        playerObjectId = intent.getStringExtra("objectId");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Speler");
        query.getInBackground(playerObjectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    playerObjectWasRetrievedSuccessfully(object);
                } else {
                    Log.e("error retrieving player", "The specified player with id " + playerObjectId + " could not be retrieved.");
                }
            }
        });
    }


    private void playerObjectWasRetrievedSuccessfully(ParseObject speler) {
        String voornaam = speler.get("voornaam").toString();
        String achternaam = speler.get("achternaam").toString();
        String positie = speler.get("positie").toString();
        String voet = speler.get("voet").toString();
        String leeftijd = speler.get("leeftijd").toString();

        TextView textViewVoornaam = (TextView) findViewById(R.id.textview_detail_speler_voornaam);
        TextView textViewAchternaam = (TextView) findViewById(R.id.textview_detail_speler_naam);
        TextView textViewPositie = (TextView) findViewById(R.id.textview_detail_positie);
        TextView textViewVoet = (TextView) findViewById(R.id.textview_detail_voet);
        TextView textViewLeeftijd = (TextView) findViewById(R.id.textview_detail_leeftijd);


        textViewVoornaam.setText(voornaam);
        textViewAchternaam.setText(achternaam);
        textViewPositie.setText(positie);
        textViewVoet.setText(voet);
        textViewLeeftijd.setText(leeftijd);
    }
}
