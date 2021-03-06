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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import ldg.bacotest.Adapters.ReactiesAdapter;
import ldg.bacotest.R;
import ldg.bacotest.entities.Reacties;

/**
 * Created by Lars on 8/10/2015.
 */
public class BerichtDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    /** Initialize navigation Drawer*/
    private DrawerLayout bacoDrawerLayout;
    private String mActivityTitle;
    private ActionBarDrawerToggle bacoDrawerToggle;
    private ArrayAdapter<String> bacoAdapter;
    private ListView bacoDrawerList;

    /** initialize for method to retrieve data from parse to put them in a list and adapter*/
    private List<Reacties> dataReactiesList;
    private RecyclerView reactieRecyclerView;
    private RecyclerView.Adapter reactieAdapter;
    private RecyclerView.LayoutManager reactieLayoutManager;
    private String berichtenObjectId;

    /** declare for reactiePost*/
    private String berichtenId;
    private EditText editTextReactie;
    private Button buttonReactiePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berichten_detail);

        reactieRecyclerView= (RecyclerView) findViewById(R.id.reacties_recycler_view);
        retrieveReactiesFromParseDatabase();

         /** navigation drawer */
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

        /** get info from card from berichtenActivity */
        final Intent intent = getIntent();
        berichtenObjectId = intent.getStringExtra("objectId");

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Berichten");
        query.getInBackground(berichtenObjectId, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    BerichtenObjectWasRetrievedSuccessfully(object);
                } else {
                    Log.e("error retrieving player", "The specified player with id " + berichtenObjectId + " could not be retrieved.");
                }
            }
        });

        /** */
        editTextReactie= (EditText) findViewById(R.id.editText_reactie);
        buttonReactiePost= (Button) findViewById(R.id.button_reactie);



        buttonReactiePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputReactie=editTextReactie.getText().toString();

                if (TextUtils.isEmpty(inputReactie)){
                    editTextReactie.setError("Reactie can't be empty");
                }
                else {
                    ParseObject parseObject=new ParseObject("Reacties");
                    parseObject.put("reactie",inputReactie);

                    ParseObject bericht=ParseObject.createWithoutData("Berichten",berichtenObjectId);
                    parseObject.put("berichtId", bericht);
                    ParseUser user=ParseUser.getCurrentUser();

                    if (user != null) {
                        parseObject.put("userId", user);
                    } else {
                        ParseUser unknownUser = new ParseUser();
                        unknownUser.setUsername("Username is unknown");

                        parseObject.put("user", unknownUser);
                    }

                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            Toast.makeText(getBaseContext(),"Reactie has been added",Toast.LENGTH_LONG);
                            finish();
                        }
                    });
                }
            }
        });
    }
    /**  setup of drawerlist , actions bacoDrawerToggle*/
    private void setupDrawer() {
        bacoDrawerToggle=new ActionBarDrawerToggle(this,bacoDrawerLayout,R.string.open,R.string.close){
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

    /** Add items to the drawerlist */
    private void addDawerItems() {
        String[] navigationMenuArray = {"HOME", "SPELERS","KALENDER","RANGSCHIKKING"};
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

    private void retrieveReactiesFromParseDatabase() {
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("Reacties");
        final List<Reacties> parsedReactiesForBericht=new ArrayList<>();

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> reacties, ParseException e) {
                if (e == null) {
                    for (ParseObject reactie : reacties) {
                        ParseObject parseObjectBerichtId = (ParseObject) reactie.get("berichtId");
                        String berichtId = parseObjectBerichtId.getObjectId().toString();
                        if (berichtenObjectId.equals(berichtId)) {
                            String stringReactie = reactie.get("reactie").toString();
                            String objectId = reactie.getObjectId();

                            ParseUser userObject = (ParseUser) reactie.get("userId");
                            String userId = userObject.getObjectId();


                            Reacties newReacties = new Reacties();
                            newReacties.setReactie(stringReactie);
                            newReacties.setObjectId(objectId);

                            ParseUser unknownUser = new ParseUser();
                            unknownUser.setUsername("unknown username");

                            ParseUser reactieUser = null;
                            try {
                                reactieUser = ParseUser.createWithoutData(ParseUser.class, userId).fetchIfNeeded();
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                            String userName = reactieUser.getUsername();
                            newReacties.setUserId(TextUtils.isEmpty(userName) ? unknownUser.getUsername() : userName);

                            parsedReactiesForBericht.add(newReacties);
                        }
                    }
                    dataReactiesList = parsedReactiesForBericht;
                } else {
                    Log.e("retrieving error", "Shit went wrong when trying to get reacties from Parse.com");
                }
                reactieAdapter = new ReactiesAdapter(dataReactiesList, getBaseContext());
                reactieLayoutManager = new LinearLayoutManager(getBaseContext());
                reactieRecyclerView.setLayoutManager(reactieLayoutManager);
                reactieRecyclerView.setAdapter(reactieAdapter);
            }
        });

    }

    //get berichtinfo from choosen bericht
    private void BerichtenObjectWasRetrievedSuccessfully(ParseObject bericht) {
        String berichtTitel=bericht.get("titel").toString();
        String berichtInleiding=bericht.get("inleiding").toString();
        String berichtContent=bericht.get("bericht").toString();

        TextView textViewBerichtTitel= (TextView) findViewById(R.id.textview_bericht_detail_titel);
        TextView textViewBerichtInleiding= (TextView) findViewById(R.id.textview_bericht_detail_inleiding);
        TextView textViewBerichtContent= (TextView) findViewById(R.id.textview_bericht_detail_content);

        textViewBerichtTitel.setText(berichtTitel);
        textViewBerichtInleiding.setText(berichtInleiding);
        textViewBerichtContent.setText(berichtContent);
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

        return super.onOptionsItemSelected(item);
    }
    /** actions onclick on drawerlist*/
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


    public void goToAddPictureIntent(View view){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Berichten");
        query.getInBackground(berichtenObjectId, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    Intent intent = new Intent(getBaseContext(), PictureActivity.class);
                    intent.putExtra("objectId", berichtenObjectId);
                    startActivity(intent);
                }
            }
        });
    }


}
