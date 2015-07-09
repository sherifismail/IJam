package com.example.iJam.activity;

import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iJam.network.InsertTrackTask;
import com.example.iJam.R;
import com.example.iJam.network.ServerManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UploadTrackActivity extends AppCompatActivity implements View.OnClickListener{
    Button btStop, btRecord, btUpload;
    ImageView imgTrack;
    EditText etName, etInstrument, etTags;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_track);

        btRecord = (Button)findViewById(R.id.trackupload_bt_startrecord);
        btStop = (Button)findViewById(R.id.trackupload_bt_stoprecord);
        imgTrack = (ImageView)findViewById(R.id.trackupload_img_trackimage);
        btUpload = (Button) findViewById(R.id.trackupload_bt_upload);
        etName = (EditText) findViewById(R.id.trackupload_et_name);
        etInstrument = (EditText) findViewById(R.id.trackupload_et_instrument);
        etTags = (EditText) findViewById(R.id.trackupload_et_tags);

        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        btUpload.setOnClickListener(this);
        btRecord.setOnClickListener(this);
        btStop.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.trackupload_bt_stoprecord:
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder = null;

                btStop.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully", Toast.LENGTH_LONG).show();
                break;
            case R.id.trackupload_bt_startrecord:
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                    Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
                    btRecord.setEnabled(false);
                    btStop.setEnabled(true);
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case R.id.trackupload_bt_upload:
                String name = etName.getText().toString().trim();
                String instrument = etInstrument.getText().toString().trim();
                String tags = etTags.getText().toString().trim();
                JSONObject json_track = new JSONObject();
                boolean val = false;
                try {
                    json_track.put("name", name);
                    json_track.put("band", val);
                    json_track.put("band_id", 4);
                    json_track.put("user_id", MainActivity.user.getUser_id());
                    json_track.put("instrument", instrument);
                    json_track.put("duration", 24);
                    json_track.put("upload_date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                    json_track.put("tags", tags);
                    json_track.put("img_url", "http:/ahourl");
                    json_track.put("track_url", "http:/wahokamanwa7ed");

                    new InsertTrackTask(getApplicationContext()) {
                        @Override
                        protected void onPostExecute(String s) {
                            try {
                                JSONObject response = new JSONObject(s);
                                String status = response.getString("status");
                                ServerManager.setServerStatus(status);
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
                break;
        }
    }
}
