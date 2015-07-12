package com.Example.iJam.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.Example.iJam.R;
import com.Example.iJam.network.ServerManager;

import java.util.ArrayList;


public class TrackDetailsActivity extends AppCompatActivity {
    TextView txtAuthor, txtTitle, txtLikes, txtRating;
    //ImageView imgTrack;
    ListView trackdetails;
    VideoView trackplayer;
    MediaController mc;
    FrameLayout imgtrack;
//Button playtrack;
    final ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);
        trackdetails=(ListView)findViewById(R.id.trackdetail_lv_tracks);
        trackplayer=(VideoView)findViewById(R.id.trackdetail_vp_player);
        //playtrack=(Button) findViewById(R.id.trackdetail_bt_playtrack);
        //imgTrack = (ImageView) findViewById(R.id.img_track);
        //imgTrack.setImageResource(R.drawable.x);
        imgtrack=(FrameLayout)findViewById(R.id.trackdetail_img_testimage);
        imgtrack.setBackgroundResource(R.drawable.x);
        String trackurl=ServerManager.getServerURL()+"/test_track.mp3";
        try {

            String title = getIntent().getStringExtra("title");
            String likes = getIntent().getStringExtra("likes");
            String rating = getIntent().getStringExtra("rating");
            String author = getIntent().getStringExtra("author");
            String[] trackitems = new String[]{title, likes, rating, author,"#solo#musica#piano", "Piano","1:54","11-07-2015"};
            for (int i = 0; i < trackitems.length; ++i) {
                list.add(trackitems[i]);
            }
            trackplayer.setVideoURI(Uri.parse(trackurl));
           /* playtrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trackplayer.start();
                }
            });*/
           mc=new MediaController(this);
            mc.setMediaPlayer(trackplayer);
            trackplayer.start();
            mc.setAnchorView(trackplayer);
            trackplayer.setMediaController(mc);

           // mc.setPadding(0, 0, 0, 1200);
           /* txtLikes = (TextView) findViewById(R.id.track_txt_likes);
            txtRating = (TextView) findViewById(R.id.track_txt_rating);
            txtTitle = (TextView) findViewById(R.id.track_txt_title);
            txtAuthor = (TextView) findViewById(R.id.track_txt_author);

            txtRating.setText(rating);
            txtLikes.setText(likes);
            txtAuthor.setText(author);
            txtTitle.setText(title);
*/
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, trackitems);
            trackdetails.setAdapter(adapter);
        }catch(Exception ee){
            ee.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_track_details, menu);
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
