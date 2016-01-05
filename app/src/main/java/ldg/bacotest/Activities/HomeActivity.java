package ldg.bacotest.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.Image;
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
import android.widget.Switch;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ldg.bacotest.Adapters.BerichtAdapter;
import ldg.bacotest.R;
import ldg.bacotest.entities.Berichten;
import ldg.bacotest.entities.Speler;

/**
 * Created by Lars on 5/10/2015.
 */
public class HomeActivity extends AppCompatActivity {
    private View view;
    private Toolbar toolbar;
    private String userName;
    /**
     * Initialize navigation Drawer
     */
    private DrawerLayout bacoDrawerLayout;
    private String mActivityTitle;
    private ActionBarDrawerToggle bacoDrawerToggle;
    private ArrayAdapter<String> bacoAdapter;
    private ListView bacoDrawerList;
    /**
     * Initialize recyclerview
     */
    private RecyclerView recyclerView;

    /**
     * initialize for method to retrieve data from parse to put them in a list and adapter
     */
    private List<Berichten> berichtenList;
    private RecyclerView.Adapter berichtenAdapter;
    private RecyclerView.LayoutManager berichtenLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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

        /* */
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_home);
        retrieveBerichtenFromParseDatabase();
    }

    private void retrieveBerichtenFromParseDatabase() {
        final ParseQuery parseQuery = new ParseQuery("Berichten");
        final List<Berichten> parsedBericht = new ArrayList<>();


        parseQuery.findInBackground(new FindCallback<ParseObject>() {

            @Override
            public void done(List<ParseObject> berichten, ParseException e) {
                if (e == null) {
                    for (ParseObject bericht : berichten) {
                        parseQuery.include("userId");
                        String titel = bericht.get("titel").toString();
                        String berichtInleiding = bericht.get("inleiding").toString();
                        String berichtContent = bericht.get("bericht").toString();
                        String berichtTimestamp=bericht.getCreatedAt().toString();
                        String objectId = bericht.getObjectId();


                        ParseUser userObject = (ParseUser) bericht.get("userId");
                        String userId = userObject.getObjectId();

                        ParseUser unknownUser = new ParseUser();
                        unknownUser.setUsername("unknown username");


                        ParseUser user = (ParseUser) bericht.get("userId");
                        ParseUser reactieUser = null;
                        try {
                            reactieUser = ParseUser.createWithoutData(ParseUser.class, userId).fetchIfNeeded();
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                        String userName = reactieUser.getUsername();


                        Berichten newBericht = new Berichten();
                        newBericht.setUserId(TextUtils.isEmpty(userName) ? unknownUser.getUsername() : userName);
                        newBericht.setTitel(titel);
                        newBericht.setBericht(berichtContent);
                        newBericht.setInleiding(berichtInleiding);
                        newBericht.setTimestamp(berichtTimestamp);
                        newBericht.setObjectId(objectId);
                        parsedBericht.add(newBericht);
                    }
                    berichtenList = parsedBericht;

                } else {
                    Log.e("error", "something went wrong retrieving berichten from parse");
                }

                berichtenAdapter = new BerichtAdapter(berichtenList, getBaseContext());
                berichtenLayoutManager = new LinearLayoutManager(getBaseContext());
                recyclerView.setLayoutManager(berichtenLayoutManager);
                recyclerView.setAdapter(berichtenAdapter);
            }
        });
    }

    /**
     * Add items to the drawerlist
     */
    private void addDawerItems() {
        String[] navigationMenuArray = {"HOME", "SPELERS", "KALENDER", "RANGSCHIKKING","LOG OUT"};
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
                    case 4:
                        logOut(view);


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
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (bacoDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();

        switch(item.getItemId()) {
            case R.id.action_settings_login:
                return true;

            case R.id.action_new_message:
                Toast.makeText(getBaseContext(),"Nieuw bericht",Toast.LENGTH_LONG).show();
                goToNieuwBericht(view);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }

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

    public void goToNieuwBericht(View view) {
        Intent intent = new Intent(this, BerichtAddActivity.class);
        startActivity(intent);
        finish();
    }

    //logout function
    public void logOut(View view){
        ParseUser currentUser = ParseUser.getCurrentUser();
        SharedPreferences stayLoggedIn = getBaseContext().getSharedPreferences("stay logged in", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = stayLoggedIn.edit();

        editor.putString("username", "");
        editor.putString("password", "");
        editor.commit();

        currentUser.logOut();
        Intent intent = new Intent(getBaseContext(), LogInActivity.class);
        startActivity(intent);
    }

}
