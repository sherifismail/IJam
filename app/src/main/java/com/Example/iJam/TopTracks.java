package com.Example.iJam;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TopTracks extends Fragment {

    private FloatingActionButton mFAB;

    private RelativeLayout mRoot;

    public static ArrayList<Track> topTracks = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_top_tracks, container, false);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_second);
        mFAB = (FloatingActionButton) v.findViewById(R.id.fab);
        mFAB.setOnClickListener(mFabClickListener);

        ArrayList<Track> tracksList = new ArrayList<Track>();
        String strJ = "[\n" +
                "    {\n" +
                "        \"Title\":\"Title1\",\n" +
                "        \"Uploader\":\"Uploader1\",\n" +
                "        \"Duration\":90,\n" +
                "        \"Likes\": 120,\n" +
                "        \"Rating\":3,\n" +
                "        \"Instrument\":\"Violin\",\n" +
                "        \"Tags\":\n" +
                "        [\n" +
                "            {\n" +
                "                \"Tag0\":\"#Tag1\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"Tag1\":\"Tag2\"\n" +
                "            }\n" +
                "      ]\n" +
                "    }\n" +
                "]";

        try {
            JSONArray j = new JSONArray(strJ);
            tracksList = Track.parseJson(j);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView lvTracks = (ListView) v.findViewById(R.id.lvTracks);
        trackAdapter tracksAdap = new trackAdapter(getActivity(), MainActivity.class, (ArrayList<trackInterface>) (ArrayList<?>) tracksList);
        lvTracks.setAdapter(tracksAdap);
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
            Intent i=new Intent(getActivity(),UploadTracks.class);
            startActivity(i);
        }
    };
    public void getTopTracks(Context context){
        JSONObject ob = new JSONObject();
        new HttpPostTask(ServerManager.getServerURL()+"/tracks/top_tracks.php",context){
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONArray j = new JSONArray(s);
                    topTracks = Track.parseJson(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute(ob);
    }
}