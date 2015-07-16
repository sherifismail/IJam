package com.Example.iJam.fragments;

//import android.app.Fragment;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.Example.iJam.R;
import com.Example.iJam.activities.RecordActivity;
import com.Example.iJam.models.MyAudioManager;
import com.Example.iJam.models.Track;
import com.Example.iJam.network.NetworkManager;
import com.android.volley.toolbox.NetworkImageView;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by sherif on 7/12/2015.
 */
public class TrackDetailFragment extends Fragment {

    ListView trackDetails;
    NetworkImageView imgTrack;
    FloatingActionButton fabJam, fabLike, fabRate;
    RelativeLayout mRoot;
    Track myTrack;
    private AudioTrack track = null;

    final ArrayList<String> list = new ArrayList<String>();


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_track_details, container, false);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_trackDetails);
        fabJam = (FloatingActionButton) v.findViewById(R.id.trackdetail_fab_jamover);
        fabJam.setOnClickListener(mFabClickListener);
        fabLike = (FloatingActionButton)v.findViewById(R.id.trackdetail_fab_like);
        fabLike.setOnClickListener(likeListener);
        fabRate = (FloatingActionButton)v.findViewById(R.id.trackdetail_fab_rate);
        fabRate.setOnClickListener(rateListener);
        trackDetails =(ListView)v.findViewById(R.id.trackdetail_lv_tracks);
        imgTrack =(NetworkImageView)v.findViewById(R.id.trackdetail_img_testimage);

        myTrack = (Track) getActivity().getIntent().getSerializableExtra("track");

        Track myTrack = (Track) getActivity().getIntent().getSerializableExtra("track");

        try {
            final String title = myTrack.getUser_name();
            final String likes = Integer.toString(myTrack.getLikes());
            final String rating = Double.toString(myTrack.getRating());
            final String uploader = myTrack.getUploader();
            final String tags = myTrack.getTags();
            final String instrument = myTrack.getInstrument();
            final String imgUrl = myTrack.getImgUrl();
            final String duration = Integer.toString(myTrack.getDuration());
            final String uploadDate = myTrack.getUpload_date();
            final String trackUrl = myTrack.getTrackUrl();

            imgTrack.setImageUrl(imgUrl, NetworkManager.getInstance(getActivity()).getImageLoader());
            //InitAudio();

            String[] trackItems = new String[]{"Title: "+ title, "Likes Count: " + likes,
                    "Rating: " + rating, "Uploader:" + uploader,
                    "Tags: " + tags, "Instrument: " + instrument,
                    "Song Duration: " + duration, "Upload Date: " + uploadDate};
            for (int i = 0; i < trackItems.length; ++i) {
                list.add(trackItems[i]);
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, trackItems);
            trackDetails.setAdapter(adapter);

        }catch(Exception ee){
            ee.printStackTrace();
        }

        return v;
    }

    private View.OnClickListener rateListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording";
            MyAudioManager.InitAudio(outputFile);
            track.play();
            /*EditText etRate = new EditText(getActivity());
            AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
            myDialog.setTitle("Rate Track")
                    .setView(etRate)
                    .setMessage("Range 0-5")
                    .setPositiveButton("Rate", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            AlertDialog dialog = myDialog.create();
            dialog.show();*/
        }
    };
    private View.OnClickListener likeListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            /*new HttpGetTask(ServerManager.getServerURL()+"/tracks/like.php?id="+myTrack.getID(), getActivity()){
                @Override
                protected void onPostExecute(String s) {
                    try {
                        JSONObject response = new JSONObject(s);
                        if (response.getString("status").equals("success"))
                            Toast.makeText(ctx, "Track liked", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(ctx, "Like failed" + response.getString("error"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.execute();*/
            MyAudioManager.mixFiles(Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording",
                                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording2",
                                    Environment.getExternalStorageDirectory().getAbsolutePath() + "/mix");
        }
    };
    private View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            /*
            Intent i = new Intent(getActivity(),JammingActivity.class);
            i.putExtra("track", myTrack);
            startActivity(i);
            getActivity().finish();*/
            MyAudioManager.InitAudio(Environment.getExternalStorageDirectory().getAbsolutePath() + "/mix");
            track.play();
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
