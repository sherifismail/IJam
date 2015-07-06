package com.Example.iJam;

import java.util.ArrayList;

/**
 * Created by Mostafa on 6/29/2015.
 */
public class User {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private ArrayList<Track> tracks;

    public User(String username, String password, String fname, String lname){
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        tracks = null;
    }

    public void setFname(String name){
        fname = name;
    }

    public void setLname(String name){
        lname = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void addTrack(String trackName, int duration, String imgurl, ArrayList<String> tags, String instrument){
        Track temp = new Track(trackName, username, false, duration, imgurl, tags, instrument);
        tracks.add(temp);
    }

    public void jamOn(Track original, String instrument){
        Track temp = new Track(username, false, instrument, original);
        tracks.add(temp);
    }
}
