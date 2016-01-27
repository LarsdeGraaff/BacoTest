package ldg.bacotest.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import ldg.bacotest.R;


/**
 * Created by Lars on 26/01/2016.
 */
public class PictureActivity extends AppCompatActivity {

    private ImageView imageViewFoto;
    private String fotoOjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picture);

        imageViewFoto= (ImageView) findViewById(R.id.imageViewTakenPicture);



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
                        // Toast.makeText(this, "Image saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
                        Bitmap bitmapPhoto = (Bitmap) data.getExtras().get("data");
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        bitmapPhoto.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                        byte[] dataPhoto=stream.toByteArray();



                        ParseUser parseUser=ParseUser.getCurrentUser();
                        String parseUserName=parseUser.getUsername().toString();


                        String namePicture= parseUserName + "_bericht.jpg";
                        ParseFile file=new ParseFile(namePicture,dataPhoto);
                        file.saveInBackground();




                        final ParseObject foto= new ParseObject("BerichtFoto");
                        String fotoId=foto.getObjectId();
                        foto.put("picture", file);
                        foto.put("userId", parseUser);
                        //foto.put("berichtObjectId",berichtOjectId);
                        foto.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if (e==null){
                                    String fotoObjectId=foto.getObjectId();

                                    Intent intent=new Intent(getBaseContext(),BerichtAddActivity.class);
                                    intent.putExtra("fotoObjectId",fotoObjectId);
                                    startActivity(intent);
                                    finish();


                                }
                                else {

                                }
                            }
                        });



                       // imageViewFoto.setImageBitmap(bitmapPhoto);



                    } else if (resultCode == RESULT_CANCELED) {
                        // User cancelled the image capture
                    } else {
                        // Image capture failed, advise user
                    }
                }

        }


    }


}
