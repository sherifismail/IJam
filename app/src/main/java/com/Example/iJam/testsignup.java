package com.Example.iJam;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class testsignup extends ActionBarActivity {

    EditText et_username;
    EditText et_pass;
    EditText et_confirm_pass;
    EditText et_email;
    EditText et_fname;
    EditText et_lname;
    Button btn_signup_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testsignup);

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
                String password = et_pass.getText().toString().trim();
                String confirm_pass = et_confirm_pass.getText().toString().trim();
                if(password.equals(confirm_pass)) {
                    String user_name = et_username.getText().toString().trim();
                    String email = et_email.getText().toString().trim();
                    String fname = et_fname.getText().toString().trim();
                    String lname = et_lname.getText().toString().trim();

                    JSONObject user = new JSONObject();
                    try{
                        user.put("user_name", user_name);
                        user.put("password", password);
                        user.put("email", email);
                        user.put("first_name", fname);
                        user.put("last_name", lname);

                        InsertUserTask insertUserTask = new InsertUserTask(getApplicationContext());
                        insertUserTask.execute(user);
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