package com.example.iJam.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.iJam.activities.MainActivity;
import com.example.iJam.activities.SignInActivity;
import com.example.iJam.network.HttpGetTask;
import com.example.iJam.R;
import com.example.iJam.network.ServerManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {
    ListView listV;
    ImageView profileimage;
    //LinearLayout linearList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);

        String[] profitems = new String[]{"Profile", "Settings", "My Tracks", "About us", "Sign Out"};
        listV = (ListView) v.findViewById(R.id.listView2);

        final ArrayList<String> list = new ArrayList<String>();
        profileimage = (ImageView) v.findViewById(R.id.imageView2);
        profileimage.setImageResource(R.drawable.x);

        TextView userName = (TextView) v.findViewById(R.id.tv_profilename);
        final TextView userShows = (TextView) v.findViewById(R.id.tv_profiletracks);

        userName.setText(MainActivity.user.getUser_name());

        new HttpGetTask(ServerManager.getServerURL()+"/tracks/my_tracks.php?uid="+MainActivity.user.getUser_id(), getActivity()){
            @Override
            protected void onPostExecute(String s) {
                try {
                    JSONObject response = new JSONObject(s);
                    if(response.getString("status").equals("success"))
                        userShows.setText(response.getString("count") + " tracks");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }.execute();


        for (int i = 0; i < profitems.length; ++i) {
            list.add(profitems[i]);
        }
        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                } else if (position == 1) {

                } else if (position == 2) {

                } else if (position == 3) {

                } else if (position == 4) {
                    Intent intent = new Intent(getActivity(), SignInActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });

        listV.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, profitems);
        listV.setAdapter(adapter);

        return v;
    }

    public static ProfileFragment newInstance(String text) {

        ProfileFragment f = new ProfileFragment();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


}