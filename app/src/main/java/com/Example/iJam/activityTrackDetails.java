package com.Example.iJam;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class activityTrackDetails extends ActionBarActivity {
    TextView txtAuthor;
    TextView txtTitle;
    TextView txtLikes;
    TextView txtRating;
    ImageView trackimage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_details);

        trackimage = (ImageView) findViewById(R.id.img_track);
        trackimage.setImageResource(R.drawable.x);;

        try {
            String title = getIntent().getStringExtra("title");
            String likes = getIntent().getStringExtra("likes");
            String rating = getIntent().getStringExtra("rating");
            String author = getIntent().getStringExtra("author");

            txtLikes = (TextView) findViewById(R.id.track_txt_likes);
            txtRating = (TextView) findViewById(R.id.track_txt_rating);
            txtTitle = (TextView) findViewById(R.id.track_txt_title);
            txtAuthor = (TextView) findViewById(R.id.track_txt_author);

            txtRating.setText(rating);
            txtLikes.setText(likes);
            txtAuthor.setText(author);
            txtTitle.setText(title);

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
