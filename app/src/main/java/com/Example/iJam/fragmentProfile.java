package com.Example.iJam;

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

import java.util.ArrayList;

public class fragmentProfile extends Fragment {
    ListView listV;
    ImageView profileimage;
    //LinearLayout linearList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);
        String[] profitems = new String[]{"fragmentProfile", "Settings", "My Tracks", "About us", "Sign Out"};
        listV = (ListView) v.findViewById(R.id.listView2);
        //linearList = (LinearLayout) v.findViewById(R.id.linearLayout);
        //ListAdapter testlist=
        final ArrayList<String> list = new ArrayList<String>();
        profileimage = (ImageView) v.findViewById(R.id.imageView2);
        profileimage.setImageResource(R.drawable.x);

        for (int i = 0; i < profitems.length; ++i) {
            //View item=
            //linearList.addView(testlist,null,null);
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
                    Intent intent = new Intent(getActivity(), activitySingIn.class);
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
        //listV.setScrollContainer(false);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, profitems);
        listV.setAdapter(adapter);
        /*
        for(int i = 0; i < adapter.getCount(); i++) {
            View item = adapter.getView(i, null, null);
            linearList.addView(item);
        }*/
        //TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
        //tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static fragmentProfile newInstance(String text) {

        fragmentProfile f = new fragmentProfile();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }


}