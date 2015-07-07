package com.Example.iJam;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Upload extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_tracks);

        final EditText et_name = (EditText) findViewById(R.id.et_trackname);
        final EditText et_insturment = (EditText) findViewById(R.id.et_trackinstrument);
        final EditText et_tags = (EditText) findViewById(R.id.et_tracktags);
        Button btn_upload = (Button) findViewById(R.id.btn_uploadtrack);

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString().trim();
                String instrument = et_insturment.getText().toString().trim();
                String tags = et_tags.getText().toString().trim();
                JSONObject json_track = new JSONObject();
                boolean val =false;
                try{
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
