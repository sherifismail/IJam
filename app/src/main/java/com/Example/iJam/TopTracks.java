package com.Example.iJam;
//import android.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TopTracks extends Fragment {

    private FloatingActionButton mFAB;
    private RelativeLayout mRoot;
    private ListView lvTracks;
    public static ArrayList<Track> topTracks = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_top_tracks, container, false);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_second);
        mFAB = (FloatingActionButton) v.findViewById(R.id.fab);
        mFAB.setOnClickListener(mFabClickListener);
        lvTracks = (ListView) v.findViewById(R.id.lvTracks);

        getTopTracks(getActivity());
        return v;
    }

    public static TopTracks newInstance(String text) {

        TopTracks f = new TopTracks();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    private View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(getActivity(),Upload.class);
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

                        trackAdapter tracksAdap = new trackAdapter(getActivity(), MainActivity.class, (ArrayList<trackInterface>) (ArrayList<?>) topTracks);
                        lvTracks.setAdapter(tracksAdap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}