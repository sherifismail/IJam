package com.Example.iJam.fragments;
//import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.Example.iJam.activities.MainTrackDetailActivity;
import com.Example.iJam.activities.TrackDetailsActivity;
import com.Example.iJam.activities.UploadTrackActivity;
import com.Example.iJam.adapters.TrackAdapter;
import com.Example.iJam.network.HttpGetTask;
import com.Example.iJam.interfaces.TrackInterface;
import com.Example.iJam.models.Track;
import com.Example.iJam.R;
import com.Example.iJam.network.ServerManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TopTracksFragment extends Fragment {

    private FloatingActionButton mFAB;
    private RelativeLayout mRoot;
    private ListView lvTracks;
    public static ArrayList<Track> topTracks = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_top_tracks, container, false);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_second);
        mFAB = (FloatingActionButton) v.findViewById(R.id.toptracks_fab_add);
        mFAB.setOnClickListener(mFabClickListener);
        lvTracks = (ListView) v.findViewById(R.id.toptracks_lv_tracks);

        getTopTracks(getActivity());
        return v;
    }

    public static TopTracksFragment newInstance(String text) {

        TopTracksFragment f = new TopTracksFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    private View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(getActivity(),UploadTrackActivity.class);
            startActivity(i);
        }
    };

    private void getTopTracks(Context context){
        new HttpGetTask(ServerManager.getServerURL()+"/tracks/top_tracks.php",context){
            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject response = new JSONObject(s);
                    if(response.getString("status").equals("success")) {
                        JSONArray jArray = new JSONArray(response.getString("results"));
                        topTracks = Track.parseJson(jArray);

                        TrackAdapter tracksAdap = new TrackAdapter(getActivity(), MainTrackDetailActivity.class, (ArrayList<TrackInterface>) (ArrayList<?>) topTracks);
                        lvTracks.setAdapter(tracksAdap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}