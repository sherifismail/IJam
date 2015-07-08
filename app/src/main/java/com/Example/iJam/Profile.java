package com.Example.iJam;

import android.app.ListFragment;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class Profile extends Fragment {
ListView listV;
    ImageView profileimage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_profile, container, false);
        String[] profitems=new String[]{"Profile","Settings","My Tracks","About us","Sign Out"};
        listV=(ListView)v.findViewById(R.id.listView2);
        final ArrayList<String> list = new ArrayList<String>();
profileimage=(ImageView)v.findViewById(R.id.imageView2);
        profileimage.setImageResource(R.drawable.x);
        for (int i = 0; i < profitems.length; ++i) {
            list.add(profitems[i]);
        }
listV.setOnItemClickListener(new  AdapterView.OnItemClickListener(){

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){

        }
        else if (position==1){

        }
        else if(position==2){

        }
        else if (position==3){

        }

        else if (position==4){
        Intent intent=new Intent(getActivity(),signin.class);
        startActivity(intent);
            getActivity().finish();
        }
    }
});

        listV.setScrollContainer(false);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, profitems);
        listV.setAdapter(adapter);
        //TextView tv = (TextView) v.findViewById(R.id.tvFragSecond);
        //tv.setText(getArguments().getString("msg"));

        return v;
    }

    public static Profile newInstance(String text) {

        Profile f = new Profile();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }



}