package ldg.bacotest.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ldg.bacotest.Adapters.SpelerStatsAdapter;
import ldg.bacotest.R;
import ldg.bacotest.entities.SpelerStats;

/**
 * Created by Lars on 6/10/2015.
 */
public class SpelerDetailActivity extends AppCompatActivity {
    private String playerObjectId;

    /** initialize for method to retrieve data from parse to put them in a list and adapter*/
    private List<SpelerStats> dataSpelerStatsList;
    private RecyclerView spelerStatsRecyclerView;
    private RecyclerView.Adapter spelerStatsAdapter;
    private RecyclerView.LayoutManager spelerStatsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_speler_detail);

        spelerStatsRecyclerView= (RecyclerView) findViewById(R.id.my_recycler_view_speler_stats);
        retrievePlayerStatsFromParseDatabase();


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

    private void retrievePlayerStatsFromParseDatabase() {

        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("MatchStats");
        final List<SpelerStats> parsedPlayerStats = new ArrayList<>();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e ==null){
                    for (ParseObject spelerStat : list){
                        ParseObject parseObjectSpelerId = (ParseObject) spelerStat.get("spelerId");
                        String spelerId = parseObjectSpelerId.getObjectId().toString();

                        ParseObject parseObjectkalenderId = (ParseObject) spelerStat.get("kalenderId");
                        String kalenderId = parseObjectkalenderId.getObjectId().toString();

                        if (playerObjectId.equals(spelerId)){

                            String goals= spelerStat.get("goals").toString();
                            String assists= spelerStat.get("assists").toString();
                            String objectId=spelerStat.getObjectId();


                            ParseObject tegenstander=null;
                            try {
                                 tegenstander = ParseObject.createWithoutData("Kalender", kalenderId).fetchIfNeeded();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }

                            String spelerStatsThuisploeg=tegenstander.get("thuisPloeg").toString();
                            String spelerStatsuitploeg=tegenstander.get("uitPloeg").toString();



                            SpelerStats spelerStatsObject=new SpelerStats();
                            spelerStatsObject.setThuisploeg(spelerStatsThuisploeg);
                            spelerStatsObject.setUitploeg(spelerStatsuitploeg);
                            spelerStatsObject.setGoals(goals);
                            spelerStatsObject.setAssists(assists);
                            spelerStatsObject.setObjectId(objectId);

                            parsedPlayerStats.add(spelerStatsObject);
                        }
                    }
                    dataSpelerStatsList=parsedPlayerStats;
                } else {
                    Log.e("retrieving error", "Shit went wrong when trying to get spelerstats from Parse.com");

                }

                spelerStatsAdapter=new SpelerStatsAdapter(dataSpelerStatsList,getBaseContext());
                spelerStatsLayoutManager=new LinearLayoutManager(getBaseContext());
                spelerStatsRecyclerView.setLayoutManager(spelerStatsLayoutManager);
                spelerStatsRecyclerView.setAdapter(spelerStatsAdapter);

            }
        });
    }
}
