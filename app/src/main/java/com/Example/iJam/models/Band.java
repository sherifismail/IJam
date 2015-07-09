package com.Example.iJam.models;

import com.Example.iJam.interfaces.BandInterface;

import java.util.ArrayList;

/**
 * Created by Mostafa on 6/29/2015.
 */
public class Band implements BandInterface {
    private int band_id;
    private String imageUrl;
    private String name;
    private String author;
    private ArrayList<User> members;
    private ArrayList<Track> tracks;

    public Band(String name){
        this.name = name;
        members = new ArrayList<>();
        tracks = new ArrayList<>();
    }

    public Band(){
        name = null;
        band_id = 0;
        members = new ArrayList<>();
        tracks = new ArrayList<>();
    }

    @Override
    public int getID() {
        return band_id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArrayList<Track> getTracks() {
        return tracks;
    }

    @Override
    public ArrayList<User> getUsers() {
        return members;
    }

    @Override
    public String getAuthor() {
        return author;
    }

    @Override
    public String getImageUrl() {
        return imageUrl;
    }

    @Override
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public void setMembers(ArrayList<User> members) {
        this.members = members;
    }

    @Override
    public void addMember(User member){
        members.add(member);
    }

    @Override
    public void addTrack(Track track){
        tracks.add(track);
    }

    @Override
    public void setTracks(ArrayList<Track> tracks) {
        this.tracks = tracks;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setID(int id) {
        this.band_id = id;
    }
}