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
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import ldg.bacotest.Adapters.SpelerAdapter;
import ldg.bacotest.R;
import ldg.bacotest.entities.Speler;
import ldg.bacotest.entities.Statistieken;

/**
 * Created by Lars on 5/10/2015.
 */
public class SpelerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    /** Initialize navigation Drawer*/
    private DrawerLayout bacoDrawerLayout;
    private String mActivityTitle;
    private ActionBarDrawerToggle bacoDrawerToggle;
    private ArrayAdapter<String> bacoAdapter;
    private ListView bacoDrawerList;
    /** Initialize recyclerview*/
    private RecyclerView recyclerView;

    /** initialize for method to retrieve data from parse to put them in a list and adapter*/
    private List<Speler> spelersList;
    private RecyclerView.Adapter spelerAdapter;
    private RecyclerView.LayoutManager spelerLayoutManager;
    private RecyclerView spelerRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spelers);

         /* navigation drawer */
        bacoDrawerList = (ListView) findViewById(R.id.baco_navigation_list);
        bacoDrawerLayout = (DrawerLayout) findViewById(R.id.baco_drawer_layout);
        mActivityTitle = getTitle().toString();

        addDawerItems();
        setupDrawer();

     /* toolbar */
        toolbar= (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        /* recyclerview */

        recyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        retrievePlayersFromParseDataBase();

        /* */


    }

    /**  Method to retrieve players from parse database*/

    private void retrievePlayersFromParseDataBase() {
       ParseQuery parseQuery=new ParseQuery("Speler");
        final List<Speler> parsedSpeler=new ArrayList<>();

        parseQuery.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> spelers, ParseException e) {
                if (e==null){
                    for (ParseObject speler:spelers){


                        String voorNaam=speler.get("voornaam").toString();
                        String achterNaam=speler.get("achternaam").toString();
                        String positie=speler.get("positie").toString();
                        String voet=speler.get("voet").toString();
                        String leeftijd= speler.get("leeftijd").toString();
                        String objectId=speler.getObjectId();

                        Speler newSpeler=new Speler();
                        newSpeler.setSpelersVoornaam(voorNaam);
                        newSpeler.setSpelersNaam(achterNaam);
                        newSpeler.setPositie(positie);
                        newSpeler.setVoet(voet);
                        newSpeler.setLeeftijd(leeftijd);


                        newSpeler.setObjectId(objectId);
                        parsedSpeler.add(newSpeler);
                    }
                    spelersList=parsedSpeler;
                }
                else {
                    Log.e("error","something went wrong retrieving players from parse");
                }
                spelerAdapter=new SpelerAdapter(spelersList,getBaseContext());
                spelerLayoutManager = new LinearLayoutManager(getBaseContext());

                recyclerView.setLayoutManager(spelerLayoutManager);
                recyclerView.setAdapter(spelerAdapter);
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_spelers, menu);
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
        Intent intent=new Intent(this,RangschikkingActivity.class);
        startActivity(intent);
        finish();
    }

    private void kalenderActivity(View view) {
        Intent intent=new Intent(this, KalenderActivity.class);
        startActivity(intent);
        finish();
    }

}