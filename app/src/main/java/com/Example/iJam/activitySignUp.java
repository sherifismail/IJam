package com.Example.iJam;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class activitySignUp extends ActionBarActivity {

    EditText et_username, et_pass, et_confirm_pass, et_email, et_fname, et_lname;
    Button btn_signup_;
    String password, confirm_pass, user_name, email, fname, lname;
    ImageView profileimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profileimage=(ImageView)findViewById(R.id.imageView4);
        profileimage.setImageResource(R.drawable.x);
        et_username=(EditText) findViewById(R.id.editText);
        et_pass=(EditText) findViewById(R.id.editText2);
        et_confirm_pass=(EditText) findViewById(R.id.editText3);
        et_email=(EditText) findViewById(R.id.editText4);
        et_fname=(EditText) findViewById(R.id.editText5);
        et_lname=(EditText) findViewById(R.id.editText6);
        btn_signup_=(Button) findViewById(R.id.button3);

        btn_signup_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do{
                    password = et_pass.getText().toString().trim();
                    confirm_pass = et_confirm_pass.getText().toString().trim();
                    user_name = et_username.getText().toString().trim();
                    email = et_email.getText().toString().trim();
                    fname = et_fname.getText().toString().trim();
                    lname = et_lname.getText().toString().trim();
                }while(password.equals("") || confirm_pass.equals("") || user_name.equals("")|| email.equals("")
                        || fname.equals("") || lname.equals(""));

                if(password.equals(confirm_pass)) {
                    JSONObject user = new JSONObject();
                    try{
                        user.put("user_name", user_name);
                        user.put("password", password);
                        user.put("email", email);
                        user.put("first_name", fname);
                        user.put("last_name", lname);

                        new InsertUserTask(getApplicationContext()){
                            @Override
                            protected void onPostExecute(String s) {
                                try{
                                    JSONObject response = new JSONObject(s);
                                    String status = response.getString("status");
                                    ServerManager.setServerStatus(status);
                                    if(status.equals("fail")) {
                                        Toast.makeText(ctx, "Sign up failed! " + response.getString("error"), Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
                                        int uid = response.getInt("user_id");
                                        Intent inte = new Intent(ctx, MainActivity.class);
                                        inte.putExtra("user_id", uid);
                                        inte.putExtra("user_name", user_name);
                                        inte.putExtra("password", password);
                                        inte.putExtra("first_name", fname);
                                        inte.putExtra("last_name", lname);
                                        inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        ctx.startActivity(inte);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.execute(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(), "passwords mismatch!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}