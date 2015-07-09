package com.Example.iJam.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.Example.iJam.network.InsertUserTask;
import com.Example.iJam.R;
import com.Example.iJam.network.ServerManager;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText et_username, et_pass, et_confirm_pass, et_email, et_fname, et_lname;
    Button btn_signup;
    ImageView profileImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profileImage =(ImageView)findViewById(R.id.signup_img_userimage);
        et_username=(EditText) findViewById(R.id.signup_et_username);
        et_pass=(EditText) findViewById(R.id.signup_et_pass);
        et_confirm_pass=(EditText) findViewById(R.id.signup_et_conpass);
        et_email=(EditText) findViewById(R.id.signup_et_email);
        et_fname=(EditText) findViewById(R.id.signup_et_fname);
        et_lname=(EditText) findViewById(R.id.signup_et_lname);
        btn_signup =(Button) findViewById(R.id.signup_bt_signup);
        btn_signup.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {
        final String password = et_pass.getText().toString().trim();
        final String confirm_pass = et_confirm_pass.getText().toString().trim();
        final String user_name = et_username.getText().toString().trim();
        final String email = et_email.getText().toString().trim();
        final String fname = et_fname.getText().toString().trim();
        final String lname = et_lname.getText().toString().trim();

        if (password.equals("") || confirm_pass.equals("") || user_name.equals("") || email.equals("")
                || fname.equals("") || lname.equals(""))
            Toast.makeText(getApplicationContext(), "one or more of the fields is empyt!", Toast.LENGTH_SHORT).show();
        else {
            if (password.equals(confirm_pass)) {
                JSONObject user = new JSONObject();
                try {
                    user.put("user_name", user_name);
                    user.put("password", password);
                    user.put("email", email);
                    user.put("first_name", fname);
                    user.put("last_name", lname);

                    new InsertUserTask(getApplicationContext()) {
                        @Override
                        protected void onPostExecute(String s) {
                            try {
                                JSONObject response = new JSONObject(s);
                                String status = response.getString("status");
                                ServerManager.setServerStatus(status);
                                if (status.equals("fail")) {
                                    Toast.makeText(ctx, "Sign up failed! " + response.getString("error"), Toast.LENGTH_SHORT).show();
                                } else {
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
            } else
                Toast.makeText(getApplicationContext(), "passwords mismatch!", Toast.LENGTH_SHORT).show();
        }
    }
}