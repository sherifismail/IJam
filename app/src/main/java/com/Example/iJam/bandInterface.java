package com.Example.iJam;

import java.util.ArrayList;

/**
 * Created by Khodary on 7/7/15.
 */
public interface bandInterface {
    public int getID();
    public String getName();
    public ArrayList<Track> getTracks();
    public ArrayList<User> getUsers();
    public String getAuthor();

    public void setAuthor(String author);
    public void setMembers(ArrayList<User> members);
    public void addMember(User member);
    public void addTrack(Track track);
    public void setTracks(ArrayList<Track> tracks);
    public void setName(String name);
    public void setID(int id);
}
