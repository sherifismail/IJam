package com.Example.iJam.models;

import com.Example.iJam.interfaces.TrackInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Khodary on 7/5/15.
 */
public class Track implements TrackInterface, Serializable {
    private String title;                //track name
    private String uploader;            //track auploader name
    //private String band;               //true it the uploader is band
    private int id;                //track id
    private int duration;               //in seconds
    private String imageUrl;              //image source location
    private String trackUrl;
    private String upload_date;           //track upload date
    private String tags;     //track tags
    private int ancestor;             //ID of ancestor track, null if solo
    private ArrayList<Track> children;          //ID of children tracks, empty if last jam
    //private ArrayList<String> contributors;         //names of contributing users
    //private ArrayList<String> instruments;          //names of instrucments involved
    private String instrument;
    private int likes;                  //number of likes
    private double rating;
    private int raters;

    public Track(String title, String uploader, int id, int duration, String imageUrl, String trackUrl,
                 String upload_date, String tags, int ancestor,
                 String instrument, int likes, double rating) {
        this.title = title;
        this.id = id;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.trackUrl = trackUrl;
        this.upload_date = upload_date;
        this.tags = tags;
        this.ancestor = ancestor;
        this.instrument = instrument;
        this.likes = likes;
        this.rating = rating;
        this.uploader = uploader;
    }

    public Track(String uploader, boolean band, String instrument, Track ancestor) {
        this.title = ancestor.title;
        this.uploader = uploader;
        //this.band = band;
        //trackID = ++id;
        this.duration = ancestor.duration;
        this.tags = ancestor.tags;
        this.ancestor = ancestor.getID();
        children = null;
        //contributors = ancestor.contributors;
        //contributors.add(ancestor.uploader);
        this.instrument = instrument;
        likes = 0;
        rating = 0;
        raters = 0;

        ancestor.children.add(this);
    }

    public Track() {
        title = null;
        uploader = null;
        //band = 0;
        id = 0;
        duration = 0;
        imageUrl = null;
        upload_date = null;
        tags = null;
        ancestor = 0;
        children = null;
        //contributors;
        //instruments;
        instrument = null;
        likes = 0;
        rating = 0.0;
        raters = 0;
    }

    public static JSONArray toJsonArray(ArrayList<Track> tracks) throws JSONException {
        JSONArray trackArray = new JSONArray();
        for (Track track : tracks) {
            trackArray.put(track.toJSONObject());
        }
        return trackArray;
    }

    public JSONObject toJSONObject() throws JSONException {
        JSONObject trackOb = new JSONObject();
        trackOb.put("name", this.getTitle());
        trackOb.put("imageurl", this.getImageUrl());
        trackOb.put("author", this.getUploader());
        trackOb.put("duration", this.getDuration());
        trackOb.put("tags", this.getTags());
        trackOb.put("likes", this.getLikes());
        trackOb.put("rating", this.getRating());
        trackOb.put("instrument", this.getInstrument());

        return trackOb;
    }

    public static ArrayList<Track> parseJson(JSONArray jArray) throws JSONException {
        ArrayList<Track> myTracks = new ArrayList<>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject ob = jArray.getJSONObject(i);

            int duration = ob.getInt("duration");
            int likes = ob.getInt("likes");
            int id = ob.getInt("track_id");
            double rating = ob.getDouble("rating");
            String title = ob.getString("track_name");
            String uploadDate = ob.getString("upload_date");
            String instrument = ob.getString("instrument");
            String imgUrl = ob.getString("img_url");
            String tags = ob.getString("tags");
            String band = ob.getString("band_name");
            String trackUrl = ob.getString("track_url");
            String user_name = ob.getString("user_name");
            int ancestor = ob.getInt("ancestor_id");
            //ArrayList<String> tags = new ArrayList<>();

                /*JSONArray t = ob.getJSONArray("Tags");
                for(int j = 0; j < t.length(); j++ ){
                    JSONObject ob2 = t.getJSONObject(j);

                    String tag = ob2.getString("Tag"+Integer.toString(j));
                    tags.add(tag);
                }*/
            Track track;

            if (user_name == null)
                track = new Track(title, band, id, duration, imgUrl, trackUrl,
                        uploadDate, tags, ancestor, instrument, likes, rating);
            else
                track = new Track(title, user_name, id, duration, imgUrl, trackUrl,
                        uploadDate, tags, ancestor, instrument, likes, rating);

            myTracks.add(track);
        }
        return myTracks;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setRating(double rating) {
        this.rating = rating;
    }

    @Override
    public void setLikes(int likes) {
        this.likes = likes;
    }

    @Override
    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public void setAncestor(int ancestor) {
        this.ancestor = ancestor;
    }

    @Override
    public void setID(int id) {
        this.id = id;
    }

    @Override
    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    @Override
    public void setChildren(ArrayList<Track> children) {
        this.children = children;
    }

    @Override
    public void setUpload_date(String date) {
        this.upload_date = date;
    }

    @Override
    public void setTrackUrl(String trackUrl) {
        this.trackUrl = trackUrl;
    }

    @Override
    public ArrayList<Track> getChildren() {
        return children;
    }

    @Override
    public String getTrackUrl() {
        return trackUrl;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public double getRating() {
        return rating;
    }

    @Override
    public int getLikes() {
        return likes;
    }

    @Override
    public String getUploader() {
        return uploader;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public String getTags() {
        return tags;
    }

    @Override
    public int getAncestor() {
        return ancestor;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public String getInstrument() {
        return instrument;
    }

    @Override
    public String getUpload_date() {
        return this.upload_date;
    }

    public ArrayList<String> getContributors() {
        ArrayList<String> contributors = new ArrayList<String>();
        Track node = this;
        do {
            contributors.add(node.getUploader());
        } while (node.getAncestor() != 0);

        return contributors;
    }

    public ArrayList<String> getInstruments() {
        ArrayList<String> instruments = new ArrayList<String>();
        Track node = this;
        do {
            instruments.add(node.getInstrument());
        } while (node.getAncestor() != 0);

        return instruments;
    }
}