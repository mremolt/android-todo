package de.dcs.mrtodoapp.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.query.Select;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.dcs.mrtodoapp.app.models.Item;


public class ItemDetailActivity extends Activity {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;
    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        Intent intent = getIntent();
        item = new Select().from(Item.class).where("_id = ?", intent.getLongExtra("itemId", 0)).executeSingle();
        final Activity current = this;

        if (item != null) {
            TextView content   = (TextView) findViewById(R.id.content);
            TextView category  = (TextView) findViewById(R.id.category);
            TextView deadline  = (TextView) findViewById(R.id.deadline);
            final ImageView completed = (ImageView) findViewById(R.id.completed);

            Button complete = (Button) findViewById(R.id.button_item_complete);
            Button edit     = (Button) findViewById(R.id.button_item_edit);
            Button delete   = (Button) findViewById(R.id.button_item_delete);
            Button picture   = (Button) findViewById(R.id.button_item_pic);

            content.setText(item.getContent());
            category.setText(item.getCategoryName());
            deadline.setText(item.getDeadlineText());

            GoogleMap map = ((MapFragment) getFragmentManager()
                    .findFragmentById(R.id.map)).getMap();

            if (item.getImageName() != null) {
                ImageView pic = (ImageView) findViewById(R.id.picture);
                pic.setImageBitmap(BitmapFactory.decodeFile(item.getImageName()));
            }

            LatLng currentLocation = new LatLng(item.getLat(), item.getLng());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13));

            map.addMarker(new MarkerOptions()
                    .title(item.getContent())
                    .snippet(item.getCategoryName())
                    .position(currentLocation));

            if (item.getCompleted()) {
                completed.setImageResource(R.drawable.ic_action_good);
            }

            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.setCompleted(true);
                    item.save();
                    completed.setImageResource(R.drawable.ic_action_good);
                }
            });

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(current, EditItemActivity.class);
                    intent.putExtra("itemId", item.getId());
                    startActivity(intent);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.delete();
                    Intent intent = new Intent(current, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Item deleted!", Toast.LENGTH_SHORT).show();
                }
            });

            picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                    // start the image capture Intent
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Log.v("debug", fileUri.getPath());
                // Image captured and saved to fileUri specified in the Intent

                item.setImageName(fileUri.getPath());
                item.save();

                ImageView picture = (ImageView) findViewById(R.id.picture);
                picture.setImageBitmap(BitmapFactory.decodeFile(item.getImageName()));

                Toast.makeText(this, "Image saved to:\n" +
                        fileUri.getPath(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }
    }


    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_"+ timeStamp + ".jpg");

        return mediaFile;
    }

}
