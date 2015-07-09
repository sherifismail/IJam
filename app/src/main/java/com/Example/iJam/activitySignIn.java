package com.Example.iJam;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class activitySignIn extends AppCompatActivity {

    EditText et_user_name;
    EditText et_password;
    Button signup;
    Button signin;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        et_user_name = (EditText) findViewById(R.id.signin_et_username);
        et_password = (EditText) findViewById(R.id.signin_et_pass);
        signin = (Button)findViewById(R.id.signin_bt_signin);
        signup = (Button)findViewById(R.id.button2);

        TextView mTextView=(TextView)findViewById(R.id.txtview_forget);
        mTextView.setPaintFlags(mTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mTextView.setText("Forget Password");

        mTextView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activitySignIn.this,activitySignUp.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = et_user_name.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (user_name.equals("") || password.equals(""))
                    Toast.makeText(getApplicationContext(), "one or more of the fields is empty!", Toast.LENGTH_SHORT).show();
                else{
                    JSONObject login_info = new JSONObject();
                    try {
                        login_info.put("user_name", user_name);
                        login_info.put("password", password);

                        new LogInTask(getApplicationContext()) {
                            @Override
                            protected void onPostExecute(String s) {
                                try {
                                    JSONObject response = new JSONObject(s);
                                    String status = response.getString("status");
                                    if (status.equals("fail")) {
                                        Toast.makeText(ctx, "Log in failed! " + response.getString("error"), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ctx, "Success", Toast.LENGTH_SHORT).show();
                                        JSONObject user_info = response.getJSONObject("user");

                                        Intent inte = new Intent(ctx, MainActivity.class);
                                        inte.putExtra("user_id", user_info.getInt("user_id"));
                                        inte.putExtra("user_name", user_info.getString("user_name"));
                                        inte.putExtra("password", user_info.getString("password"));
                                        inte.putExtra("first_name", user_info.getString("first_name"));
                                        inte.putExtra("last_name", user_info.getString("last_name"));
                                        inte.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        ctx.startActivity(inte);
                                        finish();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }.execute(login_info);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activitySignIn.this, activitySignUp.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signin, menu);
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
