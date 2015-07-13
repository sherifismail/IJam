package com.Example.iJam.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mostafa on 6/29/2015.
 */
public class User implements Serializable {
    //private int user_id;
    private String user_name;
    private String password;
    private String first_name;
    private String last_name;
    private String imgUrl;
    private String email;
    private ArrayList<Track> tracks;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User(/*int user_id,*/ String user_name, String password, String fname,
                String lname, String imgUrl, String email){
        //this.user_id = user_id;
        this.user_name = user_name;
        this.password = password;
        this.first_name = fname;
        this.last_name = lname;
        this.imgUrl = imgUrl;
        this.email = email;
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

    /*public void addTrack(String trackName, int duration, String imgurl, String tags, String instrument){
        Track temp = new Track(trackName, user_name, false, duration, imgurl, tags, instrument);
        tracks.add(temp);
    }*/

    public void jamOn(Track original, String instrument){
        Track temp = new Track(user_name, false, instrument, original);
        tracks.add(temp);
    }
    public JSONObject toJSONObject() throws JSONException{
        JSONObject userOb = new JSONObject();

        userOb.put("user_name", user_name);
        userOb.put("password", password);
        userOb.put("first_name", first_name);
        userOb.put("last_name", last_name);
        userOb.put("email", email);
        userOb.put("img_url", imgUrl);

        return userOb;
    }
}
