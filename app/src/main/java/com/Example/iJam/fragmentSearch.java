package com.Example.iJam;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class fragmentSearch extends Fragment {
    private FloatingActionButton mFAB;
    private RelativeLayout mRoot;
    ListView searchList;
    EditText search_name;
    public static ArrayList<Track> searchRes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search, container, false);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_search);
        mFAB = (FloatingActionButton) v.findViewById(R.id.fab);
        mFAB.setOnClickListener(mFabClickListener);
        search_name = (EditText) v.findViewById(R.id.et_searchTracks);
        Button search = (Button) v.findViewById(R.id.btn_searchTracks);
        searchList = (ListView) v.findViewById(R.id.listview_search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = search_name.getText().toString().trim();
                SearchTracks(name, getActivity());
            }
        });

        return v;
    }

    public static fragmentSearch newInstance(String text) {

        fragmentSearch f = new fragmentSearch();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    private View.OnClickListener mFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i=new Intent(getActivity(),activityUpload.class);
            startActivity(i);
        }
    };

    private void SearchTracks(String val, Context context){

        StringBuilder paramsBuilder = new StringBuilder();
        try{
            paramsBuilder.append("?name=");
            paramsBuilder.append(URLEncoder.encode(val, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        String encodedParams = paramsBuilder.toString();
        String APIurl= ServerManager.getServerURL()+"/tracks/search_tracks.php"+encodedParams;

        new HttpGetTask(APIurl, context){
            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject response = new JSONObject(s);
                    if(response.getString("status").equals("success")) {
                        JSONArray jArray = new JSONArray(response.getString("results"));
                        searchRes = Track.parseJson(jArray);

                        trackAdapter tracksAdap = new trackAdapter(getActivity(), activityTrackDetails.class, (ArrayList<trackInterface>) (ArrayList<?>) searchRes);
                        searchList.setAdapter(tracksAdap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}