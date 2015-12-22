package ldg.bacotest.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import ldg.bacotest.R;

/**
 * Created by Lars on 14/10/2015.
 */
public class BerichtAddActivity extends AppCompatActivity {
    private Toolbar toolbar;


    /**
     * message form
     */
    private EditText editTextAddBerichtTitel;
    private EditText editTextAddBerichtContent;
    private EditText editTextAddBerichtInleiding;
    private Button buttonPostBericht;
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


        /** Commit post message*/
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

        /**  test save picture in parse,*/


    }

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private Uri fileUri;

    private static final int CAMERA_REQUEST = 1888;
    private static final int SELECT_PHOTO = 100;

    public void goToGalleryPicture(View view) {
        Intent photointent = new Intent(getIntent().ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photointent.setType("image/*");
        startActivityForResult(photointent, SELECT_PHOTO);
    }

    public void goToTakePicture(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Specify the URI of your file as output directory for the picture
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:


                if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null!= data) {
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    cursor.close();
                    imageViewFoto.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                }

                break;

            case CAMERA_REQUEST:
                if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
                    if (resultCode == RESULT_OK) {
                        // Image captured and saved to fileUri specified in the Intent
                        Toast.makeText(this, "Image saved to:\n" +
                                data.getData(), Toast.LENGTH_LONG).show();
                        Bitmap bitmapPhoto = (Bitmap) data.getExtras().get("data");
                        imageViewFoto.setImageBitmap(bitmapPhoto);

                    } else if (resultCode == RESULT_CANCELED) {
                        // User cancelled the image capture
                    } else {
                        // Image capture failed, advise user
                    }
                }
                break;
        }
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
}
