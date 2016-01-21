package ldg.bacotest.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import ldg.bacotest.Adapters.MatchStatsAdapter;
import ldg.bacotest.Adapters.ReactiesAdapter;
import ldg.bacotest.R;
import ldg.bacotest.entities.MatchStats;
import ldg.bacotest.entities.Reacties;
import ldg.bacotest.entities.Speler;

/**
 * Created by Lars on 5/01/2016.
 */
public class KalenderDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String kalenderObjectId;

    private String matchStatPlayerName;
    /**
     * Initialize navigation Drawer
     */
    private DrawerLayout bacoDrawerLayout;
    private String mActivityTitle;
    private ActionBarDrawerToggle bacoDrawerToggle;
    private ArrayAdapter<String> bacoAdapter;
    private ListView bacoDrawerList;

    /**
     * initialize for method to retrieve data from parse to put them in a list and adapter
     */
    private List<MatchStats> dataMatchStatsList;
    private RecyclerView matchStatsRecyclerView;
    private RecyclerView.Adapter matchStatsAdapter;
    private RecyclerView.LayoutManager matchStatsLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender_detail);

        matchStatsRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_kalender_match_stats);
        retrieveMatchStatsFromParseDatabase();

        /** navigation drawer */
        bacoDrawerList = (ListView) findViewById(R.id.baco_navigation_list);
        bacoDrawerLayout = (DrawerLayout) findViewById(R.id.baco_drawer_layout);
        mActivityTitle = getTitle().toString();

        addDawerItems();
        setupDrawer();


        /* toolbar */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /** get info from card from kalenderActivity */
        final Intent intent = getIntent();
        kalenderObjectId = intent.getStringExtra("objectId");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Kalender");
        query.getInBackground(kalenderObjectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    KalenderObjectWasRetrievedSuccessfully(object);

                } else {
                    Log.e("error getting kalender", "The specified kalender with id " + kalenderObjectId + " could not be retrieved.");
                }
            }
        });


    }


    /**
     * setup of drawerlist , actions bacoDrawerToggle
     */
    private void setupDrawer() {
        bacoDrawerToggle = new ActionBarDrawerToggle(this, bacoDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("MENU");
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mActivityTitle);
            }
        };

        bacoDrawerToggle.setDrawerIndicatorEnabled(true);
        bacoDrawerLayout.setDrawerListener(bacoDrawerToggle);
    }

    /**
     * Add items to the drawerlist
     */
    private void addDawerItems() {
        String[] navigationMenuArray = {"HOME", "SPELERS", "KALENDER", "RANGSCHIKKING"};
        bacoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, navigationMenuArray);
        bacoDrawerList.setAdapter(bacoAdapter);
        bacoDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        homeActivity(view);
                        break;
                    case 1:
                        spelersActivity(view);
                        break;
                    case 2:
                        kalenderActivity(view);
                        break;
                    case 3:
                        rangschikkingActivity(view);
                        break;


                    default:
                        break;
                }
            }
        });
    }

    //get kalenderinfo from choosen kalender
    private void KalenderObjectWasRetrievedSuccessfully(ParseObject kalender) {

        String kalenderThuisPloeg = kalender.get("thuisPloeg").toString();
        String kalenderUitPloeg = kalender.get("uitPloeg").toString();
        String kalenderDatum = kalender.get("datum").toString();
        String kalenderUur = kalender.get("uur").toString();
        String kalenderPlaats = kalender.get("plaats").toString();

        String kalenderScoreThuis = kalender.get("scoreThuisPloeg").toString();
        String kalenderScoreUit = kalender.get("scoreUitPloeg").toString();

        TextView textViewKalenderThuisPloeg = (TextView) findViewById(R.id.textview_kalender_detail_thuisploeg);
        TextView textViewKalenderUitPloeg = (TextView) findViewById(R.id.textview_kalender_detail_uitploeg);
        TextView textViewKalenderDatum = (TextView) findViewById(R.id.textview_kalender_detail_datum);
        TextView textViewKalenderUur = (TextView) findViewById(R.id.textview_kalender_detail_uur);
        TextView textViewKalenderPlaats = (TextView) findViewById(R.id.textview_kalender_detail_plaats);
        TextView textViewScoreThuis = (TextView) findViewById(R.id.textview_kalender_detail_score_thuisploeg);
        TextView textViewScoreUit = (TextView) findViewById(R.id.textview_kalender_detail_score_uitploeg);

        textViewKalenderThuisPloeg.setText(kalenderThuisPloeg);
        textViewKalenderUitPloeg.setText(kalenderUitPloeg);
        textViewKalenderPlaats.setText(kalenderPlaats);
        textViewKalenderDatum.setText(kalenderDatum);
        textViewKalenderUur.setText(kalenderUur);
        textViewScoreThuis.setText(kalenderScoreThuis);
        textViewScoreUit.setText(kalenderScoreUit);
    }


    public void goToInsertMatchStats(View view) {
        //final Intent intent = getIntent();
        //kalenderObjectId = intent.getStringExtra("objectId");


        ParseQuery<ParseObject> query = ParseQuery.getQuery("Kalender");
        query.getInBackground(kalenderObjectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    KalenderObjectWasRetrievedSuccessfully(object);

                    String currentUser = ParseUser.getCurrentUser().getObjectId();
                    String userLarsCanOnlyInsertStats = "4tWF0GBeNt";

                    if (currentUser.equals(userLarsCanOnlyInsertStats)) {

                        Intent newintent = new Intent(getBaseContext(), WedstrijdStatistiekenActivity.class);
                        newintent.putExtra("objectId", kalenderObjectId);
                        startActivity(newintent);
                        finish();
                    }
                    else {
                        Toast.makeText(getBaseContext(), "You can only make posts on your own blogs.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        view.setVisibility(View.GONE);

    }

    private void retrieveMatchStatsFromParseDatabase() {

        final ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("MatchStats");
        final List<MatchStats> parsedMatchStatsForSpecificMatch = new ArrayList<>();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject matchstat : list) {

                        ParseObject parseObjectkalenderId = (ParseObject) matchstat.get("kalenderId");
                        String kalenderId = parseObjectkalenderId.getObjectId().toString();

                        if (kalenderObjectId.equals(kalenderId)) {
                            query.include("spelerId");
                            String goals = matchstat.get("goals").toString();
                            String assists = matchstat.get("assists").toString();
                            String objectId = matchstat.getObjectId();

                            //
                            ParseObject parseObjectSpeler = (ParseObject) matchstat.get("spelerId");
                            String spelerId = parseObjectSpeler.getObjectId();



                            //

                            MatchStats matchStatsObject = new MatchStats();
                            //matchStatsObject.setNaamSpeler(spelersnaam);
                            matchStatsObject.setGoals(goals);
                            matchStatsObject.setAssists(assists);
                            matchStatsObject.setObjectId(objectId);

                            parsedMatchStatsForSpecificMatch.add(matchStatsObject);
                        }

                    }

                    dataMatchStatsList = parsedMatchStatsForSpecificMatch;
                } else {
                    Log.e("retrieving error", "Shit went wrong when trying to get matchstats from Parse.com");
                }
                matchStatsAdapter = new MatchStatsAdapter(dataMatchStatsList, getBaseContext());
                matchStatsLayoutManager = new LinearLayoutManager(getBaseContext());
                matchStatsRecyclerView.setLayoutManager(matchStatsLayoutManager);
                matchStatsRecyclerView.setAdapter(matchStatsAdapter);
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bacoDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        bacoDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kalender, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (bacoDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * actions onclick on drawerlist
     */
    private void spelersActivity(View view) {
        Intent intent = new Intent(this, SpelerActivity.class);
        startActivity(intent);
        finish();
    }


    private void homeActivity(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void rangschikkingActivity(View view) {
        Intent intent = new Intent(this, RangschikkingActivity.class);
        startActivity(intent);
        finish();
    }

    private void kalenderActivity(View view) {
        Intent intent = new Intent(this, KalenderActivity.class);
        startActivity(intent);
        finish();
    }


}
