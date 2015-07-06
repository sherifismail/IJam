package com.Example.iJam;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Mostafa on 7/6/2015.
 */
public class LogInTask extends HttpPostTask {

    public LogInTask(Context ctx) {
        super(ServerManager.getServerURL()+"/users/login.php", ctx);
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            JSONObject response = new JSONObject(s);
            String status = response.getString("status");
            ServerManager.setServerStatus(status);
            if(status.equals("fail")) {
                Toast.makeText(ctx, "Log in failed! " + response.getString("error"), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(ctx, ThirdActivity.class);
                inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(inte);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
