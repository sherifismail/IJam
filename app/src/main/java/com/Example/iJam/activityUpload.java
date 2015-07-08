package com.Example.iJam;

import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class activityUpload extends ActionBarActivity {
    Button stop,record;
    private MediaRecorder myAudioRecorder;
    private String outputFile = null;
    ImageView trackimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_tracks);
        record=(Button)findViewById(R.id.button5);
        stop=(Button)findViewById(R.id.button6);
        final EditText et_name = (EditText) findViewById(R.id.et_trackname);
        final EditText et_insturment = (EditText) findViewById(R.id.et_trackinstrument);
        final EditText et_tags = (EditText) findViewById(R.id.et_tracktags);
        Button btn_upload = (Button) findViewById(R.id.btn_uploadtrack);
        ProgressBar recordprogress=(ProgressBar) findViewById(R.id.progressBar);
        recordprogress.setVisibility(View.INVISIBLE);
        stop.setEnabled(false);
        trackimage=(ImageView)findViewById(R.id.imageView3);
        trackimage.setImageResource(R.drawable.x);
        outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        myAudioRecorder=new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString().trim();
                String instrument = et_insturment.getText().toString().trim();
                String tags = et_tags.getText().toString().trim();
                JSONObject json_track = new JSONObject();
                boolean val = false;
                try {
                    json_track.put("name", name);
                    json_track.put("band", val);
                    json_track.put("band_id", 4);
                    json_track.put("user_id", 3);
                    json_track.put("instrument", instrument);
                    json_track.put("duration", 24);
                    json_track.put("upload_date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
                    json_track.put("tags", tags);
                    json_track.put("img_url", "http:/ahourl");
                    json_track.put("track_url", "http:/wahokamanwa7ed");

                    InsertTrackTask insertTrackTask = new InsertTrackTask(getApplicationContext());
                    insertTrackTask.execute(json_track);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    myAudioRecorder.prepare();
                    myAudioRecorder.start();
                }

                catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                record.setEnabled(false);
                stop.setEnabled(true);

                Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                myAudioRecorder  = null;

                stop.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Audio recorded successfully",Toast.LENGTH_LONG).show();
            }
        });
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
}
