package com.Example.iJam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Khodary on 7/5/15.
 */
public class Track implements trackInterface {
    private String title;                //track name
    private String uploader;            //track auploader name
    private boolean band;               //true it the uploader is band
    private int id;                //track id
    private int duration;               //in seconds
    private String imageUrl;              //image source location
    private String upload_date;           //track upload date
    private ArrayList<String> tags;     //track tags
    private Track ancestor;             //ID of ancestor track, null if solo
    private ArrayList<Track> children;          //ID of children tracks, empty if last jam
    //private ArrayList<String> contributors;         //names of contributing users
    //private ArrayList<String> instruments;          //names of instrucments involved
    private String instrument;
    private int likes;                  //number of likes
    private double rating;
    private int raters;


    public Track(String name, String uploader, boolean band, int duration, String imageUrl, ArrayList<String> tags, String instrument){
        this.title = title;
        this.uploader = uploader;
        this.band = band;
        //trackID = ++id;
        this.duration = duration;
        this.imageUrl = imageUrl;
        Date date = new Date();
        upload_date = new SimpleDateFormat("dd-MM-yyyy").format(date);
        this.tags = tags;
        ancestor = null;
        children = null;
        //contributors = null;
        this.instrument = instrument;
        likes = 0;
        rating = 0;
        raters = 0;
    }

    public Track(String uploader, boolean band, String instrument, Track ancestor){
        this.title = ancestor.title;
        this.uploader = uploader;
        this.band = band;
        //trackID = ++id;
        this.duration = ancestor.duration;
        this.tags = ancestor.tags;
        this.ancestor = ancestor;
        children = null;
        //contributors = ancestor.contributors;
        //contributors.add(ancestor.uploader);
        this.instrument = instrument;
        likes = 0;
        rating = 0;
        raters = 0;

        ancestor.children.add(this);
    }

    public Track(){
        title = null;
        uploader = null;
        band = false;
        id = 0;
        duration = 0;
        imageUrl = null;
        upload_date = null;
        tags = null;
        ancestor = null;
        children =  null;
        //contributors;
        //instruments;
        instrument = null ;
        likes = 0;
        rating = 0.0;
        raters = 0;
    }

    public static JSONArray toJsonArray(ArrayList<Track> tracks){
        JSONArray trackArray = new JSONArray();
        for(Track track : tracks){
            trackArray.put(track.toJSONObject());
        }
        return trackArray;
    }

    public JSONObject toJSONObject(){
        JSONObject trackOb= new JSONObject();
        try {
            //String encodedImage = convertToBase64();

            trackOb.put("Title", this.getTitle());
            trackOb.put("Uploader", this.getUploader());
            trackOb.put("Duration", this.getDuration());
            trackOb.put("Tags", this.getTags());
            trackOb.put("Likes", this.getLikes());
            trackOb.put("Duration", this.getDuration());
            trackOb.put("Rating", this.getRating());
            trackOb.put("Instrument", this.getInstrument());
            //contact.put("Image", encodedImage);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trackOb;
    }

    public static ArrayList<Track> parseJson(JSONArray jArray){
        ArrayList<Track> myTracks = new ArrayList<>();

        for(int i = 0; i < jArray.length(); i++){
            try {
                Track track = new Track();
                JSONObject ob = jArray.getJSONObject(i);

                int duration = ob.getInt("Duration");
                int likes = ob.getInt("Likes");
                double rating = ob.getDouble("Rating");
                String title = ob.getString("Title");
                String uploader = ob.getString("Uploader");
                String instrument = ob.getString("Instrument");
                ArrayList<String> tags = new ArrayList<>();

                JSONArray t = ob.getJSONArray("Tags");
                for(int j = 0; j < t.length(); j++ ){
                    JSONObject ob2 = t.getJSONObject(j);

                    String tag = ob2.getString("Tag"+Integer.toString(j));
                    tags.add(tag);
                }
                track.setDuration(duration);
                track.setLikes(likes);
                track.setRating(rating);
                track.setTitle(title);
                track.setUploader(uploader);
                track.setInstrument(instrument);
                track.setTags(tags);

                myTracks.add(track);

            } catch (JSONException e) {
                e.printStackTrace();
            }

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
    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    @Override
    public void setAncestor(Track ancestor) {
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
    public ArrayList<String> getTags() {
        return tags;
    }

    @Override
    public Track getAncestor() {
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

    public ArrayList<String> getContributors(){
        ArrayList<String> contributors = new ArrayList<String>();
        Track node = this;
        do{
            contributors.add(node.getUploader());
        }while(node.getAncestor()!=null);

        return contributors;
    }

    public ArrayList<String> getInstruments(){
        ArrayList<String> instruments = new ArrayList<String>();
        Track node = this;
        do{
            instruments.add(node.getInstrument());
        }while(node.getAncestor()!=null);

        return instruments;
    }
}