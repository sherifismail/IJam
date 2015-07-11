package com.Example.iJam.models;

import java.util.ArrayList;

/**
 * Created by Mostafa on 6/29/2015.
 */
public class User {
    //private int user_id;
    private String user_name;
    private String password;
    private String first_name;
    private String last_name;
    private ArrayList<Track> tracks;

    public User(/*int user_id,*/ String user_name, String password, String fname, String lname){
        //this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.first_name = fname;
        this.last_name = lname;
        tracks = null;
    }

    /*public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }*/

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setFirst_name(String name){
        first_name = name;
    }

    public void setLast_name(String name){
        last_name = name;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void addTrack(String trackName, int duration, String imgurl, ArrayList<String> tags, String instrument){
        Track temp = new Track(trackName, user_name, false, duration, imgurl, tags, instrument);
        tracks.add(temp);
    }

    public void jamOn(Track original, String instrument){
        Track temp = new Track(user_name, false, instrument, original);
        tracks.add(temp);
    }
}
