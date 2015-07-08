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
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Search extends Fragment {
    private FloatingActionButton mFAB;
    private RelativeLayout mRoot;
    ListView searchList;
    public static ArrayList<Track> searchRes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search, container, false);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_search);
        mFAB = (FloatingActionButton) v.findViewById(R.id.fab);
        mFAB.setOnClickListener(mFabClickListener);
        searchList = (ListView) v.findViewById(R.id.listview_search);

        SearchTracks(getActivity());

        return v;
    }

    public static Search newInstance(String text) {

        Search f = new Search();
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

    private void SearchTracks(Context context){
        String url="";
        new HttpGetTask(url, context){
            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject response = new JSONObject(s);
                    if(response.getString("status").equals("success")) {
                        JSONArray jArray = new JSONArray(response.getString("results"));
                        searchRes = Track.parseJson(jArray);

                        trackAdapter tracksAdap = new trackAdapter(getActivity(), MainActivity.class, (ArrayList<trackInterface>) (ArrayList<?>) searchRes);
                        searchList.setAdapter(tracksAdap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}