package ldg.bacotest.Activities;

import android.content.Context;
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

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ldg.bacotest.Adapters.KalenderAdapter;
import ldg.bacotest.R;
import ldg.bacotest.entities.Berichten;
import ldg.bacotest.entities.Kalender;

/**
 * Created by Lars on 5/10/2015.
 */
public class KalenderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String kalenderObjectId;
    /**
     * Initialize navigation Drawer
     */
    private ArrayAdapter<String> bacoAdapter;
    private DrawerLayout bacoDrawerLayout;
    private ListView bacoDrawerList;
    private String mActivityTitle;
    private ActionBarDrawerToggle bacoDrawertoggle;
    /**
     * Initialize recyclerview
     */
    private RecyclerView recyclerView;
    /**
     * initialize for method to retrieve data from parse to put them in a list and adapter
     */
    private List<Kalender> kalenderList;
    private RecyclerView.Adapter kalenderAdapter;
    private RecyclerView.LayoutManager kalenderLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalender);
     /* navigation drawer */
        bacoDrawerLayout = (DrawerLayout) findViewById(R.id.baco_drawer_layout);
        mActivityTitle = getTitle().toString();
        bacoDrawerList = (ListView) findViewById(R.id.baco_navigation_list);

        addDrawerItems();
        setupDrawerItems();

        /* toolbar */
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        /* RecyclerView Kalender*/
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_kalender);
        retrievekalenderItemsFromParseDatabase();

    }

    private void retrievekalenderItemsFromParseDatabase() {
        final ParseQuery parseQuery = new ParseQuery("Kalender");


        final List<Kalender> parsedKalender = new ArrayList<>();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> kalenderitems, ParseException e) {
                if (e == null) {
                    for (ParseObject kalender : kalenderitems) {
                        //parseQuery.include("kalenderId");
                        String thuisPloeg = kalender.get("thuisPloeg").toString();
                        String uitPloeg = kalender.get("uitPloeg").toString();
                        String datum = kalender.get("datum").toString();
                        String uur = kalender.get("uur").toString();
                        String plaats = kalender.get("plaats").toString();
                        String thuisScore = kalender.get("scoreThuisPloeg").toString();
                        String uitScore = kalender.get("scoreUitPloeg").toString();
                        String objectId = kalender.getObjectId();

                        /////////////////
                        ////////////////

                        Kalender kalender1 = new Kalender();
                        kalender1.setThuisPloeg(thuisPloeg);
                        kalender1.setUitPloeg(uitPloeg);
                        kalender1.setDatum(datum);
                        kalender1.setUur(uur);
                        kalender1.setPlaats(plaats);
                        kalender1.setScoreThuis(thuisScore);
                        kalender1.setScoreUit(uitScore);
                        kalender1.setObjectId(objectId);

                        parsedKalender.add(kalender1);
                    }

                    kalenderList = parsedKalender;
                } else {
                    Log.e("error", "something went wrong retrieving berichten from parse");
                }
                kalenderAdapter = new KalenderAdapter(kalenderList, getBaseContext());
                kalenderLayoutManager = new LinearLayoutManager(getBaseContext());
                recyclerView.setLayoutManager(kalenderLayoutManager);
                recyclerView.setAdapter(kalenderAdapter);
            }


        });


    }

    private void setupDrawerItems() {
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

    private void addDrawerItems() {
        bacoDrawertoggle = new ActionBarDrawerToggle(this, bacoDrawerLayout, R.string.open, R.string.close) {
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
                invalidateOptionsMenu();
            }
        };

        bacoDrawertoggle.setDrawerIndicatorEnabled(true);
        bacoDrawerLayout.setDrawerListener(bacoDrawertoggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        bacoDrawertoggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        bacoDrawertoggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kalender, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (bacoDrawertoggle.onOptionsItemSelected(item)) {
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
