package com.example.iJam.fragments;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.iJam.activities.UploadTrackActivity;
import com.example.iJam.R;

public class BandsFragment extends Fragment {

    private FloatingActionButton mFAB;

    private RelativeLayout mRoot;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_bands, container, false);
        mRoot = (RelativeLayout) v.findViewById(R.id.root_activity_bands);
        mFAB = (FloatingActionButton) v.findViewById(R.id.toptracks_fab_add);
        mFAB.setOnClickListener(mFabClickListener);

        return v;
    }

    public static BandsFragment newInstance(String text) {

        BandsFragment f = new BandsFragment();
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

}