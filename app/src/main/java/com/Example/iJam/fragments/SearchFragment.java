package com.Example.iJam.fragments;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class SearchFragment extends Fragment {
    private FloatingActionButton mFAB;
    private RelativeLayout mRoot;
    ListView searchList;
    EditText search_name;
    public static ArrayList<Track> searchRes = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_search, container, false);
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_search);
        mFAB = (FloatingActionButton) v.findViewById(R.id.toptracks_fab_add);
        mFAB.setOnClickListener(mFabClickListener);
        search_name = (EditText) v.findViewById(R.id.search_et_searchTracks);

        //Button search = (Button) v.findViewById(R.id.btn_searchTracks);
        searchList = (ListView) v.findViewById(R.id.search_lv_search);
        search_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String name = search_name.getText().toString().trim();
                    SearchTracks(name, getActivity());
                    return true;
                }
                return false;
            }
        });

/*
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = search_name.getText().toString().trim();
                SearchTracks(name, getActivity());
            }
        });*/

        return v;
    }

    public static SearchFragment newInstance(String text) {

        SearchFragment f = new SearchFragment();
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

                        TrackAdapter tracksAdap = new TrackAdapter(getActivity(), MainTrackDetailActivity.class, (ArrayList<TrackInterface>) (ArrayList<?>) searchRes);
                        searchList.setAdapter(tracksAdap);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
    }
}