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
public class LogInTask extends AsyncTask<JSONObject, Void, String> {
    @Override
    protected String doInBackground(JSONObject... params) {
        try {
            URL url = new URL(ServerManager.getServerURL() + "/users/login.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("POST");

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
            writer.write(params[0].toString());
            writer.flush();
            writer.close();

            //SERVER RESPONSE
            StringBuilder builder = new StringBuilder();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                String line;
                while ((line = reader.readLine()) != null) {
                    if(line.charAt(0)!='<')
                        builder.append(line + "\n");
                }
                reader.close();
                return builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            JSONObject response = new JSONObject(s);
            String status = response.getString("status");
            Context ctx = signin.CTX;
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
