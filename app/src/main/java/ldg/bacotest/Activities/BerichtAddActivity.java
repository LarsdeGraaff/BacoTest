package ldg.bacotest.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NotificationCompat;
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
    private ParseFile photoFile;


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
                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(intent);
                    Toast.makeText(getBaseContext(), "Bericht has been added to the list", Toast.LENGTH_LONG).show();
                }
            }
        });

        /**  test save picture in parse,*/


    }


    private byte[] readInFile(String path) throws IOException {
        // TODO Auto-generated method stub
        byte[] data = null;
        File file = new File(path);
        InputStream input_stream = new BufferedInputStream(new FileInputStream(
                file));
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        data = new byte[16384]; // 16K
        int bytes_read;
        while ((bytes_read = input_stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, bytes_read);
        }
        input_stream.close();
        return buffer.toByteArray();

    }

    /**
     * Take picture or go to gallery
     */


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
        startActivityForResult(intent, CAMERA_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case SELECT_PHOTO:


                if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && null != data) {
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
                if (requestCode == CAMERA_REQUEST) {
                    if (resultCode == RESULT_OK) {
                        // Image captured and saved to fileUri specified in the Intent
                        Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
                        Bitmap bitmapPhoto = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        bitmapPhoto.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] dataPhoto=stream.toByteArray();


                        ParseFile file=new ParseFile("foto.jpg",dataPhoto);
                        file.saveInBackground();

                        ParseObject foto= new ParseObject("BerichtFoto");
                        foto.put("picture",file);
                        foto.saveInBackground();


                        imageViewFoto.setImageBitmap(bitmapPhoto);


                    } else if (resultCode == RESULT_CANCELED) {
                        // User cancelled the image capture
                    } else {
                        // Image capture failed, advise user
                    }
                }

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
