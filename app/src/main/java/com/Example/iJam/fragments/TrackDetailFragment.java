package com.Example.iJam.fragments;

//import android.app.Fragment;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.Example.iJam.R;
import com.Example.iJam.network.ServerManager;

import java.util.ArrayList;

/**
 * Created by sherif on 7/12/2015.
 */
public class TrackDetailFragment extends Fragment {
    TextView txtAuthor, txtTitle, txtLikes, txtRating;
    //ImageView imgTrack;
    ListView trackdetails;
    VideoView trackplayer;
    MediaController mc;
    FrameLayout imgtrack;
    Button playtrack;
    String formatted="",title,likes,rating,author;
    final ArrayList<String> list = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_track_details, container, false);

        trackdetails=(ListView)v.findViewById(R.id.track_detail_listview);
        trackplayer=(VideoView)v.findViewById(R.id.player);
        playtrack=(Button) v.findViewById(R.id.play);
        //imgTrack = (ImageView) findViewById(R.id.img_track);
        //imgTrack.setImageResource(R.drawable.x);
        imgtrack=(FrameLayout)v.findViewById(R.id.testimg);
        imgtrack.setBackgroundResource(R.drawable.x);
        /*int hours = time / 3600;
        int minutes = (time / 60) - (hours * 60);
        int seconds = time - (hours * 3600) - (minutes * 60) ;
        String trackdurationmin=String.valueOf(minutes);
        String trackduration=trackdurationmin+":"+String.valueOf(seconds);*/
        String trackurl= ServerManager.getServerURL()+"/test_track.mp3";
        try {
            title = getActivity().getIntent().getStringExtra("title");
            likes = getActivity().getIntent().getStringExtra("likes");
            rating = getActivity().getIntent().getStringExtra("rating");
            author = getActivity().getIntent().getStringExtra("author");

            trackplayer.setVideoURI(Uri.parse(trackurl));

            int time=trackplayer.getDuration();
            String tracktime=String.valueOf(time);
            //Log.i("trackduration", tracktime);
            trackplayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    int duration = mp.getDuration() / 1000;
                    int hours = duration / 3600;
                    int minutes = (duration / 60) - (hours * 60);
                    int seconds = duration - (hours * 3600) - (minutes * 60);
                   formatted = String.format("%02d:%02d", minutes, seconds);
                    Log.i("trackduration", formatted);
                    Toast.makeText(getActivity().getApplicationContext(), "duration is " + formatted, Toast.LENGTH_LONG).show();
                    String[] trackitems = new String[]{title, likes, rating, author,"#solo#musica#piano", "Piano",formatted,"11-07-2015"};
                    for (int i = 0; i < trackitems.length; ++i) {
                        list.add(trackitems[i]);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, trackitems);
                    trackdetails.setAdapter(adapter);
                }
            });

            playtrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    trackplayer.start();

                }
            });
            mc=new MediaController(getActivity());
            mc.setMediaPlayer(trackplayer);

            //int length = mc.getDuration();

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
        }catch(Exception ee){
            ee.printStackTrace();
        }


        return v;
    }

    public static TrackDetailFragment newInstance(String text) {

        TrackDetailFragment f = new TrackDetailFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    }
