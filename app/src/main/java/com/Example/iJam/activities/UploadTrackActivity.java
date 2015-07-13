package com.Example.iJam.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Example.iJam.R;
import com.Example.iJam.network.HttpImageTask;
import com.Example.iJam.network.HttpTrackTask;
import com.Example.iJam.network.InsertTrackTask;
import com.Example.iJam.network.ServerManager;
import com.Example.iJam.network.HttpImageTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UploadTrackActivity extends AppCompatActivity implements View.OnClickListener{
    Button btStop, btRecord, btUpload, btStorage;
    ImageView imgTrack;
    EditText etName, etInstrument, etTags;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    boolean ready = false;
    String img_url, track_url, track_duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_track);

        Intent caller = getIntent();
        outputFile = caller.getStringExtra("filename");

        imgTrack = (ImageView)findViewById(R.id.trackupload_img_trackimage);
        btUpload = (Button) findViewById(R.id.trackupload_bt_upload);
        etName = (EditText) findViewById(R.id.trackupload_et_name);
        etInstrument = (EditText) findViewById(R.id.trackupload_et_instrument);
        etTags = (EditText) findViewById(R.id.trackupload_et_tags);

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);

        btUpload.setOnClickListener(this);
        imgTrack.setOnClickListener(this);
        //btStorage.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recordtrack, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void performCrop(Uri picUri) {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    Uri imageUri = data.getData();
                    performCrop(imageUri);
                    break;
                case 2:
                    //get the returned data
                    Bundle extras = data.getExtras();
                    //get the cropped bitmap
                    Bitmap thePic = extras.getParcelable("data");
                    imgTrack.setImageBitmap(thePic);
                    break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.trackupload_img_trackimage:
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                break;

            case R.id.trackupload_bt_upload:
                Upload();
                break;
        }
    }

    private void Upload(){
        final String name = etName.getText().toString().trim();
        final String instrument = etInstrument.getText().toString().trim();
        final String tags = etTags.getText().toString().trim();

        //----------------------------------------------------------------------------------------------------------------
        //AFTER VALIDATION
        if(name.equals("") || instrument.equals("") || tags.equals(""))
            Toast.makeText(getApplicationContext() ,"one or more fields are left empty!", Toast.LENGTH_SHORT).show();
        else {
            if(outputFile==null)
                Toast.makeText(getApplicationContext() ,"no track is selected for uploading", Toast.LENGTH_SHORT).show();
            else {

                Bitmap img = ((BitmapDrawable) imgTrack.getDrawable()).getBitmap();

                new HttpImageTask(ServerManager.getServerURL() + "/tracks/upload_image.php", getApplicationContext()) {
                    @Override
                    protected void onPostExecute(String s) {
                        if (s.equals(""))
                            return;
                        try {
                            JSONObject response = new JSONObject(s);
                            if (response.getString("status").equals("success")) {
                                img_url = ServerManager.getServerURL() + "/tracks/" + response.getString("url");
                                Toast.makeText(ctx, img_url, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ctx, response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //---------------------------------------------------------------------------------------------
                        //UPLOAD TRACK TO DATABASE
                        if (ready)
                            UploadTrack(name, track_duration, instrument, tags, img_url, track_url);
                        else
                            ready = true;
                    }
                }.execute(img);

                new HttpTrackTask(ServerManager.getServerURL() + "/tracks/upload_track.php", getApplicationContext()) {
                    @Override
                    protected void onPostExecute(String s) {
                        if (s.equals(""))
                            return;
                        try {
                            JSONObject response = new JSONObject(s);
                            if (response.getString("status").equals("success")) {
                                track_url = ServerManager.getServerURL() + "/tracks/" + response.getString("url");
                                Toast.makeText(ctx, track_url, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ctx, response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //---------------------------------------------------------------------------------------------
                        //UPLOAD TRACK TO DATABASE
                        if (ready)
                            UploadTrack(name, track_duration, instrument, tags, img_url, track_url);
                        else
                            ready = true;
                    }
                }.execute(outputFile);
            }
        }
    }

    private void UploadTrack(String name, String duration, String instrument, String tags, String img_url, String track_url){
        JSONObject json_track = new JSONObject();
        try {
            json_track.put("name", name);
            json_track.put("user_name", MainActivity.user.getUser_name());
            json_track.put("band_name", JSONObject.NULL);
            json_track.put("duration", 24);
            json_track.put("upload_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            json_track.put("instrument", instrument);
            json_track.put("tags", tags);
            json_track.put("img_url", img_url);
            json_track.put("track_url", track_url);

            new InsertTrackTask(getApplicationContext()) {
                @Override
                protected void onPostExecute(String s) {
                    try {
                        JSONObject response = new JSONObject(s);
                        String status = response.getString("status");
                        if (status.equals("fail")) {
                            Toast.makeText(ctx, "Failed to upload track! " + response.getString("error"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute(json_track);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
