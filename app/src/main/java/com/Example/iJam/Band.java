package com.Example.iJam;

import java.util.ArrayList;

/**
 * Created by Mostafa on 6/29/2015.
 */
public class Band {
    private int band_id;
    private String name;
    private ArrayList<User> members;
    private ArrayList<Track> tracks;

    public Band(String name){
        this.name = name;
        members = null;
        tracks = null;
    }

    public void addMember(User member){
        members.add(member);
    }

    public void addTrack(Track track){
        tracks.add(track);
    }
}