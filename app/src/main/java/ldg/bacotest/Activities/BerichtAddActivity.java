package ldg.bacotest.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;

import ldg.bacotest.R;

/**
 * Created by Lars on 14/10/2015.
 */
public class BerichtAddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ParseFile photoFile;
    private String berichtObjectId;
    private String fotoObjectIdForNewMessage;


    /**
     * message form
     */
    private EditText editTextAddBerichtTitel;
    private EditText editTextAddBerichtContent;
    private EditText editTextAddBerichtInleiding;
    private Button buttonPostBericht;
    private Button confirmPicture;
    private ImageView imageViewFoto;

    /**
     * Initialize navigation Drawer
     */
    private DrawerLayout bacoDrawerLayout;
    private String mActivityTitle;
    private ActionBarDrawerToggle bacoDrawerToggle;
    private ArrayAdapter<String> bacoAdapter;
    private ListView bacoDrawerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bericht_add);

        /* navigation drawer */
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

        /* form add message*/
        editTextAddBerichtTitel = (EditText) findViewById(R.id.editText_bericht_add_titel);
        editTextAddBerichtContent = (EditText) findViewById(R.id.editText_bericht_add_content);
        editTextAddBerichtInleiding = (EditText) findViewById(R.id.editText_bericht_add_inleiding);
        buttonPostBericht = (Button) findViewById(R.id.button_add_bericht_post_bericht);
        imageViewFoto = (ImageView) findViewById(R.id.imageViewTakenPicture);
        confirmPicture = (Button) findViewById(R.id.button_confirm_picture);

        /** get objectId from picture*/
        final Intent intent = getIntent();
        fotoObjectIdForNewMessage=intent.getStringExtra("fotoObjectId");

        confirmPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Locate the image in res > drawable-hdpi
                Bitmap bitmap = BitmapFactory.decodeFile(String.valueOf(imageViewFoto));
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile

            }
        });


        /** Commit post message*/
        buttonPostBericht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputBerichtTitel = editTextAddBerichtTitel.getText().toString();
                String inputBerichtContent = editTextAddBerichtContent.getText().toString();
                String inputBerichtInleiding = editTextAddBerichtInleiding.getText().toString();
               // String inputBerichtFotoObjectId=fotoObjectIdForNewMessage.toString();


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
                    ParseObject foto = ParseObject.createWithoutData("BerichtFoto", fotoObjectIdForNewMessage);
                    parseObject.put("berichtFotoId",foto);
                    // parseObject.put("fotoBericht",file);


                    ParseUser user = ParseUser.getCurrentUser();
                    if (user != null) {
                        parseObject.put("userId", user);
                    } else {
                        ParseUser unknownUser = new ParseUser();
                        unknownUser.setUsername("Username is unknown");

                        parseObject.put("userId", unknownUser);
                    }




                    String usernamefornotification = user.getUsername();

                    /** notification when post new message*/
                    // build notification

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getBaseContext().getApplicationContext())
                                    .setSmallIcon(R.drawable.ic_launcher)
                                    .setContentTitle("Niew bericht van: " + usernamefornotification)
                                    .setContentText(inputBerichtTitel);

                    //notification onClick action:
                    Intent resultIntent = new Intent(getBaseContext(), HomeActivity.class);
                    PendingIntent resultPendingIntent =
                            PendingIntent.getActivity(
                                    getBaseContext().getApplicationContext(),
                                    0,
                                    resultIntent,
                                    PendingIntent.FLAG_UPDATE_CURRENT
                            );

                    // Sets an ID for the notification
                    int mNotificationId = 001;
                    // Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    // Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());


                    mBuilder.setAutoCancel(true);
                    mBuilder.setContentIntent(resultPendingIntent);
                    /** */


                    parseObject.saveInBackground();

                      /* go to picture intent to add picture to message */



                    /////






                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), "Bericht has been added to the list", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**  test save picture in parse,*/


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


    /* Inflate menu_add_bericht to toolbar on this activitie*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_add_bericht, menu);
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
    public void goToActivityPicture(View view){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Berichten");
        query.getInBackground(berichtObjectId, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e!=null){
                            Intent intent = new Intent(getBaseContext(), PictureActivity.class);
                            intent.putExtra("objectId", berichtObjectId);
                            startActivity(intent);
                        }
                    }
                });




    }
}
