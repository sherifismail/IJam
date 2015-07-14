package com.Example.iJam.fragments;

//import android.app.Fragment;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.Example.iJam.R;
import com.Example.iJam.activities.JammingActivity;
import com.Example.iJam.models.Track;
import com.Example.iJam.network.NetworkManager;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by sherif on 7/12/2015.
 */
public class TrackDetailFragment extends Fragment {
    TextView txtAuthor, txtTitle, txtLikes, txtRating;
    ListView trackDetails;
    VideoView trackPlayer;
    MediaController mc;
    NetworkImageView imgTrack;
    private FloatingActionButton mFAB, fabLike;
    private RelativeLayout mRoot;
    Track myTrack;

    final ArrayList<String> list = new ArrayList<String>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_track_details, container, false);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_trackDetails);
        mFAB = (FloatingActionButton) v.findViewById(R.id.trackdetail_fab_jamover);
        mFAB.setOnClickListener(mFabClickListener);
        fabLike = (FloatingActionButton)v.findViewById(R.id.trackdetail_fab_like);
        fabLike.setOnClickListener(likeListener);
        trackDetails =(ListView)v.findViewById(R.id.trackdetail_lv_tracks);
        trackPlayer =(VideoView)v.findViewById(R.id.trackdetail_vp_player);
        imgTrack =(NetworkImageView)v.findViewById(R.id.trackdetail_img_testimage);

        myTrack = (Track) getActivity().getIntent().getSerializableExtra("track");

        Track myTrack = (Track) getActivity().getIntent().getSerializableExtra("track");

        try {
            final String title = myTrack.getTitle();
            final String likes = Integer.toString(myTrack.getLikes());
            final String rating = Double.toString(myTrack.getRating());
            final String uploader = myTrack.getUploader();
            final String tags = myTrack.getTags();
            final String instrument = myTrack.getInstrument();
            final String imgUrl = myTrack.getImgUrl();
            final String duration = Integer.toString(myTrack.getDuration());
            final String uploadDate = myTrack.getUpload_date();
            final String trackUrl = myTrack.getTrackUrl();
                    //ServerManager.getServerURL()+"/php6EBA.tmp.mp3";

            imgTrack.setImageUrl(imgUrl, NetworkManager.getInstance(getActivity()).getImageLoader());
            trackPlayer.setVideoURI(Uri.parse(trackUrl));

            String[] trackItems = new String[]{"Title: "+ title, "Likes Count: " + likes,
                    "Rating: " + rating, "Uploader:" + uploader,
                    "Tags: " + tags, "Instrument: " + instrument,
                    "Song Duration: " + duration, "Upload Date: " + uploadDate};
            for (int i = 0; i < trackItems.length; ++i) {
                list.add(trackItems[i]);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, trackItems);
            trackDetails.setAdapter(adapter);

            trackPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    // TODO Auto-generated method stub
                    int duration = mp.getDuration() / 1000;
                    int hours = duration / 3600;
                    int minutes = (duration / 60) - (hours * 60);
                    int seconds = duration - (hours * 3600) - (minutes * 60);
                    String formatted = String.format("%02d:%02d", minutes, seconds);
                    //Log.i("trackduration", formatted);
                    Toast.makeText(getActivity().getApplicationContext(), "duration is " + formatted, Toast.LENGTH_LONG).show();

                }
            });

            mc = new MediaController(getActivity());
            mc.setMediaPlayer(trackPlayer);
            trackPlayer.start();
            //int length = mc.getDuration();

            mc.setAnchorView(trackPlayer);
            trackPlayer.setMediaController(mc);

        }catch(Exception ee){
            ee.printStackTrace();
        }

        return v;
    }
    private View.OnClickListener likeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Tracked Liked!", Toast.LENGTH_SHORT).show();
        }
    };
    private View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i = new Intent(getActivity(),JammingActivity.class);
            i.putExtra("track", myTrack);
            startActivity(i);
            getActivity().finish();
        }
    };

    public static TrackDetailFragment newInstance(String text) {

        TrackDetailFragment f = new TrackDetailFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}
