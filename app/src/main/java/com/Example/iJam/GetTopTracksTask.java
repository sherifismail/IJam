package com.Example.iJam;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mostafa on 7/7/2015.
 */
public class GetTopTracksTask extends HttpPostTask {
    public GetTopTracksTask(Context ctx) {
        super(ServerManager.getServerURL()+"/tracks/top_tracks.php", ctx);
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            JSONArray top_tracks = new JSONArray(s);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
