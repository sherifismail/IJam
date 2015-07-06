package com.Example.iJam;

import java.util.ArrayList;

/**
 * Created by Khodary on 7/5/15.
 */
public interface myInterface {
    public void setImageUrl(String imageUrl);
    public void setTitle(String title);
    public void setRating(float rating);
    public void setLikes(int likes);
    public void setUploader(String uploader);
    public void setDuration(int duration);
    public void setTags(ArrayList<String> tags);
    public void setAncestor(Track ancestor);
    public void setID(int id);
    public void setInstrument(String instrument);
    public void setChildren(ArrayList<Track> children);
    public void setUpload_date(String date);

    public String getImageUrl();
    public String getTitle();
    public float getRating();
    public int getLikes();
    public String getUploader();
    public int getDuration();
    public ArrayList<String> getTags();
    public Track getAncestor();
    public int getID();
    public String getInstrument();
    public String getUpload_date();
}

