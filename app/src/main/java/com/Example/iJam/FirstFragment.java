package com.Example.iJam;
//import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;


public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_first_fragment, container, false);

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
        myAdapter tracksAdap = new myAdapter(getActivity(), MainActivity.class, (ArrayList<myInterface>) (ArrayList<?>) tracksList);
        lvTracks.setAdapter(tracksAdap);
        return v;
    }

    public static FirstFragment newInstance(String text) {

        FirstFragment f = new FirstFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
}